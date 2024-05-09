package CoronavirusAnalyzer;

// Import packages.
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.AbstractButton;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main class to run the Coronavirus Analyzer program.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CoronavirusAnalyzer extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	// Declare objects.
	private static CoronavirusAnalyzer frame;
	private CAScrollPane scrollBar;
	private CAMapPane map;
	private CARankPane ranking;
	private CATablePane dataTable;
	private CAHistoryPane history;
	private JPanel bottom;
	private JTextField tfCountry; 
	
	// Declare and initialize constants.
	private static final int WINDOW_WIDTH = 1900;
	private static final int WINDOW_HEIGHT = 924;
	private static final String LABEL_TOTAL_CASES = "Total Cases";
	private static final String LABEL_NEW_CASES = "New Cases";
	private static final String LABEL_TOTAL_DEATHS = "Total Deaths";
	private static final String LABEL_NEW_DEATHS = "New Deaths";

	/**
	 * Setup and add components the frame of the Coronavirus Analyzer program.
	 *
	 * @return void
	 */
	private void run()
	{
		// [Code Private - Contact to View]

		// This section sets up the bottom panel which includes the play and stop button, options to change statistics, and search for country feature.
			// Set up the statistics display control buttons, i.e., Play and Stop buttons.
				// Instantiations of the JButton class.

				// Set button size and associate an action to a button press.

				// Add the Play and Stop buttons to the bottom panel.

			// Set up the options bar where the user can change the statistics to display.
				// Instantiations of the JRadioButton class.

				// Place the radio buttons into a panel with a raised border.

				// Ensure only one button can be selected each time. Set default selection to "Total Cases".

				// Instantiate the ActionListener class to apply it to the radio buttons.

				// Add the panel of radio buttons to the bottom panel.
		
			// Set up the a country search field and a button that initiates the search for detailed data based on the field.
				// Instantiate the JTextField class to allow input of a country to search for. Default is "Canada".

				// Create an instantiation of the JButton class to initiate the searching of detailed data of the country in the text field.

				// Create a panel with a raised border that contains the text field and search button.

				// Add the panel containing the text field and search button into the bottom panel.

		// This section sets up the panel above the bottom panel which contains the scroll bar, representing the timeline.
			// Create and setup an instance of the custom class CAScrollPane to allow for custom features.

		// This section sets up the top-central panel which contains the map displaying the statistics.
			// Create and setup an instance of the custom class caMapPane to allow for custom features.

		// This section sets up the left panel which contains the ranking in descending order of a statistics.
			// Create and setup an instance of the custom class caRankPane to allow for custom features.

		// This section sets up the top-right panel which contains the graph showing the history of the text field country's statistics.
			// Create and setup an instance of the custom class CAHistoryPane to allow for custom features.

		// This section sets up the bottom-right panel which contains the table of statistics of the text field country.
			// Create and setup an instance of the custom class CATablePane to allow for custom features.

		// This section sets up the position of panels on the frame.
			// An overall dashboard will contain all panels in a GridBagLayout. Overall, panels are catagorized in three columns.

			// The left column will contain the ranking panel.

			// The right column will contain the graph and table of data with equal dimensions.

			// Instantiate the GridBagConstraints class to customize the layout.
			
			// The middle colum will contain the map, timeline, and the button control/search field with custom dimensions.
			
			// Add the left and right columns to the dashboard. These columns take up equal vertical space as three rows in the middle column.

			// Add the dashboard containing all panels to the frame.

		// This section will sets up frame settings.

		// Setup the panes to start processing in the program.
	}

	/**
	 * Updates the graph and table in the right panel that is specific to a country, when new country is requested.
	 * 
	 * @return void
	 */
	private void updateDetail()
	{
		// [Code Private - Contact to View]
	}

	/**
	 * Updates the presentation type when the desired statistical value changes based on JRadioButton selection.
	 * Each statistic type is represented with a number.
	 * <p> 1. Total cases
	 * <p> 2. New cases
	 * <p> 3. Total deaths
	 * <p> 4. New deaths
	 * 
	 * @param type The type of statistic to view denoted by the numbers 1, 2, 3, and 4
	 * @return void
	 */
	private void updatePresentationType(int type)
	{
		// [Code Private - Contact to View]
	}

	/**
	 * Calls the constructor method of the superclass JFrame to create a frame labelled "Coronavirus Analyzer".
	 */
	public CoronavirusAnalyzer()
	{
		// [Code Private - Contact to View]
	}

	/**
	 * Starts the Coronavirus Analyzer program.
	 *
	 * @param args Command-line arguments
	 * @return void
	 */
	public static void main(String[] args)
	{
		// [Code Private - Contact to View]
    }
}