package CoronavirusAnalyzer;

/**
 * Implements a Binary Search Tree to organize country and data ranking.
 * 
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CABinarySearchTree
{
   private CARankNode rootPointer;

   /**
    * Sets initial value of root pointer.
    */
   public CABinarySearchTree() {
      rootPointer = null;
   }

   /**
    * Insert a new CARankNode into the binary search tree based on country and ranking.
    *
    * @param country The name of the country
    * @param rank The rank of the country
    * @return Whether insert is succesful
    */
   public boolean insert(String country, int rank)
   {
      CARankNode newItem = new CARankNode(country, rank);

      // Set first item as the root.
      if(rootPointer == null)
         rootPointer = newItem;
      else
      {
         CARankNode temp = rootPointer;
         while(true)
         {
            // If the country is less than the country of the current node.
            if(country.compareTo(temp.getCountry()) < 0) 
            {
               // If there is no left child, add a left child and exit.
               if(temp.getLeft() == null)
               {
                  temp.setLeft(newItem);
                  return true;
               }
               // Visit the left child of the current node if there is a left child.
               temp = temp.getLeft();
            }
            else // Inserted item country is greater than or equal to the country at the current node.
            {
               // If there is no right child, add a right child and exit.
               if(temp.getRight() == null)
               {
                  temp.setRight(newItem);
                  return true;
               }
               // Visit the right child of the current node if there is a right child.
               temp = temp.getRight();
            }
         }
      }
      return true;
   }

   /**
    * Look for the country in the binary search tree and return the CARankNode.
    * @param country Name of country to be found
    * @return The CARankNode of the country or null if country is not in the tree.
    */
   public CARankNode search (String country)
   {
      // No tree exists.
      if (rootPointer == null)
         return null;

      CARankNode temp = rootPointer;
      while(temp != null)
      {
         // Country found.
         if(country.equals(temp.getCountry()))
            return temp;
         // Search deeper in the tree.
         temp = (country.compareTo(temp.getCountry()) < 0)?temp.getLeft():temp.getRight();
      }
      return null; // Noting is found.
   }
}