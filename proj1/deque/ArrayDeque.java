package deque;


import java.util.Iterator;

public class ArrayDeque <T> implements Deque<T>{
     private T[] arr;
    private  int head;
    private  int tail;
    private  int size;
    public  ArrayDeque(){
        arr=(T[])new Object[8];
        head=0;
        tail=0;
        size=0;
    }
    public boolean isfull(){
        if(size>=arr.length)
        return true;
        return false;
    }
    public  void resize(){

            T[] a=(T[])new Object[arr.length*2];
//            for(int i=0;i<arr.length;i++){
//                if(head+i<arr.length)
//                a[i]=arr[head+i];
//                if(head+i>=arr.length){
//                    a[i]=a[i-arr.length+head];
//                }
//            }
            for(int i=0;i<tail;i++){
                a[i]=arr[i];
            }
            for(int i=head+1 ;i<arr.length;i++){
                a[i+arr.length]=arr[i];
            }
            head=arr.length+head;

            arr=a;
        }


    @Override
    public void addFirst(T item) {
        if (!isfull()) {
            if(head==0||tail==0){
                tail=1;
            }
            if (head == 0) {
                arr[head] = item;
                head = arr.length - 1;
                size++;
            } else {
                arr[head] = item;
                head--;
                size++;
            }
        }
        else{
            resize();
            if (head == 0) {
                arr[head] = item;
                head = arr.length - 1;
                size++;
            } else {
                arr[head] = item;
                head--;
                size++;
            }
        }
    }


    public void addLast(T item){
        if(size<arr.length-2){
            if(head==0||tail==0){
                head=arr.length-1;
            }
            if(tail==arr.length-1) {
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
        else{
            resize();
            if(tail==arr.length-1) {
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
        if (size == 0) {

            return true;
        }
return false;
    }
    public int size(){
        return  size;
    }
    public void printDeque(){
        int num;
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
        if(!isEmpty()) {

            if (head ==arr.length-1) {
                T n = arr[0];
    arr[0]=null;

                head = 0;
                size--;
                return n;
            } else {
                T n=arr[head+1];
                arr[head+1] = null;

                head++;
                size--;
                return n;
            }


        }
        return null;
    }
    public T removeLast(){
        if(!isEmpty()) {
            T n;
            if(tail-1>=0) {
                 n = arr[tail - 1];
                arr[tail - 1] = null;
            }
            else{
                 n = arr[arr.length-1];
                arr[arr.length-1] = null;
            }
            if (tail == arr.length) {
                tail = 0;
                size--;
            } else {
                tail--;
                size--;
            }
            return n;
        }
        return null;
    }


    public T get(int index){

if(index>=size){
    return null;
}
else{
    if(head-index<0){
        return arr[arr.length-index+head];
    }
    else {
        return arr[head-index];
    }
}
//else{
//    if(head>tail) {
//    if(index<arr.length-head) {
//    return arr[head+index];
//}
//        else{
//            return arr[index-(arr.length-head)];
//        }
//    }
//    else{
//        if(index==0){
//            return arr[head];
//        }
//        else {
//            return arr[head + index];
//        }
//    }
//
//}
//
//
   }
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    public class ArrayDequeIterator implements Iterator<T>{
        private int wizPos;

        private ArrayDequeIterator() {
            wizPos = 0;
        }
        public boolean hasNext(){
return size<wizPos;
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
            if(oa.get(i)!=this.get(i)){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        ArrayDeque<Integer> a=new ArrayDeque<>();
        a.addFirst(1);
      a.removeFirst();
        a.printDeque();

   }


}
