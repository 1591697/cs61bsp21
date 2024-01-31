package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE

        AList<Integer> ns=new AList<Integer>();
        AList<Double> times=new AList<Double>();
        AList<Integer> op=new AList<Integer>();
       int m=10000;

        for(int i=1000;i<=128000;i=i*2){
            ns.addLast(i);
        }


        for(int i=0;i<ns.size();i++){
            int j=0;
            int k=ns.get(i);
            SLList<Integer> l=new SLList<Integer>();
            while(j<k){
                l.addLast(j);
                j++;
            }
//            Stopwatch sw = new Stopwatch();
//            for(int s=0;s<10000;s++){
//            l.getLast();
//            }
//            times.addLast(sw.elapsedTime());
            Stopwatch sw = new Stopwatch();
            for (int f = 0; f < m; f++) {
                l.getLast();
            }
            times.addLast(sw.elapsedTime());
            op.addLast(m);
        }
        printTimingTable(ns,times,op);
        for(int i=0;i<times.size();i++) {
            System.out.println(times.get(i));
        }

    }
//        int M = 10000;
//        AList<Integer> Ns = new AList<Integer>();
//        AList<Double> times = new AList<Double>();
//        AList<Integer> opCounts = new AList<Integer>();
//
//        int temp=1000;
//        while (temp<=128000){
//            Ns.addLast(temp);
//            // opCounts.addLast(temp);
//            temp= temp*2;
//        }
//
//        for (int i = 0; i < Ns.size(); i++) {
//            int N = Ns.get(i);
//            int j = 0;
//            SLList<Integer> L = new SLList<Integer>();
//            while (j<N){
//                L.addLast(j);
//                j=j+1;
//            }
//            Stopwatch sw = new Stopwatch();
//            for (int k = 0; k < M; k++) {
//                L.getLast();
//            }
//            times.addLast(sw.elapsedTime());
//            opCounts.addLast(M);
//        }
//        printTimingTable(Ns,times,opCounts);
//    }

}
