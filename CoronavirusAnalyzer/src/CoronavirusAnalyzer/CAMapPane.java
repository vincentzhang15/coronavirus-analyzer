package CoronavirusAnalyzer;

// Import packages.
import javax.swing.JPanel;

import java.io.IOException;
import java.io.ByteArrayInputStream;

import java.awt.Canvas;
import java.awt.BorderLayout;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;

/**
 * Loads and processes the SVG world map file and composes SVG map when requested.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CAMapPane extends JPanel
{
    private static final long serialVersionUID = 1L;

    // Declare objects and instantiate classes.
    private CASVGCanvas svgCanvas = new CASVGCanvas();
    private CASVGData svg = new CASVGData();
    public CAReadCSV covid = new CAReadCSV();

    // Declare and initialize variables.
    private int currentDay = -1;
    private int presentationType = 1; // Set the default statistic type to total confirmed cases.

    /**
     * Initiates the SVG processing.
     */
    public CAMapPane() {
        // [Code Private - Contact to View]
    }

    /**
     * Reads and loops through the world map SVG and stores important values.
     * 
     * @return The document containing the SVG file
     * @throws IOException
     */
	private Document loadDocument() throws IOException 
    {
        // [Code Private - Contact to View]
	}

    /**
     * Get the number of CSV files which corresponds to the number of days.
     * 
     * @return The number of CSV files
     */
    public int getCovidNumberOfDays() {
        // [Code Private - Contact to View]
    }

    /**
     * Sets up the panel and laods the CSV files and SVG file.
     * 
     * @return void
     */
    public void createComponents()
    {
        // [Code Private - Contact to View]
    }

    /**
     * Updates the current day and refreshes map.
     * 
     * @param value The current day value
     */
    public void updateDate(int value)
    {
        // [Code Private - Contact to View]
    }

    /**
     * Refreshes the map.
     * 
     * @return void
     */
    private void refresh()
    {
        // [Code Private - Contact to View]

        // Compose the SVG for the image to be painted.
        // Generate the SVG document from String strSvg.
        // Pass the document to the canvas for Batik to draw.
    }

    /**
     * When the presentation type changes, the map should update as well.
     * 
     * @param type The type of coronavirus data that should be displayed
     * @return void
     */
    public void updatePresentationType(int type)
    {
        // [Code Private - Contact to View]
    }
}