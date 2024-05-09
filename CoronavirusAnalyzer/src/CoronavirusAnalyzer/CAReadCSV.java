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
		// [Code Private - Contact to View]

		/*
		Create an instance of the CALinkedListQueue class to store the filenames of all CSV files that contain covid-19 statistics.
		Two examples below shows the CSV filenames that are stored in the CALinkedListQueue instance.
			"data/csse_covid_19_daily_reports/01-01-2021.csv"
			"data/csse_covid_19_daily_reports/01-02-2021.csv"
		*/

		// Find all the covid-19 CSV data files in the JAR file and add them to the linked list queue.
	}

	/**
	 * Read the contents of the CSV files and process it.
	 * 
	 * @param filename The current file to read and process
	 * @return void
	 */
	private void loadFileFromURL(String filename) // Process the data in each CSV file.
	{
		// [Code Private - Contact to View]

		/*
		The first three entries of the "data/csse_covid_19_daily_reports/01-01-2021.csv" file is shown below. Other CSV files are in the same format.
			FIPS,Admin2,Province_State,Country_Region,Last_Update        ,Lat     ,Long_    ,Confirmed,Deaths,Recovered,Active,Combined_Key,Incident_Rate     ,Case_Fatality_Ratio
			    ,      ,              ,Afghanistan   ,2021-01-02 05:22:33,33.93911,67.709953,51526    ,2191  ,41727    ,0     ,Afghanistan ,0.0               ,4.2522221790940495
			    ,      ,              ,Albania       ,2021-01-02 05:22:33,41.1533 ,20.1683  ,58316    ,1181  ,33634    ,23501 ,Albania     ,2026.4090624782818,2.025173194320598
		*/

		// Extracting the year, month, and day from the CSV filename, e.g., "data/csse_covid_19_daily_reports/01-01-2021.csv".
		// Reformat the order of date to year, month, day.
			// Obtain the index of the column headings.

				// All code in this loop below, runs from second row to last, thus, they are statistical values.
				// Obtain the statistical value based on row (from loop index) and column (based on the i=0 loop column headings).

				// Adjust special cases.

				// Store values.

					// If there is already an entry for a country, add the existing statistical values as sum.
			// One complete day of statistics for each country is added to "data". This method is called for daily CSV files.
	}

	/**
	 * Loads individual CSV day data files into TreeMap "data". Process "data" to setup the calculation for new cases and new deaths.
	 * 
	 * @return void
	 */
	public void loadDataFiles()
	{
		// [Code Private - Contact to View]

		/*
		Loops through the TreeMap "data" to calculate the statistics for new cases and
		new deaths through subtraction of adjacent total cases and total deaths data respectively.
		*/

			// Loop through the statistics of each country at a specific day.
	}

	/**
	 * Adjust for inconsistency between SVG map file and CSV data file.
	 * 
	 * @param country The country from either the CSV file or SVG file where country name may be inconsistent
	 * @return The converted country that is uniform throughout the program
	 */
	public String getCountryName(String country)
	{
		// [Code Private - Contact to View]
	}

	/**
	 * Gets the current day of the current entry.
	 * 
	 * @return The date
	 */
	public String getCurrentDate()
	{
		// [Code Private - Contact to View]
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
		// [Code Private - Contact to View]
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
		// [Code Private - Contact to View]
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
		// [Code Private - Contact to View]

		// Delete old table content by removing rows one by one starting at the end of the table.

		// Add new table values to the table. TreeMap data contains the date, country, and statistics value.
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
		// [Code Private - Contact to View]

		// Clear old history of a country when data refresh is requested, i.e., change of country or presentation type.

		// TreeMap data contains date, country, and statistical value.
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
		// [Code Private - Contact to View]

			// Find partition.
		
			// Continue sorting subsections.
	}
}
