import java.util.Vector;
import java.util.Collections;

public class ARoute {
  private int dist = 0;
  private double fitLevel = 0;
  private Vector<City> aRoute = new Vector<City>();

  public ARoute() {
    for (int i=0; i<Cities.cities.size(); i++) this.aRoute.add(Cities.cities.get(i));
    Collections.shuffle(aRoute);
  }

  public ARoute(Vector<City> r) {
    this.aRoute = r;
  }

  public int getTotalRouteDist() {
    int totalDist = 0;
    if (dist == 0) {
      for (int i=0; i<aRoute.size(); i++) {
        if (i+1 < aRoute.size()) totalDist += aRoute.get(i).getDistTo(aRoute.get(i+1));
        else totalDist += aRoute.get(i).getDistTo(aRoute.get(0));
      }
      dist = totalDist;
    }
    return dist;
  }

  public double getFitLevel() {
    if (fitLevel == 0) return 1/(double)getTotalRouteDist();
    return fitLevel;
  }
}
