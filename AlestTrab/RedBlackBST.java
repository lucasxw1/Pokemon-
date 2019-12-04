
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/** Arvore Rubro Negra adaptada para TAD; 
 *  Lucas Garcia e Suane Vallim;
 * 
 * 
 * Comentario dos autores no fim da classe.
*  @author Robert Sedgewick
*  @author Kevin Wayne
*/

public class RedBlackBST<Key extends Comparable<Key>, Value> {


    

    // BST helper node data type
    private final class Node {
        private Key key;           // key
        private Value val;         // associated data
        public Node left, right;   // nodos da esquerda e direita
        public Node father;		  // nodo pai
        public boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    // Atributos da arvore binaria
    private Node root;     // raiz da arvore
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    //private Node nil = new Nodo //nodo nill é essencial para o funcionamento do algoritmo, precisa ser instanciado
    private int count; //total de elementos da arvore
   
   
    public RedBlackBST() {
    	count = 0;
    	//root = nil;
    }
        
    
    ///////////// INTERFACE DE METODOS SOLICITADOS ***********************************************************************************************************************************
     
    /**
     * Coloca dentro da arvore o nodo chave-valor especificado, e sobreescreve o valor de algum node caso a key passada ja esteja na arvore.
     * @param key, é a chave que guarda um valor
     * @param val, valor guardado na chave
     */
    
    /**
     * Limpa a arvore.
     */
    public void clear() {
        count = 0;
        root = null;
    }
    
    /**
     * @return True caso a arvore esteja vazia, false caso nao esteja;
     */
    public boolean isEmpty() {
        return (root == null);
    }
    
    /**
     * Verifica se a arvore contem a chave especificada;
     * @param key 
     * @return True caso a chave esteja na arvore, falso caso nao esteja;
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }
    
    /**
     * Adiciona novos nodos a arvore, caso o elemento passado seja null, lança exceção.
     * @param key, é a chave que guarda um valor
     * @param val, valor guardado na chave
     */
    public void add(Key key, Value val) {
        if (key == null || val == null) 
        	throw new IllegalArgumentException("Por favor, insira uma string diferente de null");

        root = addAux(root, key, val);
        root.color = BLACK;
        // assert check();
    }
    /** Metodo que faz a inserção e faz as rotações necessarias para a arvore se manter balanceada
    */
    private Node addAux(Node h, Key key, Value val) { 
        if (h == null) return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = addAux(h.left,  key, val); 
        else if (cmp > 0) h.right = addAux(h.right, key, val); 
        else              h.val   = val;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = sizeAux(h.left) + sizeAux(h.right) + 1;

        return h;
    }
    /**
     * 
     * @param key 
     * @return O pai da chave especificada
     */
     public Value getParent(Key key) {
         if (key == null) throw new IllegalArgumentException("argument to get() is null");
         return getParentAux(root, key);
     }
    /** 
     * Baseado no metodo get(), apenas guardando uma referencia para o nodo anterior.
    */
     private Value getParentAux(Node x, Key key) {
        Node aux = x;
         while (x != null) {
             int cmp = key.compareTo(x.key);
             if( key.compareTo(root.key) == 0) {
                 return null;}
             if      (cmp < 0) {aux = x; x = x.left;}
             else if (cmp > 0) {aux = x; x = x.right;}
             else       {      return aux.val;}
         }
         return null;
     }
   
     /**
     * Devolve o valor respectivo a chave especificada;
     * @param key 
     * @return o valor respecitov a chave, caso a chave nao exista na tabela, retorna null;
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return getAux(root, key);
    }
    private Value getAux(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }
     /**
     * Retorna a altura da arvore.
     * @return altura da arvore, uma arvore com 1 nodo tem altura 0, se esta vazia retorna -1;
     */
    public int height() {
        return heightAux(root);
    }
    private int heightAux(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(heightAux(x.left), heightAux(x.right));
    }
     /**
     * retona o total de nodos na arvore
     * @return o total de nodos da arvore;
     */
    public int size() {
        return sizeAux(root);
    }
    private int sizeAux(Node x) {
        if (x == null) return 0;
        return x.size;
    } 
     /**
 

             //METODOS RETIRADOS DAS AULAS E ADAPTADOS PARA A ARVORE RUBRO NEGRA;
   /***************************************************************************/
    /**
     * Devolve um clone da arvore
     * @return um clone da arvore.
     */
    public RedBlackBST<Key, Value> clone(){
        RedBlackBST<Key, Value> clone = new RedBlackBST<>();
        cloneAux(root, clone);
        return clone;    
    }
    private void cloneAux(Node n, RedBlackBST<Key, Value> clone){
        List<Node> aux = positionsCentralX();
        for(Node x: aux){
            clone.add(x.key, x.val);
        }
    }
    private List<Node> positionsCentralX() {
        List<Node> res = new ArrayList<>();
        positionsCentralAux(root, res);
        return res;
    }
    private void positionsCentralAux(Node n, List<Node> res) {
        if (n != null) {
            positionsCentralAux(n.left, res);
            res.add(n);
            positionsCentralAux(n.right, res);
        }
    }
      /**
      * Retorna uma lista com todos os elementos da arvore na ordem de caminhamento central.
     * @return List<key> lista com os elementos da arvore
     */
    public List<Key> positionsCentral() {
        List<Key> res = new ArrayList<>();
        for(int i = 0; i<positionsCentralX().size();i++){
            res.add(positionsCentralX().get(i).key);
        }
        return res;
    }
    /** 
     * Retorna uma lista com todos os elementos da arvore na ordem de 
     * caminhamento pos-fixada.
     * @return List<key> lista com os elementos da arvore
     */
    public List<Key> positionsPos() {
        List<Key> res = new ArrayList<>();
        positionsPosAux(root, res);
        return res;
    }
    private void positionsPosAux(Node n, List<Key> lista){
        if(n != null){
            positionsPosAux(n.left, lista);
            positionsPosAux(n.right, lista);
            lista.add(n.key);
        }
    }
    /**
     * Retorna uma lista com todos os elementos da arvore na ordem de caminhamento pre-fixada.
     * @return List<key> lista com os elementos da arvore
     */
    public List<Key> positionsPre() {
        List<Key> res = new ArrayList<>();
        positionsPreAux(root, res);
        return res;
    }
    private void positionsPreAux(Node n, List<Key> res) {
        if (n != null) {
            res.add(n.key); 
            positionsPreAux(n.left, res); 
            positionsPreAux(n.right, res); 
        }
    }
    /** 
     * Retorna uma lista com todos os elementos da arvore na ordem de 
     * caminhamento em largura. 
     * @return List<Key> com os elementos da arvore
     */  
    public List<Key> positionsWidth() {
        List<Key> res = new ArrayList<>();
        Queue<Node> fila = new Queue<>();
        fila.enqueue(root);
        while(!fila.isEmpty()) {
            Node n = fila.dequeue();
            res.add(n.key);
            if (n.left!=null)
                fila.enqueue(n.left);
            if (n.right!=null)
                fila.enqueue(n.right);
        }
        return res;
    }


        ///////////// FIM INTERFACE DE METODOS SOLICITADOS ***********************************************************************************************************************************






        
                    //METODOS DE AUXILIO E METODO DELETE(Key);
   /***************************************************************************/


 
    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }  
    /**
     * Removes the smallest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }
    /**
     * Removes the largest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) { 
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) { 
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }
    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = sizeAux(h.left) + sizeAux(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = sizeAux(h.left) + sizeAux(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = sizeAux(h.left) + sizeAux(h.right) + 1;
        return h;
    }
    /**
     * Returns the smallest key in the symbol table.
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 
    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    /**
     * Returns the largest key in the symbol table.
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) { 
        // assert x != null;
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 


   
/**
 *  The {@code BST} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  <p>
 *  This implementation uses a <em>left-leaning red-black BST</em>. 
 *  The <em>put</em>, <em>get</em>, <em>contains</em>, <em>remove</em>,
 *  <em>minimum</em>, <em>maximum</em>, <em>ceiling</em>, <em>floor</em>,
 *  <em>rank</em>, and <em>select</em> operations each take
 *  &Theta;(log <em>n</em>) time in the worst case, where <em>n</em> is the
 *  number of key-value pairs in the symbol table.
 *  The <em>size</em>, and <em>is-empty</em> operations take &Theta;(1) time.
 *  The <em>keys</em> methods take
 *  <em>O</em>(log <em>n</em> + <em>m</em>) time, where <em>m</em> is
 *  the number of keys returned by the iterator.
 *  Construction takes &Theta;(1) time.
 *  <p>
 *  For alternative implementations of the symbol table API, see {@link ST},
 *  {@link BinarySearchST}, {@link SequentialSearchST}, {@link BST},
 *  {@link SeparateChainingHashST}, {@link LinearProbingHashST}, and
 *  {@link AVLTreeST}.
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
 

   
}
