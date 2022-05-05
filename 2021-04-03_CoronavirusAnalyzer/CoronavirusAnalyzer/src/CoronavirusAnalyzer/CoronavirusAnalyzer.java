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
		// This section sets up the bottom panel which includes the play and stop button, options to change statistics, and search for country feature.
		bottom = new JPanel();
		{
			// Set up the statistics display control buttons, i.e., Play and Stop buttons.
			{
				// Instantiations of the JButton class.
				JButton buttonPlay = new JButton("Play");
				JButton buttonStop = new JButton("Stop");

				// Set button size and associate an action to a button press.
				buttonPlay.setPreferredSize(new Dimension(60, 40));
				buttonStop.setPreferredSize(new Dimension(60, 40));
				buttonPlay.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						scrollBar.play();
					}
				});
				buttonStop.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						scrollBar.stop();
					}
				});

				// Add the Play and Stop buttons to the bottom panel.
				bottom.add(buttonPlay);
				bottom.add(buttonStop);
			}

			// Set up the options bar where the user can change the statistics to display.
			{
				// Instantiations of the JRadioButton class.
				JRadioButton rb1 = new JRadioButton(LABEL_TOTAL_CASES); // Stands for radio button 1.
				JRadioButton rb2 = new JRadioButton(LABEL_NEW_CASES);
				JRadioButton rb3 = new JRadioButton(LABEL_TOTAL_DEATHS);
				JRadioButton rb4 = new JRadioButton(LABEL_NEW_DEATHS);

				// Place the radio buttons into a panel with a raised border.
				JPanel rbPanel = new JPanel(); // Radio button panel.
				rbPanel.setPreferredSize(new Dimension(500, 40));
				rbPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
				rbPanel.add(rb1);
				rbPanel.add(rb2);
				rbPanel.add(rb3);
				rbPanel.add(rb4);

				// Ensure only one button can be selected each time. Set default selection to "Total Cases".
				ButtonGroup rbGroup = new ButtonGroup(); // Radio button group.
				rbGroup.add(rb1);
				rbGroup.add(rb2);
				rbGroup.add(rb3);
				rbGroup.add(rb4);
				rb1.setSelected(true);

				// Instantiate the ActionListener class to apply it to the radio buttons.
				ActionListener rbActionListener = new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						AbstractButton src = (AbstractButton) actionEvent.getSource();
						if     (src.getText().equals(LABEL_TOTAL_CASES))  updatePresentationType(1);
						else if(src.getText().equals(LABEL_NEW_CASES))	  updatePresentationType(2);
						else if(src.getText().equals(LABEL_TOTAL_DEATHS)) updatePresentationType(3);
						else if(src.getText().equals(LABEL_NEW_DEATHS))	  updatePresentationType(4);
					}
				};
				rb1.addActionListener(rbActionListener);
				rb2.addActionListener(rbActionListener);
				rb3.addActionListener(rbActionListener);
				rb4.addActionListener(rbActionListener);

				// Add the panel of radio buttons to the bottom panel.
				bottom.add(rbPanel);
			}
		
			// Set up the a country search field and a button that initiates the search for detailed data based on the field.
			{
				// Instantiate the JTextField class to allow input of a country to search for. Default is "Canada".
				tfCountry = new JTextField(18);
				tfCountry.setText("Canada");
				tfCountry.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateDetail();
					}
				});

				// Create an instantiation of the JButton class to initiate the searching of detailed data of the country in the text field.
				JButton detailButton = new JButton("Detail");
				detailButton.setPreferredSize(new Dimension(80, 28));
				detailButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateDetail();
					}
				});

				// Create a panel with a raised border that contains the text field and search button.
				JPanel searchPanel = new JPanel();
				searchPanel.setPreferredSize(new Dimension(300, 40));
				searchPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
				searchPanel.add(tfCountry);
				searchPanel.add(detailButton);

				// Add the panel containing the text field and search button into the bottom panel.
				bottom.add(searchPanel);
			}
		}

		// This section sets up the panel above the bottom panel which contains the scroll bar, representing the timeline.
		{
			// Create and setup an instance of the custom class CAScrollPane to allow for custom features.
			scrollBar = new CAScrollPane();
			scrollBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			scrollBar.setOpaque(true);
			scrollBar.setBackground(Color.WHITE);
			scrollBar.setPreferredSize(new Dimension(1000, 40));
		}

		// This section sets up the top-central panel which contains the map displaying the statistics.
		{
			// Create and setup an instance of the custom class caMapPane to allow for custom features.
			map = new CAMapPane();
			map.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			map.setOpaque(true);
			map.setBackground(Color.WHITE);
			map.setPreferredSize(new Dimension(1000, 750));
		}

		// This section sets up the left panel which contains the ranking in descending order of a statistics.
		{
			// Create and setup an instance of the custom class caRankPane to allow for custom features.
			ranking = new CARankPane();
			ranking.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			ranking.setOpaque(true);
			ranking.setBackground(Color.WHITE);
			ranking.setPreferredSize(new Dimension(400, 400));
		}

		// This section sets up the top-right panel which contains the graph showing the history of the text field country's statistics.
		{
			// Create and setup an instance of the custom class CAHistoryPane to allow for custom features.
			history = new CAHistoryPane();
			history.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
			history.setOpaque(true);
			history.setBackground(Color.WHITE);
			history.setPreferredSize(new Dimension(400, 400));
		}

		// This section sets up the bottom-right panel which contains the table of statistics of the text field country.
		{
			// Create and setup an instance of the custom class CATablePane to allow for custom features.
			dataTable = new CATablePane();
			dataTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dataTable.setOpaque(true);
			dataTable.setBackground(Color.WHITE);
		}

		// This section sets up the position of panels on the frame.
		{
			// An overall dashboard will contain all panels in a GridBagLayout. Overall, panels are catagorized in three columns.
			JPanel dashboard = new JPanel();
			dashboard.setLayout(new GridBagLayout());

			// The left column will contain the ranking panel.
			JPanel left  = new JPanel(new GridLayout(1, 1, 2, 2));
			left.add(ranking);

			// The right column will contain the graph and table of data with equal dimensions.
			JPanel right = new JPanel(new GridLayout(2, 1, 2, 2));
			right.add(history);
			right.add(dataTable);

			// Instantiate the GridBagConstraints class to customize the layout.
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.VERTICAL;
			
			// The middle colum will contain the map, timeline, and the button control/search field with custom dimensions.
			gbc.gridx = 1; gbc.gridy = 0; dashboard.add(map, gbc);
			gbc.gridx = 1; gbc.gridy = 1; dashboard.add(scrollBar, gbc);
			gbc.gridx = 1; gbc.gridy = 2; dashboard.add(bottom, gbc);
			
			// Add the left and right columns to the dashboard. These columns take up equal vertical space as three rows in the middle column.
			gbc.gridheight = 3;
			gbc.gridx = 2; gbc.gridy = 0; dashboard.add(right, gbc);
			gbc.gridx = 0; gbc.gridy = 0; dashboard.add(left, gbc);

			// Add the dashboard containing all panels to the frame.
			frame.add(dashboard);
		}

		// This section will sets up frame settings.
		{
			setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Set window size.
			setLocationRelativeTo(null); // Center frame in screen.
			setVisible(true); // Allow frame to be visible.
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // End program when close button is pressed.
		}

		// Setup the panes to start processing in the program.
		scrollBar.setDisplay(map, dataTable, history, ranking);
		updateDetail();
	}

	/**
	 * Updates the graph and table in the right panel that is specific to a country, when new country is requested.
	 * 
	 * @return void
	 */
	private void updateDetail()
	{
		dataTable.updateDate(map.covid, tfCountry.getText());
		history.updateDate(map.covid, tfCountry.getText());
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
		map.updatePresentationType(type); // This method call triggers a refresh of the map as the user has indicated a change in choice of data viewing.
		history.updateDate(map.covid, tfCountry.getText());
		ranking.updateDate(map.covid);
	}

	/**
	 * Calls the constructor method of the superclass JFrame to create a frame labelled "Coronavirus Analyzer".
	 */
	public CoronavirusAnalyzer()
	{
		super("Coronavirus Analyzer");
	}

	/**
	 * Starts the Coronavirus Analyzer program.
	 *
	 * @param args Command-line arguments
	 * @return void
	 */
	public static void main(String[] args)
	{
		frame = new CoronavirusAnalyzer();
		frame.run();
    }
}