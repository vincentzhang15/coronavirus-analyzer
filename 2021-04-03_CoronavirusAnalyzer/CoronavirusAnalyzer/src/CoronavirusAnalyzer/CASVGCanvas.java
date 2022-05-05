/* Based on:
Java Batik resize SVG to panel size keeping aspect ratio
https://stackoverflow.com/questions/52422812/java-batik-resize-svg-to-panel-size-keeping-aspect-ratio
resize the svg to the panel size keeping the aspect-ratio
*/

package CoronavirusAnalyzer;

// Import packages.
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.svg.SVGRect;

import java.awt.geom.AffineTransform;
import org.apache.batik.bridge.ViewBox;
import org.w3c.dom.svg.SVGSVGElement;

/**
 * Sets up scaling and ratios for SVG drawing.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CASVGCanvas extends JSVGCanvas
{
    private static final long serialVersionUID = 1L;

    // Variables.
    private short svgScale; // The type of scale.
    private int svgPadding; // Image padding.
    private int type = 0;

    /**
     * Set initial values.
     */
    public CASVGCanvas() {
        this.svgScale = 9;
        this.svgPadding = 5;
    }

    /**
     * Set type.
     * 
     * @param type The type of display (e.g., history pane has separate display)
     * @return void
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * Calculates the window display size for the SVG
     * 
     * @param svgElementIdentifier The SVG element identifer
     * @param svgElement The SVG element
     * @return Data important for drawing
     */
    @Override
    protected AffineTransform calculateViewingTransform(String svgElementIdentifier, SVGSVGElement svgElement) {
        SVGRect svgElementBounds = svgElement.getBBox();
        float[] svgElementBoundsVector;

        if(svgElementBounds == null) // Prevents null pointer exception.
            svgElementBoundsVector = new float[] {0,0,2000,2000};
        else
            switch(type)
            {
                case 1: // History pane.
                    svgElementBoundsVector = new float[] {
                        svgElementBounds.getX(),
                        svgElementBounds.getY(),
                        svgElementBounds.getWidth(),
                        svgElementBounds.getHeight()
                    };
                    break;
                default: // Map pane.
                    svgElementBoundsVector = new float[] {
                        svgElementBounds.getX(),
                        (float)(svgElementBounds.getY() + svgElementBounds.getHeight() * 0.5),
                        svgElementBounds.getWidth(),
                        svgElementBounds.getHeight()
                    };
                    break;
            }

        float svgEemenetScaleToHeight = getHeight() - svgPadding;
        float svgElementScaleToWidth = getWidth() - svgPadding;

        return ViewBox.getPreserveAspectRatioTransform(
            svgElementBoundsVector, svgScale, true,
            svgElementScaleToWidth,
            svgEemenetScaleToHeight
        );
    }

    /**
     * Sets the scale.
     * 
     * @param svgScale The scale
     * @return void
     */
    public void setSvgScale(short svgScale) {
        this.svgScale = svgScale;
    }

    /**
     * Sets the padding.
     * 
     * @param svgPadding The padding
     * @return void
     */
    public void setSvgPadding(int svgPadding) {
        this.svgPadding = svgPadding;
    }
}