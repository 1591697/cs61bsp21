public class b<T> extends Dog {
    @Override
    public void bark(){
       System.out.println("BBBB");
    }




    private T name;
    private int items;

    public b(T name,int items){
        this.name=name;
        this.items=items;
    }
}
