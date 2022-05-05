package CoronavirusAnalyzer;

/**
 * Organizes and calculates the four data types to be stored.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class RecordItem
{
    // Variables needs to be accessable by other classes.
    int totalCases; // Total cases.
    int newCases; // New cases.
    int totalDeaths; // Total deaths.
    int newDeaths;

    /**
     * Set initial values.
     * 
     * @param c Number of confirmed, which is total cases.
     * @param d Total deaths
     */
    public RecordItem(int c, int d)
    {
        totalCases = c;
        totalDeaths = d;
    }

    /**
     * Find the new cases and new deaths.
     * 
     * @param c Current day total cases.
     * @param d Current day total deaths.
     * @return void
     */
    public void setPrevious(int c, int d)
    {
        newCases = totalCases - c;
        newDeaths = totalDeaths - d;
    }

    /**
     * Get the statistical value according to the presentation type.
     * 
     * @param presentationType The type of desired statistical value
     * @return The statistical value
     */
    public int getValue(int presentationType)
    {
        switch(presentationType)
        {
            case 2: return newCases;
            case 3: return totalDeaths;
            case 4: return newDeaths;
            default: return totalCases;
        }
    }
}