// Inspired by the JSVGCanvas Tutorial http://people.apache.org/~clay/batik/svgcanvas.html

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
        createComponents();
    }

    /**
     * Reads and loops through the world map SVG and stores important values.
     * 
     * @return The document containing the SVG file
     * @throws IOException
     */
	private Document loadDocument() throws IOException 
    {
        // https://stackoverflow.com/questions/52422812/java-batik-resize-svg-to-panel-size-keeping-aspect-ratio
        // Create a JSVGCanvas from an inputstream // http://batik.2283329.n4.nabble.com/create-a-JSVGCanvas-from-an-inputstream-td2978748.html
        // Batik load SVG file in SVGGraphics2D // https://stackoverflow.com/questions/51455415/batik-load-svg-file-in-svggraphics2d
        SVGDocument doc = null; // Loading the "world.svg" document into "doc" of class SVGDocument.
        try {
            doc = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName()).createSVGDocument(null, CAMapPane.class.getResourceAsStream("/data/world.svg"));
        }
        catch(IOException ex) {ex.printStackTrace();}

        // How to read XML child node values by using a loop in java
        // https://stackoverflow.com/questions/13356534/how-to-read-xml-child-node-values-by-using-a-loop-in-java
        NodeList nodeList = doc.getRootElement().getChildNodes(); // SVG is XML based. Loop through child nodes.
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if(!node.getNodeName().equals("path"))
                continue;

            SVGElement elem = (SVGElement)node;
            int k = svg.addPath(); // The index denoting the current entry place, i.e., current number of entries.

            // Extract data by country.
            if(elem.hasAttribute("id"))   svg.setId(k, elem.getAttribute("id")); 
            if(elem.hasAttribute("name")) svg.setName(k, elem.getAttribute("name")); 
            if(elem.hasAttribute("class"))svg.setClass(k, elem.getAttribute("class")); 
            if(elem.hasAttribute("d"))    svg.setD(k, elem.getAttribute("d")); 
        }
        return doc;
	}

    /**
     * Get the number of CSV files which corresponds to the number of days.
     * 
     * @return The number of CSV files
     */
    public int getCovidNumberOfDays() {
        return covid.data.size();
    }

    /**
     * Sets up the panel and laods the CSV files and SVG file.
     * 
     * @return void
     */
    public void createComponents()
    {
        JPanel panel = this;
        panel.setLayout(new BorderLayout());
        panel.add(svgCanvas);

        covid.loadDataFiles();  // Load and process all CSV files.
        try {
            loadDocument(); // Load map data from SVG.
        } catch(IOException ex) {ex.printStackTrace();}

        updateDate(-1);
    }

    /**
     * Updates the current day and refreshes map.
     * 
     * @param value The current day value
     */
    public void updateDate(int value)
    {
        currentDay = value;
        refresh();
    }

    /**
     * Refreshes the map.
     * 
     * @return void
     */
    private void refresh()
    {
        covid.updateRankOfConfirmed(currentDay, presentationType);
        int[] cData = new int[2]; // Stores statistics value and rank.

        // Compose the SVG for the image to be painted.
        String strSvg = new String();
        strSvg += "<svg baseprofile=\"tiny\" fill=\"#ececec\" stroke=\"black\" stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\".2\" "
                + "version=\"1.2\" viewbox=\"0 0 2000 957\"  width=\"2000\"  height=\"965\" xmlns=\"http://www.w3.org/2000/svg\">";

        strSvg += "<text x=\"80\" y=\"50\" fill=\"red\" font-size=\"5em\">Coronavirus Data Map</text>";
        strSvg += "<text x=\"80\" y=\"88\" fill=\"blue\" font-size=\"3em\">" + covid.getCurrentDate() + "</text>";

        strSvg += "<g transform=\"translate(0,100)\">";

        for(int i = 0; i < svg.getSize(); i++)
        {
            String id = svg.getId(i);
            String d = svg.getD(i);
            String name = svg.getName(i);
            String cls = svg.getClass(i);

            String cn = name; // "cn" stands for country name.
            if(cls.length() > 0) // The country name will either be the attribute value of "name" or "class" in the SVG file.
                cn = cls;

            int j = 0xff; // Rank colour.
            if(covid.getCountryData(cn, cData)) // If successful retrieval of statistic value and rank.
            {
                j = cData[1]; // cData[1] is the country rank.
                if(j > 0xff) // If rank is greater than 255.
                    j = 0xff; // Set rank colour to 255.
                j = 0xff - j; // Rank colour is darker if ranking is larger.
            }

            strSvg += "<path fill=\"#"+ (String.format("%02X%02X%02X", 0xff,j,0xff)) +"\" "; // Add colour.

            if(id.length() > 0)   strSvg += " id=\"" + id + "\"";
            if(name.length() > 0) strSvg += " name=\"" + name + "\"";
            if(cls.length() > 0)  strSvg += " class=\"" + cls + "\"";
            if(d.length() > 0)    strSvg += " d=\"" + d + "\"";
            strSvg += " />";
        }

        strSvg += "</g>";
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

    /**
     * When the presentation type changes, the map should update as well.
     * 
     * @param type The type of coronavirus data that should be displayed
     * @return void
     */
    public void updatePresentationType(int type)
    {
        presentationType = type;
        refresh();
    }
}