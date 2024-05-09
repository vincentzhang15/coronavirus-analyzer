package CoronavirusAnalyzer;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.w3c.dom.svg.SVGDocument;

import Stack.LLStack;
import Stack.Stack;

/**
 * Sets up the ranking panel on the left. This includes updating and composing SVG to draw.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CARankPane extends JPanel
{
    private static final long serialVersionUID = 1L;

    // Objects and variables.
    private CASVGCanvas svgCanvas = new CASVGCanvas();
    private List<String> ranking = new ArrayList<String>();

    private String rankingType = "";

    // Constants.
    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 1100;
    private static final int COUNTRIES = 20;

    private static final int BAR_WIDTH = 90; // Max width.
    private static final int BAR_HEIGHT = 20;
    private static final int BAR_SCALE = PANEL_HEIGHT / COUNTRIES;

    private static final int TOP_MARGIN = 80;
    private static final int STATISTIC_ALIGN = 10;
    private static final int BAR_ALIGN = 125;
    private static final int COUNTRY_ALIGN = 250;

    /**
     * Sets up the panel.
     */
    public CARankPane()
    {
        // [Code Private - Contact to View]
    }

    /**
     * Update the ranking according to the data that is desired to be displayed.
     * 
     * @param covid Has access to methods that can obtain the CSV flie data
     * @return void
     */
	public void updateDate(CAReadCSV covid)
    {
        // [Code Private - Contact to View]
    }

    /**
     * Refresh the ranking.
     * 
     * @return void
     */
    private void refresh()
    {
        // [Code Private - Contact to View]

        // Compose the SVG for the image to be painted.
        // Extract highest numerical data value of top COUNTRIES (i.e., 20) countries.
        // Extract the highest value statistic to set anchor for bar width.
            // Draw statistics value, bar, and country name.
        // Generate the SVG document from String strSvg.
        // Pass the document to the canvas for Batik to draw.
    }
}