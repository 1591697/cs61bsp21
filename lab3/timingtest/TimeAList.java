package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> l=new AList<Integer>();
        AList<Integer> ns=new AList<Integer>();
        AList<Double> times=new AList<Double>();
        AList<Integer> op=new AList<Integer>();
        //次数获取存储
        for(int i=1000;i<=128000;i=i*2){
            ns.addLast(i);
            op.addLast(i);
        }

        for(int i=0;i<ns.size();i++){
            int j=0;
            int k=ns.get(i);
            Stopwatch sw = new Stopwatch();
            while(j<k){
                l.addLast(j);
                j++;
            }
          times.addLast(sw.elapsedTime());
        }
        printTimingTable(ns,times,op);

    }
}
