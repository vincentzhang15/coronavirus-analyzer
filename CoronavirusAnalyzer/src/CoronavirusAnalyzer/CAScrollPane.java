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
		// [Code Private - Contact to View]

		// Set up the slider and add a ChangeListener to it to respond to changes.

		// Add the slider to the JPanel.
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
		// [Code Private - Contact to View]
	}

	/**
	 * Begin the autoscroll if there is no autoscrolling present.
	 * 
	 * @return void
	 */
	public void play()
	{
		// [Code Private - Contact to View]

		// Lambda expression simplifies and clarifies the code in place for the @override run() method in Runnable. Also loop the timeline when end is reached.
	}

	/**
	 * Stop any active autoscrolling.
	 * 
	 * @return void
	 */
	public void stop()
	{
		// [Code Private - Contact to View]
	}
}