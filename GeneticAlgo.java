import java.util.Vector;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

public class GeneticAlgo {

  private static final boolean elitism = true;
  private static final double mutate_rate = 0.1;
  private static final double crossover_rate = 0.7;
  private static final int tournamentSelectionSize = 10;
  public static Random randomno = new Random();

  public static Routes evolve(Routes r, Cities cities) {
    PriorityQueue<ARoute> newGen = new PriorityQueue<ARoute>(new Comparator<ARoute>() {
      public int compare(ARoute r1, ARoute r2) {
        double f1 = r1.getFitLevel();
        double f2 = r2.getFitLevel();

        if (f1 > f2) return -1;
        else if (f1 < f2) return 1;

        return 0;
      }
    });

    if (elitism) {
      for (int i=0; i<r.numRoutes(); i++) newGen.offer(r.getRoute(i));
      //System.out.println(newGen.size());
      // ARoute[] rrr = new ARoute[newGen.size()];
      // newGen.toArray(rrr);
      // System.out.println(rrr[0].getFitLevel());
      // System.out.println(rrr[20].getFitLevel());
      // System.out.println(rrr[50].getFitLevel());

      for (int i=0; i<r.numRoutes(); i++) {
        //System.out.println("here2");
        ARoute route1 = tournamentSelection(r);
        ARoute route2 = tournamentSelection(r);

        if (Math.random() < crossover_rate) {
          //ARoute offspring = uniformCrossover(route1, route2);
          twoPointCrossover(route1, route2, newGen);
        }
      }
      //System.out.println("here10");
      //System.out.println(r.getRoute(0));
      for (int i=0; i<r.numRoutes(); i++) {
        if (Math.random() < mutate_rate) {
          //System.out.println("mutate"+i);
          //System.out.println(r.getRoute(i));
          ARoute result = mutate(r.getRoute(i));
          //System.out.println("here14");
          newGen.offer(result);
        }
      }
    }

    for (int i=0; i<r.numRoutes(); i++) {
      r.setRoute(i, newGen.poll());
    }
    return r;
  }

  public static void twoPointCrossover(ARoute r1, ARoute r2, PriorityQueue<ARoute> newGen) {
    ARoute newOffspring1 = new ARoute(r1.getRouteSize());
    ARoute newOffspring2 = new ARoute(r1.getRouteSize());

    //System.out.println(newOffspring2.getRouteSize());

    int start = randomno.nextInt(r1.getRouteSize());
    int end = randomno.nextInt(r1.getRouteSize());

    //System.out.println(newGen.size());

    for (int i=0; i<r1.getRouteSize(); i++) {
      if (i < start || i > end) {
        newOffspring1.setC(i, r2.getC(i));
        newOffspring2.setC(i, r1.getC(i));
      }
    }
    //System.out.println("here3");

    for (int i=0; i<r1.getRouteSize(); i++) {
      if (!newOffspring1.containsC(r1.getC(i))) {
        for (int j=0; j<r1.getRouteSize(); j++) {
          if (newOffspring1.getC(j) == null) {
            newOffspring1.setC(j, r1.getC(i));
            break;
          }
        }
      }
      if (!newOffspring2.containsC(r2.getC(i))) {
        for (int j=0; j<r1.getRouteSize(); j++) {
          if (newOffspring2.getC(j) == null) {
            newOffspring2.setC(j, r2.getC(i));
            break;
          }
        }
      }
    }
    //System.out.println("here4");
    newGen.offer(newOffspring1);
    newGen.offer(newOffspring2);
  }

  // public static ARoute uniformCrossover(ARoute r1, ARoute r2) {
  //   ARoute newOffspring = new ARoute();
  //
  //   for (int i=0; i<r1.getRouteSize(); i++) {
  //     int choose = randomno.nextInt(2);
  //     switch (choose) {
  //       case 0:
  //         newOffspring.setC(i, r1.getC(i));
  //         break;
  //       case 1:
  //         newOffspring.setC(i, r2.getC(i));
  //     }
  //   }
  //   newOffspring.setFitLevel(0);
  //   newOffspring.setDist(0);
  //   return newOffspring;
  // }

  public static ARoute tournamentSelection(Routes rts) {
    Routes t = new Routes(tournamentSelectionSize);
    //Routes copy = rts;
    for (int i=0; i<tournamentSelectionSize; i++) {
      int idx = randomno.nextInt(rts.numRoutes());
      // while (copy.getRoute(idx) == null) {
      //   idx = randomno.nextInt(rts.numRoutes());
      // }
      t.setRoute(i, rts.getRoute(idx));
      //copy.setRoute(i, null);
    }
    ARoute mostFit = t.getFittestRoute();
    //System.out.println(mostFit.getFitLevel());
    return mostFit;
  }

  public static ARoute mutate(ARoute r) {
    ARoute copy = r;
    //System.out.println(copy.getRouteSize());
    for (int i=0; i<r.getRouteSize(); i++) {
      int idx = randomno.nextInt(r.getRouteSize());
      while (idx == i) {
        idx = randomno.nextInt(r.getRouteSize());
      }
      swap(i, idx, copy);
    }
    return copy;
  }

  private static void swap(int idx1, int idx2, ARoute copy) {
     City temp = copy.getC(idx1);
     copy.setC(idx1, copy.getC(idx2));
     copy.setC(idx2, temp);
  }
}
