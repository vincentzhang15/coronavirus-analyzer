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
        JPanel panel = this;
        panel.setLayout(new BorderLayout());
        panel.add(svgCanvas);
        svgCanvas.setType(1); // 1 is for history pane.
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
        covid.refreshHistory((TreeMap<String, Integer>)history, country);
        this.country = country;
        refresh();
    }

    /**
     * Update based on new day.
     * 
     * @param covid Has access to CSV data
     * @return void
     */
	public void updateDate(CAReadCSV covid)
    {
        currentDay = covid.currentEntry.getKey();
        refresh();
    }

    /**
     * Updates the graph by composing the SVG.
     * 
     * @return void
     */
    private void refresh()
    {
        // Compose the SVG for the image to be painted.
        String strSvg = new String();
        strSvg += "<svg baseprofile=\"tiny\" fill=\"#ececec\" stroke=\"black\" stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\".2\" "
                + "version=\"1.2\" viewbox=\"0 0 " + GRAPH_WIDTH + " " + GRAPH_HEIGHT + "\"  "
                + "width=\"" + GRAPH_WIDTH + "\"  height=\"" + GRAPH_HEIGHT + "\" xmlns=\"http://www.w3.org/2000/svg\">\n";

        strSvg += "<text x=\"400\" y=\"50\" fill=\"red\" font-size=\"5em\">" + country + "</text>\n";
        strSvg += "<text x=\"870\" y=\"950\" fill=\"blue\" font-size=\"3em\">Time</text>\n";
        strSvg += "<text x=\"22\" y=\"72\" fill=\"blue\" font-size=\"3em\">Magnitude</text>\n";

        // Find the maximum and minimum statistical value.
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        {
            Iterator<Map.Entry<String, Integer>> i = history.entrySet().iterator();
            while(i.hasNext()) {
                Integer v = i.next().getValue(); // Map value is the statistical value of the current day of a specific country.
                if(min > v) min = v;
                if(max < v) max = v;
            }
        }

        if(max == min)
            return;
        if(history.size() == 0)
            return;

        // Set translation values of the line graph.
        double x0 = 10;
        double y0 = 960;

        // Draw the axes.
        strSvg += "<line x1=\""+x0+"\" y1=\""+y0+"\" x2=\""+(x0+950)+"\" y2=\""+y0+"\" style=\"stroke:rgb(0,0,255);stroke-width:2\" />\n"; // The x-axis.
        strSvg += "<line x1=\""+x0+"\" y1=\""+y0+"\" x2=\""+x0+"\" y2=\""+(y0-920)+"\" style=\"stroke:rgb(0,0,255);stroke-width:2\" />\n"; // The y-axis.

        // Compose the line graph.
        {
            double x1 = x0;
            double y1 = x0;

            Iterator<Map.Entry<String, Integer>> i = history.entrySet().iterator();
            int initialSize = history.size();
            for(int j = 0; i.hasNext(); j++)
            {
                if(history.size() != initialSize)
                    return; // Prevents ConcurrentModificationException when user repeatedly change data display choice in short time interval. Also improves performance.
                Map.Entry<String, Integer> m = i.next(); // Map key is the date, Map value is the statistical value.
                double x2 =  x0 + j * (GRAPH_WIDTH  / history.size()); // Multiply the x-scale. The divisor is the number of days.
                double y2 =  y0 - (m.getValue() - min) * (GRAPH_HEIGHT / (max - min)); // Multiply the y-scale. The divisor is the range of values.

                if(j > 0) // Connect adjacent data points with a line, i.e., connect (x1, y1) and (x2, y2).
                    strSvg += "<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" style=\"stroke:rgb(255,0,255);stroke-width:2\" />\n";
                x1 = x2;
                y1 = y2;

                if(currentDay.equals(m.getKey())) // Draw a circle to keep track of the current day.
                    strSvg += "<circle cx=\""+x2+"\" cy=\""+y2+"\" r=\"8\" stroke=\"black\" stroke-width=\"3\" fill=\"red\" />\n";
            }
        }
        strSvg += "</svg>";

        // Generate the SVG document from String strSvg.
        SVGDocument doc = null;
        try {
            doc = (new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName())).createSVGDocument(null, new ByteArrayInputStream(strSvg.getBytes("UTF-8")));
        }
        catch (Exception ex) {ex.printStackTrace();}

        // Pass the document to the canvas for Batik to draw.
        svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
        svgCanvas.setDocument(doc);
    }
}