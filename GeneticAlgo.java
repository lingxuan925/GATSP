import java.util.Vector;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

public class GeneticAlgo {

  private static final boolean elitism = true;
  private static final double mutate_rate = 0.1;
  private static final double crossover_rate = 0.8;
  private static final double tournament_rate = 0.1;
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

      for (int i=0; i<r.numRoutes(); i++) {
        ARoute route1 = tournamentSelection(r);
        ARoute route2 = tournamentSelection(r);

        if (Math.random() < crossover_rate) {
          //twoPointCrossover(route1, route2, newGen);
          uniformCrossover(route1, route2, newGen);
        }
      }

      for (int i=0; i<r.numRoutes(); i++) if (Math.random() < mutate_rate) newGen.offer(mutate(r.getRoute(i)));
    }

    for (int i=0; i<r.numRoutes(); i++) r.setRoute(i, newGen.poll());

    return r;
  }

  public static void twoPointCrossover(ARoute r1, ARoute r2, PriorityQueue<ARoute> newGen) {
    ARoute newOffspring1 = new ARoute(r1.getRouteSize());
    ARoute newOffspring2 = new ARoute(r1.getRouteSize());

    int start = randomno.nextInt(r1.getRouteSize());
    int end = randomno.nextInt(r1.getRouteSize());

    for (int i=0; i<r1.getRouteSize(); i++) {
      if (start <= end && i > start || i < end) {
        newOffspring1.setC(i, r2.getC(i));
        newOffspring2.setC(i, r1.getC(i));
      }
      else if (start > end) {
        if (!(i < start && i > end)) {
          newOffspring1.setC(i, r2.getC(i));
          newOffspring2.setC(i, r1.getC(i));
        }
      }
    }

    fill(newOffspring1, newOffspring2, r1, r2);

    newGen.offer(newOffspring1);
    newGen.offer(newOffspring2);
  }

  public static void uniformCrossover(ARoute r1, ARoute r2, PriorityQueue<ARoute> newGen) {
    ARoute newOffspring1 = new ARoute(r1.getRouteSize());
    ARoute newOffspring2 = new ARoute(r1.getRouteSize());

    for (int i=0; i<r1.getRouteSize(); i++) {
      if (randomno.nextBoolean()) {
          newOffspring1.setC(i, r2.getC(i));
          newOffspring2.setC(i, r1.getC(i));
      }
    }

    fill(newOffspring1, newOffspring2, r1, r2);

    newGen.offer(newOffspring1);
    newGen.offer(newOffspring2);
  }

  private static void fill(ARoute offspring1, ARoute offspring2, ARoute parent1, ARoute parent2) {
    for (int i=0; i<offspring1.getRouteSize(); i++) {
      if (!offspring1.containsC(parent1.getC(i))) {
        for (int j=0; j<offspring1.getRouteSize(); j++) {
          if (offspring1.getC(j) == null) {
            offspring1.setC(j, parent1.getC(i));
            break;
          }
        }
      }
      if (!offspring2.containsC(parent2.getC(i))) {
        for (int j=0; j<offspring2.getRouteSize(); j++) {
          if (offspring2.getC(j) == null) {
            offspring2.setC(j, parent2.getC(i));
            break;
          }
        }
      }
    }
  }

  public static ARoute tournamentSelection(Routes rts) {
    Routes t = new Routes((int)(tournament_rate*rts.numRoutes()));

    for (int i=0; i<t.numRoutes(); i++) t.setRoute(i, rts.getRoute(randomno.nextInt(rts.numRoutes())));

    return t.getFittestRoute();
  }

  public static ARoute mutate(ARoute r) {
    ARoute copy = r;

    int idx1 = randomno.nextInt(r.getRouteSize());
    int idx2 = randomno.nextInt(r.getRouteSize());

    while (idx1 == idx2) {
      idx1 = randomno.nextInt(r.getRouteSize());
      idx2 = randomno.nextInt(r.getRouteSize());
    }

    swap(idx1, idx2, copy);
    return copy;
  }

  private static void swap(int idx1, int idx2, ARoute copy) {
     City temp = copy.getC(idx1);
     copy.setC(idx1, copy.getC(idx2));
     copy.setC(idx2, temp);
  }
}
