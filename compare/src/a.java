public class a<T> extends Dog {
    private T name;
    private int items;
    @Override
    public void bark(){
       System.out.println( "aaaa");
    }
    public a(T name,int items){
        this.name=name;
        this.items=items;
    }
}
