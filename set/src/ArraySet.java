import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.Integer;
public class ArraySet<T>  implements Iterable<T>{
    private T[] items;
    private  int size;
    public ArraySet(){
    items=(T[]) new Object[100];
    size=0;
    }
    public  boolean contains(T x){
        for(int i=0;i<size;i++){
            if(x.equals(items[i]))//通过equals 比较
                return true;
        }
        return false;
    }
    public void add(T x){
        if(contains(x)){
            return;
        }
        items[size]=x;
        size++;
        return;
    }
    public int size(){
        return  size;
    }
    public  static  void main(String[] args){
        ArraySet<String> s=new ArraySet<>();
        s.add("fish");
        s.add("cat");
        s.add("house");
        s.add("fish");
        System.out.println(s.contains("cat"));
        System.out.println(s.contains("kiki"));
        Iterator<String> ss=s.iterator();
        while(ss.hasNext()){
            String i=ss.next();
            System.out.println(i);
        }
        for(String i:s){
            System.out.println(i);
        }
    }

    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }
    private  class ArraySetIterator implements Iterator<T>{
    private int wi;

public  ArraySetIterator(){
    wi=0;
}

        public  boolean hasNext(){
        return wi<size;
    }
    public  T next(){
T reit=items[wi];
wi++;
return reit;
    }
    }
}
