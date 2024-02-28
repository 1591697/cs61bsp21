package deque;


import java.util.Iterator;

public class ArrayDeque <T> implements Deque<T>{
     private T[] arr;
    private  int head ;
    private  int tail;
    private  int size ;



    public void resize(){
        T[] a=(T[])new Object[arr.length*2];
//            for(int i = 0;i < arr.length;i++){
//                if(head+i < arr.length)
//                a[i] = arr[head+i];
//                if(head+i >= arr.length){
//                    a[i] = arr[i-arr.length+head];
//                }
//            }
            for(int i = 0;i < tail;i++){
                a[i] = arr[i];
            }
            for(int i = head+1 ;i < arr.length;i++){
                a[i+arr.length] = arr[i];
            }
            head = arr.length+head;

            arr = a;
    }
    public void rresize(){
        T[] a=(T[])new Object[arr.length/2];
        int k=0;
//            for(int i = 0;i < arr.length;i++){
//                if(head+i < arr.length)
//                a[i] = arr[head+i];
//                if(head+i >= arr.length){
//                    a[i] = arr[i-arr.length+head];
//                }
//            }
        if(head > tail) {
            for (int i = 0; i < tail; i++) {
                a[i] = arr[i];
            }
            for (int i = head + 1 - arr.length / 2; i < arr.length / 2; i++) {
                a[i] = arr[i + arr.length / 2];
            }
            head = head - arr.length/2;

            arr = a;
        }
        else {
            for(int i = head;i < tail; i ++){
                a[k] = arr[i];
                k ++;
            }
            head = 0;
            tail = 0;
            arr = a;
        }
    }
    public void addFirst(T item){
        if(head == tail){
            resize();
        }

        if(head > 0&& head < arr.length){
            arr[head] = item;
            head--;
        }
        else{
            arr[head] = item;
            head = arr.length-1;
        }
        size++;
    }
    public void addLast(T item){
        if(head == tail){
            resize();
        }
        arr[tail] = item;
        if(tail < arr.length-1){
            tail++;
        }
        else{
          tail = 0;
        }
        size++;
    }

    public boolean isEmpty(){
        return size == 0 ?true:false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        if(size != 0){
           for(int i = 0;i < size;i++){
               System.out.println(get(i));
           }
        }
    }
    public T removeFirst(){
        T i ;
        if(size<arr.length/2 && size>4){
            rresize();
        }
        if(!isEmpty() ){
            size--;
           if(head == arr.length-1){
               head = 0;
               i = arr[head];
               arr[head] = null;

           }
           else {
               i = arr[head+1];
               arr[head+1] = null;
               head++;
           }
                return i;
        }
      return null;
    }
    public T removeLast(){
        T i ;
        if(size<arr.length/2 && size>4){
            rresize();
        }
        if(!isEmpty() ){
            size--;
            if(tail == 0){
                tail = arr.length -1;
                i = arr[tail];
                arr[tail] = null;

            }
            else {
                i = arr[tail-1];
                arr[tail-1] = null;
                tail--;
            }
            return i;
        }
        return null;
    }
    public T get(int index){
        if(index > size){
            return null;
        }
        else {
            if (head == arr.length - 1) {
                return arr[index];
            }

            return arr[(index + head + 1) % arr.length];

        }
    }
    public  ArrayDeque(){
        arr=(T[])new Object[8];
        head=0;
        tail=1;
        size=0;
    }
//    public boolean isfull(){
//        if(size>=arr.length)
//        return true;
//        return false;
//    }
//    public  void resize(){
//
//            T[] a=(T[])new Object[arr.length*2];
////            for(int i=0;i<arr.length;i++){
////                if(head+i<arr.length)
////                a[i]=arr[head+i];
////                if(head+i>=arr.length){
////                    a[i]=a[i-arr.length+head];
////                }
////            }
//            for(int i=0;i<tail;i++){
//                a[i]=arr[i];
//            }
//            for(int i=head+1 ;i<arr.length;i++){
//                a[i+arr.length]=arr[i];
//            }
//            head=arr.length+head;
//
//            arr=a;
//        }
//
//
//    @Override
//    public void addFirst(T item) {
//        if (!isfull()) {
//            if(head==0||tail==0){
//                tail=1;
//            }
//            if (head == 0) {
//                arr[head] = item;
//                head = arr.length - 1;
//                size++;
//            } else {
//                arr[head] = item;
//                head--;
//                size++;
//            }
//        }
//        else{
//            resize();
//            if (head == 0) {
//                arr[head] = item;
//                head = arr.length - 1;
//                size++;
//            } else {
//                arr[head] = item;
//                head--;
//                size++;
//            }
//        }
//    }
//
//
//    public void addLast(T item){
//        if(size<arr.length-2){
//            if(head==0||tail==0){
//                head=arr.length-1;
//            }
//            if(tail==arr.length-1) {
//                arr[tail] = item;
//                tail = 0;
//                size++;
//            }
//            else{
//                arr[tail] = item;
//                tail++;
//                size++;
//            }
//        }
//        else{
//            resize();
//            if(tail==arr.length-1) {
//                arr[tail] = item;
//                tail = 0;
//                size++;
//            }
//            else{
//                arr[tail] = item;
//                tail++;
//                size++;
//            }
//
//        }
//
//    }
//    public boolean isEmpty(){
//        if (size == 0) {
//
//            return true;
//        }
//return false;
//    }
//    public int size(){
//        return  size;
//    }
//    public void printDeque(){
//        int num;
//        if(head>tail) {
//
//            for(int i=head;i<arr.length;i++){
//                System.out.println((arr[i]));
//            }
//            for(int i=0;i<tail;i++){
//                System.out.println(arr[i]);
//            }
//        }
//        else{
//            num=tail-head;
//            for(int i=0;i<num;i++){
//                System.out.println(arr[head+i]);
//            }
//        }
//
//
//    }
//    public T removeFirst(){
//        if(!isEmpty()) {
//
//            if (head ==arr.length-1) {
//                T n = arr[0];
//    arr[0]=null;
//
//                head = 0;
//                size--;
//                return n;
//            } else {
//                T n=arr[head+1];
//                arr[head+1] = null;
//
//                head++;
//                size--;
//                return n;
//            }
//
//
//        }
//        return null;
//    }
//    public T removeLast(){
//        if(!isEmpty()) {
//            T n;
//            if(tail-1>=0) {
//                 n = arr[tail - 1];
//                arr[tail - 1] = null;
//            }
//            else{
//                 n = arr[arr.length-1];
//                arr[arr.length-1] = null;
//            }
//            if (tail == arr.length) {
//                tail = 0;
//                size--;
//            } else {
//                tail--;
//                size--;
//            }
//            return n;
//        }
//        return null;
//    }
//
//
//    public T get(int index){
//
//if(index>=size){
//    return null;
//}
//else{
//    if(head-index<0){
//        return arr[arr.length-index+head];
//    }
//    else {
//        return arr[head-index];
//    }
//}
////else{
////    if(head>tail) {
////    if(index<arr.length-head) {
////    return arr[head+index];
////}
////        else{
////            return arr[index-(arr.length-head)];
////        }
////    }
////    else{
////        if(index==0){
////            return arr[head];
////        }
////        else {
////            return arr[head + index];
////        }
////    }
////
////}
////
////
//   }
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    public class ArrayDequeIterator implements Iterator<T>{
        private int wizPos;

        private ArrayDequeIterator() {
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
        if(this.size()!=oa.size()){
            return false;    }
        for(int i=0;i<oa.size();i++){
            if(oa.get(i).equals(this.get(i))){
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        ArrayDeque<Integer> ArrayDeque=new ArrayDeque<>();
        ArrayDeque.addLast(0);
        ArrayDeque.addLast(1);
        ArrayDeque.addLast(2);
        ArrayDeque.addFirst(3);
        ArrayDeque.removeLast()     ;
        ArrayDeque.addLast(5);
        ArrayDeque.removeLast()     ;
        ArrayDeque.addFirst(7);
        ArrayDeque.addLast(8);
        ArrayDeque.removeFirst()     ;
        ArrayDeque.addFirst(10);
        ArrayDeque.get(2)      ;
        ArrayDeque.get(4)      ;
        ArrayDeque.get(0)      ;
        ArrayDeque.removeFirst()    ;
        System.out.println(ArrayDeque.get(1));
   }


}
