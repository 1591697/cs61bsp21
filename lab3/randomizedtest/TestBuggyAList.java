package randomizedtest;

import com.sun.xml.internal.ws.developer.UsesJAXBContext;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  //todo  YOUR TESTS HERE
  @Test
  public void testThreeAddThreeRemove(){
    BuggyAList<Integer> ba=new BuggyAList<Integer>();
    AListNoResizing<Integer> anr=new AListNoResizing<Integer>();
    ba.addLast(4);
    ba.addLast(5);
    ba.addLast(6);
    anr.addLast(4);
    anr.addLast(5);
    anr.addLast(6);
    ba.removeLast();
    anr.removeLast();
    Assert.assertEquals(ba.size(),anr.size());
    Assert.assertEquals(ba.removeLast(),anr.removeLast());
    Assert.assertEquals(ba.removeLast(),anr.removeLast());
  }
@Test
  public void Randomized(){
  AListNoResizing<Integer> L = new AListNoResizing<Integer>();
  BuggyAList<Integer> B = new BuggyAList<Integer>();
  int N = 2000;
  for (int i = 0; i < N; i += 1) {
    int operationNumber = StdRandom.uniform(0, 3);
    if(operationNumber==0){
      if(L.size()==0){
        continue;
      }

        int j=L.getLast();
        System.out.println("L.getLast(" + j + ")");
        int v=B.getLast();
        System.out.println("B.getLast(" + v + ")");

    }
    if (operationNumber == 1) {
      // addLast
      int randVal = StdRandom.uniform(0, 100);
      L.addLast(randVal);
      B.addLast(randVal);
      System.out.println("L.addLast(" + randVal + ")");
      System.out.println("B.addLast(" + randVal + ")");
    }
   if (operationNumber == 2) {

     if(L.size()==0){
       continue;
     }

      int k= L.removeLast();
       System.out.println("L.removeLast(" + k + ")");
       int n= B.removeLast();
       System.out.println("B.removeLast(" + n + ")");

    }
  }
}
  @Test
  public void randomizedTest(){
    AListNoResizing<Integer> L = new AListNoResizing<Integer>();
    BuggyAList<Integer> B = new BuggyAList<Integer>();

    int N = 3500;
    for (int i = 0; i < N; i += 1) {
      int operationNumber = StdRandom.uniform(0, 3);
      if (operationNumber == 0) {
        // addLast
        int randVal = StdRandom.uniform(0, 100);
        L.addLast(randVal);
        B.addLast(randVal);
        Assert.assertEquals(L.size(),B.size());
      } else if (operationNumber == 1) {
        // size
        if(L.size()==0){
          continue;
        }
        int RemoveLastL =L.removeLast();
        int RemoveLastB = B.removeLast();
        Assert.assertEquals(RemoveLastB,RemoveLastL);
      } else if (operationNumber==2) {
        if(L.size()==0){
          continue;
        }
        int LastOfL = L.getLast();
        int LastOfB = B.getLast();
        Assert.assertEquals(LastOfL,LastOfB);
      }
    }
  }

}
