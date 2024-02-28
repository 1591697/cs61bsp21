package deque;
import java.util.Comparator;
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator cmp;
    public MaxArrayDeque(Comparator<T> c){
        cmp = c;
    }
    public T max(){
        if(this.isEmpty()){
            return null;
        }
        T maxItem = this.get(0);
        for (int i=0;i<this.size();i++) {
            T r=this.get(i);
            if (cmp.compare(r, maxItem) > 0) {
                maxItem = r;
            }
        }
        return maxItem;
    }
    public T max(Comparator<T> c){
        if(this.isEmpty()){
            return null;
        }
        T maxItem =this.get(0);
        for(int i=0;i<this.size();i++){
            T r=this.get(i);
            if(c.compare(r,maxItem)>0){
                maxItem = r;
            }
        }
        return maxItem;
    }


}
