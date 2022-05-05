package CoronavirusAnalyzer;

// Import packages.
import au.com.bytecode.opencsv.CSVReader;
import java.net.URL;
import java.security.CodeSource;
import javax.swing.table.DefaultTableModel;

import java.awt.Canvas;
import java.io.InputStreamReader;

import java.util.List;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

/**
 * Read, process and store CSV file data. Data manipulation features such as generating ranking upon request by sorting data.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CAReadCSV
{
	// TreeMap "data" consists of the date, country, and statistical value.
	public Map<String, TreeMap<String, RecordItem>> data = new TreeMap<String, TreeMap<String, RecordItem>>();
	public Entry<String, TreeMap<String, RecordItem>> currentEntry;

	private CABinarySearchTree rankOfConfirmed; // Stores country and rank.
	public List<String> rankList; // Unsorted (currently, will be later) list of countries of a statistical value corresponding to a specific presentation type.
	public int mPresentationType = 0;

	/**
	 * Get the flienames of the CSV files by looking in the jar.
	 * 
	 * @return A custom linked list queue which contains the flienames
	 */
	private CALinkedListQueue getFilenames()
	{
		/*
		Create an instance of the CALinkedListQueue class to store the filenames of all CSV files that contain covid-19 statistics.
		Two examples below shows the CSV filenames that are stored in the CALinkedListQueue instance.
			"data/csse_covid_19_daily_reports/01-01-2021.csv"
			"data/csse_covid_19_daily_reports/01-02-2021.csv"
		*/
		CALinkedListQueue filenames = new CALinkedListQueue();

		// Find all the covid-19 CSV data files in the JAR file and add them to the linked list queue.
		try {
			CodeSource src = getClass().getProtectionDomain().getCodeSource(); // How to get the path of a running JAR file? // https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file
			if (src != null)
			{
				// How to list the files inside a JAR file? // https://stackoverflow.com/questions/1429172/how-to-list-the-files-inside-a-jar-file
				URL jar = src.getLocation();
				ZipInputStream zip = new ZipInputStream(jar.openStream());
				while(true) {
					ZipEntry e = zip.getNextEntry();
					if (e == null)
						break;
					String name = e.getName();
					if (!name.startsWith("data/csse_covid_19_daily_reports/")) 
						continue;
					if (!name.endsWith(".csv")) 
						continue;
					filenames.enqueue(name); // Add the filename to the linked list queue.
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return filenames; // Return the linked list queue of filenames with the covid-19 data.
	}

	/**
	 * Read the contents of the CSV files and process it.
	 * 
	 * @param filename The current file to read and process
	 * @return void
	 */
	private void loadFileFromURL(String filename) // Process the data in each CSV file.
	{
		/*
		The first three entries of the "data/csse_covid_19_daily_reports/01-01-2021.csv" file is shown below. Other CSV files are in the same format.
			FIPS,Admin2,Province_State,Country_Region,Last_Update        ,Lat     ,Long_    ,Confirmed,Deaths,Recovered,Active,Combined_Key,Incident_Rate     ,Case_Fatality_Ratio
			    ,      ,              ,Afghanistan   ,2021-01-02 05:22:33,33.93911,67.709953,51526    ,2191  ,41727    ,0     ,Afghanistan ,0.0               ,4.2522221790940495
			    ,      ,              ,Albania       ,2021-01-02 05:22:33,41.1533 ,20.1683  ,58316    ,1181  ,33634    ,23501 ,Albania     ,2026.4090624782818,2.025173194320598
		*/

		// Extracting the year, month, and day from the CSV filename, e.g., "data/csse_covid_19_daily_reports/01-01-2021.csv".
		int n = filename.lastIndexOf("/");
		int month = Integer.parseInt(filename.substring(n+1, n+3));
		int day   = Integer.parseInt(filename.substring(n+4, n+6));
		int year  = Integer.parseInt(filename.substring(n+7, n+11));

		// Reformat the order of date to year, month, day.
		String ymd = String.format("%04d-%02d-%02d", year, month, day);

		try
		{
			TreeMap<String, RecordItem> confirmed = new TreeMap<String, RecordItem>();
			CSVReader reader2 = new CSVReader(new InputStreamReader(CAReadCSV.class.getResourceAsStream("/"+filename))); // Load the CSV file.
			List<String[]> allElements = reader2.readAll(); // Store all the values read from the CVS file into a list of string arrays.

			// Obtain the index of the column headings.
			int countryIndex = -1;
			int provinceIndex = -1;
			int confirmedIndex = -1;
			int deathsIndex = -1;
			for(int i=0; i< allElements.size(); i++) // Looping through the list rows.
			{
				if(i == 0) // First row contains the headings rather than statistical values.
				{
					for(int j = 0; j< allElements.get(i).length; j++) // Loop through the string array, i.e., columns of first row.
					{
						if(allElements.get(i)[j].equals("Country/Region"))
							countryIndex = j;
						else if(allElements.get(i)[j].equals("Country_Region"))
							countryIndex = j;
						else if(allElements.get(i)[j].equals("Province/State"))
							provinceIndex = j;
						else if(allElements.get(i)[j].equals("Province_State"))
							provinceIndex = j;
						else if(allElements.get(i)[j].equals("Confirmed"))
							confirmedIndex = j;
						else if(allElements.get(i)[j].equals("Deaths"))
							deathsIndex = j;
						if(provinceIndex==-1)
							provinceIndex = 0;
					}
					if(countryIndex == -1 || confirmedIndex == -1 || deathsIndex == -1 || provinceIndex ==-1)
					{
						System.out.printf("Error: have not found one or more data fields. %s %d %d %d %d [%s]\n", ymd, countryIndex, provinceIndex, confirmedIndex, deathsIndex, allElements.get(i)[0]);
						break;
					}
					continue;
				}

				// All code in this loop below, runs from second row to last, thus, they are statistical values.
				// Obtain the statistical value based on row (from loop index) and column (based on the i=0 loop column headings).
				String country = allElements.get(i)[countryIndex];
				String province = allElements.get(i)[provinceIndex];
				String confirmedString = allElements.get(i)[confirmedIndex];
				String deathsString = allElements.get(i)[deathsIndex]; 

				// Adjust special cases.
				if(country.equals("Mainland China"))
					country = "China"; // Inconsistency in CSV files. Earlier CSV files displayed "Mainland China" while recent CSV files displays "China".
				if(province.equals("Greenland"))
					country = "Greenland"; // To simulate data as closely as possible, separate Greenland colour from Dennmark colour since Greenland is much larger in area.

				// Store values.
				int confirmedNumber = (confirmedString.length() > 0)?Integer.parseInt(confirmedString):0;
				int deathsNumber = (deathsString.length() > 0)?Integer.parseInt(deathsString):0;
				RecordItem it = confirmed.get(country);
				if(it == null)
					confirmed.put(country, new RecordItem(confirmedNumber, deathsNumber)); // Used to sum up statistics for entire countries.
				else
				{
					// If there is already an entry for a country, add the existing statistical values as sum.
					it.totalCases += confirmedNumber;
					it.totalDeaths += deathsNumber;
				}
			}
			// One complete day of statistics for each country is added to "data". This method is called for daily CSV files.
			data.put(ymd, confirmed);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Loads individual CSV day data files into TreeMap "data". Process "data" to setup the calculation for new cases and new deaths.
	 * 
	 * @return void
	 */
	public void loadDataFiles()
	{
		CALinkedListQueue filenames = getFilenames(); // List of filenames for the covid-19 statistics.
		while(!filenames.isEmpty()) // Loop through each filename to process the data in each file.
			loadFileFromURL((String)filenames.dequeue()); // Call method to process and store data in CSV file into the TreeMap "data".

		TreeMap<String, RecordItem> previous = new TreeMap<String, RecordItem>();

		/*
		Loops through the TreeMap "data" to calculate the statistics for new cases and
		new deaths through subtraction of adjacent total cases and total deaths data respectively.
		*/
		Iterator<Map.Entry<String, TreeMap<String, RecordItem>>> i = data.entrySet().iterator(); // The day is the key in "data".
		while(i.hasNext())
		{
			Map.Entry<String, TreeMap<String, RecordItem>> m = i.next(); 

			TreeMap<String, RecordItem> tm = m.getValue(); // The value is a TreeMap with country as key and statistics as value.

			// Loop through the statistics of each country at a specific day.
			Iterator<Map.Entry<String, RecordItem>> j = tm.entrySet().iterator();
			while(j.hasNext()){
				Map.Entry<String, RecordItem> n = j.next();
	
				String country = n.getKey(); // Country is the key of the TreeMap that is the value of the "data" TreeMap.
				RecordItem v = n.getValue(); // RecordItem contains the statistics of a country at a day.

				RecordItem pv = previous.get(country);
				int pd = 0; // Previous total deaths.
				int pc = 0; // Previous total cases.
				if(pv != null) // If statistics for a country is not the first entry, i.e., not the first day of statistics.
				{
					// Save the previous day information.
					pc = pv.totalCases;
					pd = pv.totalDeaths;

					// Set the curent day as the previous day, for the processing of the next day information.
					pv.totalCases = v.totalCases;
					pv.totalDeaths = v.totalDeaths;
				}
				else // If it is the first entry for a country at a day, then there are no previous data, so add data that will become the previous values for the next day.
					previous.put(country, new RecordItem(v.totalCases, v.totalDeaths));

				// Call the current day RecordItem to calculate its new cases and new deaths based on the previous day statistics.
				v.setPrevious(pc, pd);
			}
		}
	}

	/**
	 * Adjust for inconsistency between SVG map file and CSV data file.
	 * 
	 * @param country The country from either the CSV file or SVG file where country name may be inconsistent
	 * @return The converted country that is uniform throughout the program
	 */
	public String getCountryName(String country)
	{
		if(country.equals("Russian Federation")) return "Russia";
		if(country.equals("United States")) return "US";
		if(country.equals("Saint-Barthélemy")) return "France";
		if(country.equals("Bermuda")) return "United Kingdom";
		if(country.equals("Cape Verde")) return "Cabo Verde";
		
		if(country.equals("Curaçao")) return "Curacao";
		if(country.equals("Cayman Islands")) return "United Kingdom";

		if(country.equals("Falkland Islands")) return "United Kingdom";
		if(country.equals("Faeroe Islands")) return "Denmark";
		if(country.equals("Federated States of Micronesia")) return "Micronesia";
		if(country.equals("Guam")) return "US";
		if(country.equals("Northern Mariana Islands")) return "US";
		if(country.equals("Saint-Martin")) return "France";
		if(country.equals("Montserrat")) return "United Kingdom";
		if(country.equals("New Caledonia")) return "France";
		if(country.equals("Puerto Rico")) return "US";
		if(country.equals("French Polynesia")) return "France";
		if(country.equals("S?o Tomé and Principe")) return "Sao Tome and Principe";
		if(country.equals("Sint Maarten")) return "Netherlands";
		if(country.equals("Turks and Caicos Islands")) return "United Kingdom";
		if(country.equals("British Virgin Islands")) return "United Kingdom";
		if(country.equals("United States Virgin Islands")) return "US";
		if(country.equals("St. Eustatius (Netherlands)")) return "Netherlands";
		if(country.equals("Saba (Netherlands)")) return "Netherlands";
		if(country.equals("Martinique")) return "France";
		if(country.equals("Canary Islands (Spain)")) return "Spain";
		if(country.equals("Mayotte")) return "France";
		if(country.equals("Reunion")) return "France";
		if(country.equals("Guadeloupe")) return "France";
		
		if(country.equals("Republic of Korea")) return "Korea, South";
		if(country.equals("Brunei Darussalam")) return "Brunei";
		if(country.equals("C⌠te d'Ivoire")) return "Cote d'Ivoire";
		if(country.equals("Democratic Republic of the Congo")) return "Congo (Kinshasa)";
		if(country.equals("Republic of Congo")) return "Congo (Brazzaville)";

		if(country.equals("Czech Republic")) return "Czechia";
		if(country.equals("The Gambia")) return "Gambia";
		if(country.equals("Lao PDR")) return "Laos";
		if(country.equals("Macedonia")) return "North Macedonia";
		if(country.equals("Swaziland")) return "Eswatini";
		if(country.equals("Taiwan")) return "Taiwan*";
		if(country.equals("French Guiana")) return "France";
		if(country.equals("Aruba")) return "Netherlands";
		if(country.equals("Anguilla")) return "United Kingdom";
		if(country.equals("American Samoa")) return "Samoa";
		if(country.equals("Saint-BarthΘlemy")) return "France";
		if(country.equals("Curaτao")) return "Netherlands";
		if(country.equals("Sπo TomΘ and Principe")) return "Sao Tome and Principe";
		
		return country;
	}

	/**
	 * Gets the current day of the current entry.
	 * 
	 * @return The date
	 */
	public String getCurrentDate()
	{
		if(currentEntry == null)
			return "";
		return currentEntry.getKey();
	}

	/**
	 * Updates ranking based on day and presentation type.
	 * 
	 * @param nDay The current day
	 * @param presentationType The statistical type
	 * @return void
	 */
	public void updateRankOfConfirmed(int nDay, int presentationType)
	{
		mPresentationType = presentationType;
		rankOfConfirmed = new CABinarySearchTree();
		rankList = new ArrayList<String>(); // Format: number.country.

		currentEntry = ((TreeMap<String, TreeMap<String, RecordItem>>)data).lastEntry(); // Set the current entry to the last day entry.
		if(nDay >=0 && nDay < data.size()) // If the parameter day is a valid day.
		{
			Iterator<Map.Entry<String, TreeMap<String, RecordItem>>> i = data.entrySet().iterator();
			for(int j=0; i.hasNext() && j<data.size(); j++) // Loop through TreeMap "data" while having an index.
			{
				Map.Entry<String, TreeMap<String, RecordItem>> me = i.next();
				if(j == nDay) // Linear search for the current day corresponding to the parameter current day.
				{
					currentEntry = (Entry<String, TreeMap<String, RecordItem>>) me; // Set the entry to the one specified by the parameter.
					break;
				}
			}
		}
		System.out.println(" current entry :" + currentEntry.getKey());
		

		TreeMap<String, RecordItem> daily = currentEntry.getValue(); // TreeMap "daily" has key country and value statistics for a single day.
		{
			Iterator<Map.Entry<String, RecordItem>> i = daily.entrySet().iterator();
		
			while(i.hasNext()){
				Map.Entry<String, RecordItem> me = i.next();
				RecordItem v = me.getValue();
				
				String s = String.format("%10d.%s", v.getValue(presentationType), me.getKey());
				rankList.add(s); // Store the statistics value corresponding to a data type together with the country name separated by a period.
			}
		}

		quickSort(rankList, 0, rankList.size()-1); // Quick sort the values of current chosen statistic type (total confirmed case, new cases, etc.).

		for(int j = 0; j < rankList.size(); j++) {
			String s = rankList.get(j).substring(rankList.get(j).indexOf(".")+1);
			// Insert the countries and their rank to binary search tree.
			rankOfConfirmed.insert(s, j); 
		}
	}

	/**
	 * Gets the data for the desired country.
	 * 
	 * @param country The desired country
	 * @param cData Contains the statistical value and rank
	 * @return Whether retrieval was successful
	 */
	public boolean getCountryData(String country, int [] cData)
	{
		String name = getCountryName(country);
		TreeMap<String, RecordItem> daily = currentEntry.getValue();
		RecordItem value = daily.get(name);

		cData[0] = 0;
		cData[1] = 0;

		if(value == null || rankOfConfirmed == null)
			return false;

		CARankNode node = (CARankNode)rankOfConfirmed.search(name);

		if(node == null)
			return false;

		Integer rank = node.getRank(); 

		cData[0] = value.totalCases;
		cData[1] = rank;

		return true;
	}

	/**
	 * Refreshes the data table by deleting every old row and adding new rows.
	 * 
	 * @param model The table model for the table of statistics in the bottom right corner of the frame.
	 * @param country The country for which the data table is currently displaying statistics for.
	 * @return void
	 */
	public void refreshTable(DefaultTableModel model, String country)
	{
		// Delete old table content by removing rows one by one starting at the end of the table.
		for (int i = model.getRowCount()-1; i >= 0; i--)
			model.removeRow(i);

		// Add new table values to the table. TreeMap data contains the date, country, and statistics value.
		Iterator<Map.Entry<String, TreeMap<String, RecordItem>>> i = data.entrySet().iterator();
		while(i.hasNext())
		{
			Map.Entry<String, TreeMap<String, RecordItem>> m = i.next();
			RecordItem it = m.getValue().get(country); // The statistical value of a country at a specific day.
			if(it == null)
				continue;
			model.addRow(new Object[]{m.getKey(), it.totalCases, it.newCases, it.totalDeaths, it.newDeaths});
		}
	}

	/**
	 * Refreshes the current history of a country which is used for graphing. Refresh occurs when a country changes or statistics type changes.
	 * 
	 * @param history The history of a country
	 * @param country The current country in the JTextField
	 * @return void
	 */
	public void refreshHistory(TreeMap<String, Integer> history, String country)
	{
		// Clear old history of a country when data refresh is requested, i.e., change of country or presentation type.
		history.clear(); 

		// TreeMap data contains date, country, and statistical value.
		Iterator<Map.Entry<String, TreeMap<String, RecordItem>>> i = data.entrySet().iterator();
		while(i.hasNext())
		{
			Map.Entry<String, TreeMap<String, RecordItem>> m = i.next();
			String ymd = (String)m.getKey(); // Date
			TreeMap<String, RecordItem> tm = m.getValue(); // Country and statistical value.
			RecordItem it = tm.get(country); // Statistical value.
			if(it == null)
				continue;

			history.put(ymd, it.getValue(mPresentationType)); // Store the date and the statistical value of a specific country in the history TreeMap.
		}
	}

	/**
	 * Performs quicksort to sort the ranking.
	 * 
	 * @param data The data to sort
	 * @param start The starting position/index
	 * @param end The ending position
	 * @return void
	 */
	public void quickSort(List<String> data, int start, int end)
	{
		// https://en.wikipedia.org/wiki/Quicksort
		if(start < end)
		{
			// Find partition.
			String pivot = data.get(end);
			int i = start-1;
		
			for(int j = start; j < end; j++)
			{
				if(data.get(j).compareTo(pivot) <= 0)
				{
					i++;
		
					String swapTemp = data.get(i);
					data.set(i, data.get(j));
					data.set(j, swapTemp);
				}
			}
			String swapTemp = data.get(i+1);
			data.set(i+1, data.get(end));
			data.set(end, swapTemp);
		
			// Continue sorting subsections.
			quickSort(data, start, i);
			quickSort(data, i+2, end);
		}
	}
}
