package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable,V> implements Map61B<K,V>  {


 public class Node{
  private Node left;
  private Node right;
  private K key;
  private V value;

  public void Node(K key,V value){
   this.key=key;
   this.value=value;

  }
 }
 private Node root;
    public int size = 0;


   public void BSTMap(Node left,Node right){
     root.left=left;
     root.right=right;

   }

    /** Removes all of the mappings from this map. */
   public void clear(){
    root.left = null;
    root.right=null;
    size = 0;
    root=null;
    }
     public Node getnode(Node n,K key){
  if(n == null)
   return null;

   int cmp = key.compareTo(n.key);
   if (cmp > 0) {
   return getnode(n.left, key);
   }
   else if (cmp < 0) {
    return getnode(n.right, key);
   }
   else {
       return n;
   }
    }
    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
     if(getnode(root,key) == null){
      return false;
     }
     return true;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key){
if(containsKey(key)) {
    return getnode(root, key).value;
}
else{
    return null;
}
    }

    /* Returns the number of key-value mappings in this map. */
    public int size(){
return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value){

         root= put(key, value, root);//IIII
        }


    public Node put(K key,V value,Node n){
        if(n==null){
            size++;
            Node node =new Node() ;
            node.Node(key,value);
            return  node;
        }

     if(n.key.compareTo(key)<0){
      n.left=put(key,value,n.left);
     }
    else if(n.key.compareTo(key)>0){
     n.right=put(key,value,n.right);
     }
    else{
     n.value=value;
     }

    return n;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet(){
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key){
     throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
   public V remove(K key, V value){
    throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
       return new BSTmapiterator();

    }
    private   class BSTmapiterator implements  Iterator<K>{
        Stack<Node> t=new Stack<>();
public void BSTmapiterator(){
    BSTmappush(root);
//使用stack

}public void BSTmappush(Node node){
    if(node!=null){
        t.push(node);
        BSTmappush(node.left);
    }
        }
       public boolean hasNext(){
        if(t.isEmpty()){
            return false;
        }
        return true;
       }
       public K next(){
    Node f=new Node();
    f=t.pop();
   BSTmappush(f.right);
   return f.key;
       }

    }
    public static void main(String args[]){

        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        for (int i = 0; i < 6; i++) {
            b.put("hi" + i, 1 + i);
        }
        for (int i = 0; i < 6; i++) {
           System.out.println(b.get("hi"+i));
        }

    }

}
