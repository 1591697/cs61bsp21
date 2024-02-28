package deque;

import java.util.Iterator;

public  class  LinkedListDeque<T>implements Deque<T> {
    class  node{ private T item;
        private   node pre;
        private   node next;
       node(node pre,node next,T item){
           this.pre=pre;
           this.next=next;
           this.item=item;
       }
    }
    private int size;
    private node sen;
    @Override
    public void addFirst(T item){
    size++;
    node k=new node(null,null,item);
    k.next=sen.next;
    sen.next.pre=k;
    sen.next=k;
    k.pre=sen;

    }
    public void addLast(T item){
        size++;
        node k=new node(null,null,item);


            k.pre=sen.pre;
            sen.pre.next=k;
        k.next=sen;
        sen.pre=k;

    }
    public boolean isEmpty(){
    if(sen.next==sen||sen.pre==sen){
        return true;
    }
    return false;


    }
    public int size(){
return size;
    }
    public void printDeque(){
    node k=new node(null,null,null);
        k=sen;
    if(size==0){
        System.out.println("null");
    }
    for(int i=0;i<size;i++){

        System.out.println(k.next.item);
        k=k.next;
    }


    }
    public T removeFirst(){
    T k;

    if(size==0){
        return null;
    }

        size--;
        k=sen.next.item;
        sen.next.next.pre=sen;
        sen.next=sen.next.next;
        return k;


    }
    public T removeLast(){
        T k;

        if(size==0){
            return null;
        }

            k=sen.pre.item;
        size--;
            sen.pre.pre.next=sen;
            sen.pre=sen.pre.pre;
            return k;



    }
    public T get(int index){
        node k=new node(null,null,null);
        k=sen.next;
        if(index>=size){
            return null;
        }
        else{

                if(index==0){
                    return k.item;
                }
                for (int i = 0; i <index; i++) {
                    k = k.next;
                }
                return k.item;

        }

    }
//    public Iterator<T> iterator(){
//
//    }
//    public boolean equals(Object o){
//
//
//    }

    public LinkedListDeque(){
    sen=new node(null,null,null);
    sen.next=sen;
    sen.pre=sen;
    size=0;
    }
    public T getRecursive(int index){
        if(index>=size){
            return null;
        }

        return  getRecursive(0,index,sen.next);
    }
    public T getRecursive(int pos,int index,node x){
if(pos==index){
    return x.item;
}
return getRecursive(pos +1,index,x.next);
    }
    public Iterator<T> iterator() {
        return new DequeIterator();
    }
    public class DequeIterator implements Iterator<T>{
        private int wizPos;

        private DequeIterator() {
            wizPos = 0;
        }
        public boolean hasNext(){
            return size>wizPos;
        }
        public T next(){
            T item = get(wizPos);
            wizPos += 1;
            return item;
        }

    }
    //equals 的写法。
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o==null){
            return false;
        }
        if(!(o instanceof Deque)){
            return false;
        }
        Deque<T> oa=(Deque<T>) o;
        if(this.size() != oa.size()){
            return false;    }
        for(int i = 0;i<oa.size();i++){
            if(oa.get(i).equals(this.get(i))){
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

}
