public class Routes {
  private ARoute[] routes;

  public Routes(int numOfRoutes) {
    routes = new ARoute[numOfRoutes];
  }

  public Routes(int numOfRoutes, Cities cities) {
    routes = new ARoute[numOfRoutes];
    for (int i=0; i<numOfRoutes; i++) routes[i] = new ARoute(cities);
  }

  public int numRoutes() {
    return routes.length;
  }

  public ARoute getRoute(int idx) {
    return routes[idx];
  }

  public void setRoute(int idx, ARoute r) {
    routes[idx] = r;
  }

  public ARoute getFittestRoute() {
    ARoute mostFit = routes[0];

    for (int i=1; i<routes.length; i++) if (routes[i].getFitLevel() > mostFit.getFitLevel()) mostFit = routes[i];
    return mostFit;
  }
}
