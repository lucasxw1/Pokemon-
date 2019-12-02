public class App{
    public static void main(String[] args){
        RedBlackBST<Integer, String> bst = new RedBlackBST<Integer, String>();
        bst.put(1, "a");
        bst.put(2,"b");
        bst.put(3,"c");
          bst.put(4, "d");
        bst.put(9,"e");
        bst.put(3,"f");
        
        System.out.println(bst.getParent(1));
         

    }

}