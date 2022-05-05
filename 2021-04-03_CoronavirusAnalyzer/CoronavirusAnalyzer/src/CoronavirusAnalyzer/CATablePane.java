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
        String [] columnHeading = {"Date","Total Cases","New Cases","Total Deaths", "New Deaths"}; // Declare and initialize column headings.
        tableModel = new DefaultTableModel(columnHeading, 0); // Declare table model with 5 columns and 0 rows.
        table = new JTable(tableModel); // Set JTable model to DefaultTableModel.
        scrollPane = new JScrollPane(table); // Allow the table to have scrollbars.
        this.add(scrollPane); // Add the JTable of DefaultTableModel model within the JScrollPane to the JPanel.
    }

    /**
     * Update the table of statistical values for the current chosen country in the JTextField.
     * 
     * @param covid The instance of CAReadCSV that contains the data read from the CSV files
     * @param country Current country in the JTextField
     * @return void
     */
    public void updateDate(CAReadCSV covid, String country) {
        covid.refreshTable(tableModel, country);
    }    

    /**
     * Update table based on day.
     * 
     * @param covid Has access to CSV data
     * @return void
     */
    public void updateDate(CAReadCSV covid)
    {
        int rows = table.getRowCount();
        if(rows == 0)
            return;

        String date = covid.currentEntry.getKey();

        for(int row = 0; row < rows; row++)
        {
            Object value = tableModel.getValueAt(row, 0);
            if(date.equals(value))
            {
                scrollToRow(row);
                return;
            }
        }
    }

    /**
     * Scrolls and highlights table to current day entry.
     * 
     * @param row The current day row
     * @return void
     */
    private void scrollToRow(int row)
    {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        int max = bar.getMaximum();
        int min = bar.getMinimum();
        int ext = bar.getVisibleAmount(); // Height of scrollbar.
        int val = bar.getValue(); // Top of scrollbar.
        int rows = table.getRowCount();
    
        if  (rows * (val    -min) >= (row+1) * (max - min)   // When current row is above view window.
          || rows * (val+ext-min) <=  row    * (max - min))  // When current row is below view window.
            bar.setValue(row * (max - min) / rows + min);    // Reset the current view window.

        table.clearSelection();
        table.addRowSelectionInterval(row, row);
        this.repaint();
    }
}