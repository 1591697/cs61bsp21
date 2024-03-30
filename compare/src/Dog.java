import java.util.Comparator;
import java.util.Comparator;
public class Dog<T> implements camparable<Dog> {
    private T name;
    private int items;
    public void Dog(){
        name=null;
        items=0;
    }
    public void dog(T name,int items){
        this.items=items;
        this.name=name;
    }
    public void bark(){
       System.out.println("ffff");
    }
    public int campareTo( Dog ol){
        if(this.items>ol.items){
            return 1;
        }
        else if(this.items==ol.items){
            return 0;
        }
        else{
            return -1;
        }
    }
    private static  class Namecompare implements Comparator<Dog>{
        public int compare(Dog o1,Dog o2){
            return  1;
        }
    }
    public static Comparator<Dog> getNamecompare(){
        return new Namecompare();
    }


}
