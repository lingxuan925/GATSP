import java.util.Vector;

public class Routes {
  Vector<ARoute> routes;

  public Routes(int numOfRoutes, boolean isEmpty) {
    routes = new Vector<ARoute>(numOfRoutes);
    if (isEmpty) for (int i=0; i<numOfRoutes; i++) this.routes.add(new ARoute());
  }

  public ARoute getFittestRoute() {
    ARoute mostFit = routes.get(0);

    for (int i=1; i<routes.size(); i++) if (routes.get(i).getFitLevel() > mostFit.getFitLevel()) mostFit = routes.get(i);
    return mostFit;
  }
}
