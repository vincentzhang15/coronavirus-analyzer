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
        JPanel panel = this;
        panel.setLayout(new BorderLayout());
        panel.add(svgCanvas);
        svgCanvas.setType(1); // Share same format as history pane.
    }

    /**
     * Update the ranking according to the data that is desired to be displayed.
     * 
     * @param covid Has access to methods that can obtain the CSV flie data
     * @return void
     */
	public void updateDate(CAReadCSV covid)
    {
        this.ranking = covid.rankList; // Stores already sorted list of values of a chosen statistic type (total case, new cases, etc.).
        switch(covid.mPresentationType)
        {
            case 2:  rankingType = "New Cases"; break;
            case 3:  rankingType = "Total Deaths"; break;
            case 4:  rankingType = "New Deaths"; break;
            default: rankingType = "Total Cases"; break;
        }
        refresh();
    }

    /**
     * Refresh the ranking.
     * 
     * @return void
     */
    private void refresh()
    {
        // Compose the SVG for the image to be painted.
        String strSvg = new String();
        strSvg += "<svg baseprofile=\"tiny\" fill=\"#ececec\" stroke=\"black\" stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\".2\" "
                + "version=\"1.2\" viewbox=\"0 0 " + PANEL_WIDTH + " " + PANEL_HEIGHT + "\"  "
                + "width=\"" + PANEL_WIDTH + "\"  height=\"" + PANEL_HEIGHT + "\" xmlns=\"http://www.w3.org/2000/svg\">\n";
        strSvg += "<rect x=\"0\" y=\"0\" width=\"" + PANEL_WIDTH + "\" height=\"" + PANEL_HEIGHT + "\" style=\"fill:rgb(255,255,255);stroke-width:3;stroke:white\" />";
        strSvg += "<text x=\"100\" y=\"40\" fill=\"red\" font-size=\"2.5em\">Ranking</text>\n";
        strSvg += "<text x=\"100\" y=\"65\" fill=\"blue\" font-size=\"1.5em\">"+rankingType+"</text>\n";

        Stack reversed = new LLStack();
        // Extract highest numerical data value of top COUNTRIES (i.e., 20) countries.
        for(int j = Math.max(ranking.size()-COUNTRIES+1, 0); j < ranking.size(); j++)
            reversed.push(ranking.get(j)); // Stack allows the data to be used in correct order later.
        // Extract the highest value statistic to set anchor for bar width.
        int maxStat = Integer.parseInt(((String)((LLStack)reversed).peek()).split("\\.")[0].trim());

        for(int j = 0; !reversed.isEmpty(); j++)
        {
            String [] elements = ((String)reversed.pop()).split("\\.");
            String stats = elements[0].trim();
            String country = elements[1];

            double barWidth = Math.max((double)Integer.parseInt(stats)/maxStat * BAR_WIDTH, 1); // If a bar is less than width 1, set it to width 1.

            // Draw statistics value, bar, and country name.
            strSvg += "<text x=\"" + STATISTIC_ALIGN + "\" y=\"" + (TOP_MARGIN+BAR_SCALE*(j+1)) + "\" fill=\"blue\" font-size=\"6mm\">" + stats + "</text>\n";
            strSvg += "<rect x=\"" + BAR_ALIGN       + "\" y=\"" + (TOP_MARGIN+(BAR_SCALE*(j+1))-18.0) + "\" width=\"" + barWidth + "\" height=\""+BAR_HEIGHT+"\" style=\"fill:rgb(255,0,255);stroke-width:3;stroke:rgb(255,0,255)\" />";
            strSvg += "<text x=\"" + COUNTRY_ALIGN   + "\" y=\"" + (TOP_MARGIN+BAR_SCALE*(j+1)) + "\" fill=\"blue\" font-size=\"6mm\">" + country + "</text>\n";
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