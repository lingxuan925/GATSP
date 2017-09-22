import java.util.Vector;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

public class GeneticAlgo {

  private static final boolean elitism = true;

  public static Routes evolve(Routes r, Cities cities, double mutate_rate, double crossover_rate, double tournament_rate, Random randomno) {
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
        ARoute route1 = tournamentSelection(r, tournament_rate, randomno);
        ARoute route2 = tournamentSelection(r, tournament_rate, randomno);

        if (Math.random() < crossover_rate) {
          //twoPointCrossover(route1, route2, newGen, randomno);
          uniformCrossover(route1, route2, newGen, randomno);
        }
      }

      for (int i=0; i<r.numRoutes(); i++) if (Math.random() < mutate_rate) {
        newGen.offer(inverse_mutate(r.getRoute(i), randomno));
        //newGen.offer(swap_mutate(r.getRoute(i), randomno));
      }
    }

    for (int i=0; i<r.numRoutes(); i++) r.setRoute(i, newGen.poll());

    return r;
  }

  public static void twoPointCrossover(ARoute r1, ARoute r2, PriorityQueue<ARoute> newGen, Random randomno) {
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

  public static void uniformCrossover(ARoute r1, ARoute r2, PriorityQueue<ARoute> newGen, Random randomno) {
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

  public static ARoute tournamentSelection(Routes rts, double tournament_rate, Random randomno) {
    Routes t = new Routes((int)(tournament_rate*rts.numRoutes()));

    for (int i=0; i<t.numRoutes(); i++) t.setRoute(i, rts.getRoute(randomno.nextInt(rts.numRoutes())));

    return t.getFittestRoute();
  }

  /*inverse mutation is much better than swap mutation in moving towards a
  better optimal route*/
  public static ARoute inverse_mutate(ARoute r, Random randomno) {
    ARoute copy = new ARoute(r.getRouteSize());

    int start = randomno.nextInt(r.getRouteSize());
    int end = randomno.nextInt(r.getRouteSize());

    while (start == end) {
      start = randomno.nextInt(r.getRouteSize());
      end = randomno.nextInt(r.getRouteSize());
    }

    int pos = 0;
    if (start < end) {
      ARoute section = new ARoute(end-start+1);
      for (int i=start; i<=end; i++) {
        section.setC(pos, r.getC(i));
        pos += 1;
      }
      pos -= 1;
      for (int i=0; i<r.getRouteSize(); i++) {
        if (!(i > end || i < start)) {
          copy.setC(i, section.getC(pos));
          pos -= 1;
        }
        else copy.setC(i, r.getC(i));
      }
    }
    else {
      ARoute section = new ARoute(r.getRouteSize()-(start-end)+1);
      for (int i=start; i<r.getRouteSize(); i++) {
        section.setC(pos, r.getC(i));
        pos += 1;
      }
      for (int i=0; i<=end; i++) {
        section.setC(pos, r.getC(i));
        pos += 1;
      }
      pos -= 1;
      for (int i=0; i<r.getRouteSize(); i++) if (i > end || i < start) copy.setC(i, r.getC(i));

      for (int i=0; i<r.getRouteSize(); i++) {
        if (copy.getC(i) == null) {
          copy.setC(i, section.getC(pos));
          pos -= 1;
        }
      }
    }

    return copy;
  }

  public static ARoute swap_mutate(ARoute r, Random randomno) {
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
