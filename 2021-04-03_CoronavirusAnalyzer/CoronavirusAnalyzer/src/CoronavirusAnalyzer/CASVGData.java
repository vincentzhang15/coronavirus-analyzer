package CoronavirusAnalyzer;
import java.util.ArrayList;

/**
 * Class to organize SVG data.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
class SVGPath
{
    public String id = "";
    public String name = new String();
    public String cls = new String();
    public String d = new String();
}

/**
 * Stores extracted SVG data.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CASVGData
{
    private ArrayList<SVGPath> mPaths = new ArrayList<SVGPath>();

    /**
     * Add a new path.
     * 
     * @return Index for new entry
     */
    public int addPath()
    {
        mPaths.add(new SVGPath());
        return mPaths.size()-1;
    }

    /**
     * Set the id.
     * 
     * @param i The current position
     * @param id The id value
     * @return void
     */
    public void setId(int i, String id) {
        mPaths.get(i).id = id;
    }

    /**
     * Set the d value.
     * 
     * @param i Current position
     * @param d D value
     * @return void
     */
    public void setD(int i, String d) {
        mPaths.get(i).d = d;
    }

    /**
     * Set the class value.
     * 
     * @param i Current position
     * @param cls Class value
     * @return void
     */
    public void setClass(int i, String cls) {
        mPaths.get(i).cls = cls;
    }

    /**
     * Set the name value.
     * @param i Current position
     * @param name Name value
     * @return void
     */
    public void setName(int i, String name) {
        mPaths.get(i).name = name;
    }

    /**
     * Get total number of paths.
     * 
     * @return The number of paths
     */
    public int getSize() {
        return mPaths.size();
    }

    /**
     * Get id at position i.
     * 
     * @param i Current position
     * @return The id at the current position
     */
    public String getId(int i) {
        return mPaths.get(i).id;
    }

    /**
     * Get the name at position i.
     * 
     * @param i Current position
     * @return The name at the current position
     */
    public String getName(int i) {
        return mPaths.get(i).name;
    }

    /**
     * Get the d value at position i.
     * 
     * @param i The current position
     * @return The d value at the current position
     */
    public String getD(int i) {
        return mPaths.get(i).d;
    }

    /**
     * Get the class value at position i.
     * 
     * @param i Current position
     * @return The class value at the current position
     */
    public String getClass(int i) {
        return mPaths.get(i).cls;
    }
}