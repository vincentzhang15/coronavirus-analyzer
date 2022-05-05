package CoronavirusAnalyzer;

/**
 * Node class for CABinarySearchTree.
 * 
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CARankNode
{
   private String country;
   private int rank;
   private CARankNode left, right;

   /**
    * Set initial valueS.
    * 
    * @param country The current country choice
    * @param rank The rank of the country
    */
   public CARankNode(String country, int rank)
   {
      this.country = country;
      this.rank = rank;
      left = right = null;
   }

   /**
    * Set the left CARankNode value.
    *
    * @param left The left node
    * @return void
    */
   public void setLeft(CARankNode left) {
      this.left = left;
   }

   /**
    * Set the right CARankNode value.
    *
    * @param right The right node
    * @return void
    */
   public void setRight(CARankNode right) {
      this.right = right;
   }

   /**
    * Get the left CARankNode value.
    *
    * @return The left CARankNode
    */
   public CARankNode getLeft() {
      return left;
   }

   /**
    * Get the right CARankNode value.
    *
    * @return The right CARankNode
    */
   public CARankNode getRight() {
      return right;
   }

   /**
    * Return the current node country.
    *
    * @return The country
    */
   public String getCountry() {
      return country;
   }

   /**
    * Return the current node rank.
    *
    * @return The rank
    */
   public int getRank() {
      return rank;
   }
}