package Stack;
// The "Stack" class.
   //import Node;

   public class LLStack implements Stack
   {
      private Node top;
   
      public LLStack ()
      {
         top = null;
      }
   
   
      public boolean isEmpty ()
      {
         return top == null;
      }
   
   
      public Object pop ()
      {
         if (top != null)
         {
            Object data = top.getData ();
            top = top.getNext ();
            return data;
         }
         else
            return null;
      
      }
   
   
      public boolean push (Object item)
      {
         Node temp = new Node(item, top);
         top = temp;
         return true;
      }
   
   
      public boolean isFull ()
      {
         return false;
      }
      
      public Object peek() 
      {
         if (top == null)
            throw new RuntimeException("peek: empty stack");
         else {
            Object data = top.getData ();
            return data;
         }
      }
   } // Stack class
