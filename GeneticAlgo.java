import java.util.Vector;

public class GeneticAlgo {

  private static final boolean elitism = true;
  private static final double mutate_rate = 0.015;
  private static final int route_cluster = 5;

  public static Routes evolve(Routes r) {
    int offset = 0;
    Routes newRoutes = new Routes(r.routes.size(), false);

    if (elitism) {
      newRoutes.routes.add(0, r.getFittestRoute());
      offset = 1;
    }

    for (int i=offset; i<r.routes.size(); i++) {
      ARoute chromosome1 = traitSelection(r);
      ARoute chromosome2 = traitSelection(r);

      ARoute offspring = crossover(chromosome1, chromosome2);
      newRoutes.routes.add(i, offspring);
    }

    for (int i=offset; i<r.routes.size(); i++) mutate(newRoutes.routes.get(i));
    return newRoutes;
  }

  public static ARoute crossover(ARoute c1, ARoute c2) {

  }

  public static ARoute traitSelection(Routes r) {

  }

  public static void mutate(ARoute route) {

  }
}
