public class SLLists {

private IntNode first;
    public SLLists(int x){
        first=new IntNode(x,null);
    }
    public void addfirst(int x){
        first=new IntNode(x,null);
    }
    public void addlast(int x){
        IntNode p=new IntNode(x,null);
        p=first;
        while(p.next!=null){
            p=p.next;
        }
       p.next=new IntNode(x,null);
    }
    private static int size(IntNode p){

        int k=1;
        while(p.next!=null){
            p=p.next;
            k++;
        }
        return k;
    }
    public int size(){
        return size(first);
    }
    public int getfirst(){
        return first.item;
    }
    public void addFirst2(int x){
//        my way
        if(first.next==null){
            IntNode p=new IntNode(x,null);

        }
        else{
            IntNode p=new IntNode(x,null);
            p.next=first.next;
            first.next=p;
        }
    }


    public static void main(String[] args){
        SLLists L=new SLLists(10);
        L.addfirst(15);
        L.addfirst(5);
        L.addlast(26);
        System.out.println(L.getfirst());
    }
public  static class IntNode{//静态的
    public int item;
    public IntNode next;
    public IntNode(int i,IntNode n){
        item=i;
        next=n;
    }


    }


}
