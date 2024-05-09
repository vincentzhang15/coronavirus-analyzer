package CoronavirusAnalyzer;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;

/**
 * Sets up the table at the bottom right of the frame. Autoscroll to current day entry.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CATablePane extends JPanel
{
    private static final long serialVersionUID = 1L;

    // Objects
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     * Set up the table settings.
     */
    public CATablePane()
    {
		// [Code Private - Contact to View]
    }

    /**
     * Update the table of statistical values for the current chosen country in the JTextField.
     * 
     * @param covid The instance of CAReadCSV that contains the data read from the CSV files
     * @param country Current country in the JTextField
     * @return void
     */
    public void updateDate(CAReadCSV covid, String country) {
		// [Code Private - Contact to View]
    }    

    /**
     * Update table based on day.
     * 
     * @param covid Has access to CSV data
     * @return void
     */
    public void updateDate(CAReadCSV covid)
    {
		// [Code Private - Contact to View]
    }

    /**
     * Scrolls and highlights table to current day entry.
     * 
     * @param row The current day row
     * @return void
     */
    private void scrollToRow(int row)
    {
		// [Code Private - Contact to View]
    }
}