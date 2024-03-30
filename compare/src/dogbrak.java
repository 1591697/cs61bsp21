import java.util.Comparator;

public class dogbrak {

    public static void main(String[ ] args){
        Dog a=new a("a",5);
        Dog b=new b("b",1);
        Dog c=new a("a",6);
        if(a.campareTo(b)<0){
           b.bark();
        }
        else if(a.campareTo(b)>0) {
           a.bark();
        }
        else {
            System.out.println("ffffffffffffffff");
        }

        Comparator<Dog> nc= Dog.getNamecompare();//callback function

    }
}
