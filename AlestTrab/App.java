
import java.io.File;
import java.io.FileReader;
import java.util.*;

import javax.sound.sampled.SourceDataLine;

public class App{
    public static void main(String[] args){
        RedBlackBST<String, String> bst = new RedBlackBST<String, String>();
      
       try{
        Scanner scanner =  new Scanner(new File("Pasta2.txt"));
        scanner.useDelimiter("[-\n]");
        scanner.next();
    
               
   while (scanner.hasNext()) {
      String hash = scanner.next();
      String pokemon = scanner.next();
      bst.add(hash, pokemon);
   }

  System.out.println(bst.positionsCentral());
  System.out.println(bst.get("1"));
  
  
 



  
   
  
  
  scanner.close();
       }catch(Exception e){
         System.out.println(e.getClass());
       }
        

    }

}