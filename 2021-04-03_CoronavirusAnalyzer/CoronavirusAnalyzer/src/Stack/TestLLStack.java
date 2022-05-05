package Stack;
// The "TestLLStack" class.
//import LLStack;
//import Node;
   import java.util.*;

   public class TestLLStack
   {
      static Scanner sc = new Scanner(System.in);
   
      final static int PUSH = 1;
      final static int POP = 2;
      final static int FULL = 3;
      final static int EMPTY = 4;
      final static int PEEK = 5;
      final static int EXIT = 0;
   
      static boolean quit = false;
      static LLStack websites = new LLStack ();
   
   
      public static void displayMenu ()
      {
      
         System.out.println ("List Menu\n");
         System.out.println (PUSH + " : Add an item to the stack");
         System.out.println (POP + " : Delete an item from the stack");
         System.out.println (FULL + " : Find out if the stack is full");
         System.out.println (EMPTY + " : Find out if the stack is empty");
         System.out.println (PEEK + " : Return the item at the top of the stack");
         System.out.println (EXIT + " : Exit the program");
      
      }
   
   
      public static void executeOptions ()
      {
         int option;
      
         String address = new String ("");
      
         System.out.print ("Enter your menu option: ");
         option = sc.nextInt ();
         System.out.println ("\n");
      
         switch (option)
         {
            case PUSH:
               System.out.print ("Enter the item value to be inserted: ");
               address = sc.next();
               websites.push ((Object) address);
               break;
         
            case POP:
               address = (String) websites.pop ();
               if (address != null)
                  System.out.println ("The item popped from the list = " + address);
               else
                  System.out.println ("The list was empty");
               break;
         
            case FULL:
               if (websites.isFull ())
                  System.out.println ("Stack is full");
               else
                  System.out.println ("Stack is not full");
            
               break;
         
            case EMPTY:
               if (websites.isEmpty ())
                  System.out.println ("The stack is empty");
               else
                  System.out.println ("The stack is not empty");
               break;
         
            case PEEK:
               if (websites.isEmpty ())
                  System.out.println ("The stack is empty");
               else
                  System.out.println ("The item at the top of the stack is " +websites.peek());
               break;
         
         
            case EXIT:
               System.out.println ("Good-bye");
               quit = true;
               System.exit(0);
               break;
         
            default:
               System.out.println ("Oops... that's not a valid option");
         
         }
      
      
      }
   
   
      public static void main (String [] args)
      {
      
         while (!quit)
         {
            displayMenu ();
         
            executeOptions ();
            System.out.print ("\nEnter any key to continue: ");
            sc.nextLine ();
            System.out.println();
         }
      
      
      // Place your program here.  'c' is the output console
      } // main method
   } // ListTester class


