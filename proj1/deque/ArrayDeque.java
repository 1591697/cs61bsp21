package deque;

import java.util.Iterator;

public class ArrayDeque <T> implements Deque<T>{
    private T[] arr;
    private  int head;
    private  int tail;
    private  int size=0;
    @Override
    public void addFirst(T item){
        if(tail!=head){
            if(head==0) {
                arr[head] = item;
                head = arr.length;
                size++;
            }
            else{
                arr[head] = item;
                head--;
                size++;
            }
        }

    }
    public void addLast(T item){
        if(tail!=head){
            if(tail==arr.length) {
                arr[tail] = item;
                tail = 0;
                size++;
            }
            else{
                arr[tail] = item;
                tail++;
                size++;
            }
        }

    }
    public boolean isEmpty(){

        return head==tail;

    }
    public int size(){
        return  size;
    }
    public void printDeque(){
        int num=0;
        if(head>tail) {

            for(int i=head;i<arr.length;i++){
                System.out.println((arr[i]));
            }
            for(int i=0;i<tail;i++){
                System.out.println(arr[i]);
            }
        }
        else{
            num=tail-head;
            for(int i=0;i<num;i++){
                System.out.println(arr[head+i]);
            }
        }


    }
    public T removeFirst(){
        T n=arr[head];
        arr[head]=null;
        if(head==0){
            head=arr.length;
            size--;
        }
        else {
            head++;
            size--;
        }
        return  n;
    }
    public T removeLast(){
        T n=arr[tail-1];
        arr[tail-1]=null;
        if(tail==arr.length){
            tail=0;
            size--;
        }
        else {
            tail--;
            size--;
        }
        return  n;

    }
    public T get(int index){
        if(arr[index]!=null)
            return  arr[index];
        return null;
    }
    public Iterator<T> iterator(){
        return  null;
    }
    public boolean equals(Object o){

        return  false;
    }

    public ArrayDeque(){
        arr=(T[]) new Object[10];
        head=0;
        tail=0;

    }

}
