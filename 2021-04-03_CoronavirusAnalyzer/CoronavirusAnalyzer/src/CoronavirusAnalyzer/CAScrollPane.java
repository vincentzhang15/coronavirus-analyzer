package CoronavirusAnalyzer;

// Import packages.
import java.awt.Dimension;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Organizes the scrolling of the timeline. Overall the timeline affects the ranking, map, history graph, and data table.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CAScrollPane extends JPanel
{
	private static final long serialVersionUID = 1L;

	// Declare objects.
    private JSlider timeline;
	private CAMapPane map;
	private CATablePane dataTable;
	private CAHistoryPane history;
	private CARankPane ranking;

	// For the purpose of executing the task of scrolling the timeline repeatedly over a fixed interval.
	private final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> handleOfPlay = null;

	/**
	 * Set up slider, which represents the timeline, and add it to the JPanel.
	 */
    public CAScrollPane()
    {
		// Set up the slider and add a ChangeListener to it to respond to changes.
		timeline = new JSlider(); // Create a horizontal slider to represent the timeline.
		timeline.setValue(1); // Set the initial slider position at 1.
		timeline.setPreferredSize(new Dimension(1000, 50));
		timeline.setPaintTicks(true);
		timeline.setPaintLabels(true);
		timeline.setMajorTickSpacing(1);
		timeline.addChangeListener(new ChangeListener() { // https://stackoverflow.com/questions/20153868/using-changelistener-to-fire-changes-in-java-swing
			public void stateChanged(ChangeEvent evt) {
				JSlider source = (JSlider)evt.getSource();
				if (!source.getValueIsAdjusting()) {
					int value = (int)source.getValue();
					map.updateDate(value);
					dataTable.updateDate(map.covid);
					history.updateDate(map.covid);
					ranking.updateDate(map.covid);
				}    
			}
		});

		// Add the slider to the JPanel.
		this.add(timeline);
    }

	/**
	 * Set initial values for ranking, map, history graph, data table, and timeline max value.
	 * 
	 * @param map The JPanel child of map
	 * @param table The JPanel child of table
	 * @param history The JPanel child of history
	 * @param ranking The JPanel child of ranking
	 * @return void
	 */
	public void setDisplay(CAMapPane map, CATablePane table, CAHistoryPane history, CARankPane ranking)
	{
		this.ranking = ranking;
		this.map = map;
		this.history = history;
		this.dataTable = table;
        timeline.setMaximum(map.getCovidNumberOfDays()); // Set the number of tick marks on the slider to the number of CSV day data files.
	}

	/**
	 * Begin the autoscroll if there is no autoscrolling present.
	 * 
	 * @return void
	 */
	public void play()
	{
		if(handleOfPlay != null) // If the timeline is already playing then no need to add an overlapping auto scroller.
			return;
		// Lambda expression simplifies and clarifies the code in place for the @override run() method in Runnable. Also loop the timeline when end is reached.
		handleOfPlay = SCHEDULER.scheduleAtFixedRate (()->timeline.setValue((timeline.getValue()+1) % timeline.getMaximum()), 300, 300, TimeUnit.MILLISECONDS);
	}

	/**
	 * Stop any active autoscrolling.
	 * 
	 * @return void
	 */
	public void stop()
	{
		if(handleOfPlay == null) // If there is no active autoscroller then there is no autoscroller to stop.
			return;
		handleOfPlay.cancel(false); // Wait for in-progress scroll to complete then stop scrolling.
		handleOfPlay = null; // Stop the scrolling.
	}
}