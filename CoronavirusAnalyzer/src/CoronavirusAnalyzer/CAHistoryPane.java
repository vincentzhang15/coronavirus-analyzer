package CoronavirusAnalyzer;

// Import packages.
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.io.ByteArrayInputStream;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.w3c.dom.svg.SVGDocument;

/**
 * Sets up the graph and updates according to changes in day, desired data type, and country.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CAHistoryPane  extends JPanel
{
    private static final long serialVersionUID = 1L;

    // Objects and variables.
    private CASVGCanvas svgCanvas = new CASVGCanvas();
    private Map<String, Integer> history = new TreeMap<String, Integer>(); // Date and statistical value for a specific country.
    private String country = "";
    private String currentDay = "";

    // Constants
    private static final double GRAPH_WIDTH = 1000;
    private static final double GRAPH_HEIGHT = 1000;

    /**
     * Set up the panel.
     */
    public CAHistoryPane()
    {
        // [Code Private - Contact to View]
    }

    /**
     * Initiates the update of the graph.
     * 
     * @param covid Has access to CSV data
     * @param country The current country to display the graph for
     * @return void
     */
    public void updateDate(CAReadCSV covid, String country)
    {
        // [Code Private - Contact to View]
    }

    /**
     * Update based on new day.
     * 
     * @param covid Has access to CSV data
     * @return void
     */
	public void updateDate(CAReadCSV covid)
    {
        // [Code Private - Contact to View]
    }

    /**
     * Updates the graph by composing the SVG.
     * 
     * @return void
     */
    private void refresh()
    {
        // Compose the SVG for the image to be painted.
        // [Code Private - Contact to View]

        // Find the maximum and minimum statistical value.
        // [Code Private - Contact to View]

        // Set translation values of the line graph.
        // [Code Private - Contact to View]

        // Draw the axes.
        // [Code Private - Contact to View]

        // Compose the line graph.
        // [Code Private - Contact to View]


        // Generate the SVG document from String strSvg.
        // [Code Private - Contact to View]

        // Pass the document to the canvas for Batik to draw.
        // [Code Private - Contact to View]
    }
}