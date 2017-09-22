import java.util.Vector;
import java.util.Collections;
import java.util.Random;

public class ARoute {
  // private int dist = 0;
  // private double fitLevel = 0;
  private Vector<City> aRoute = new Vector<City>();
//create a new contructor for new offspring
  public ARoute(int size) {
    for (int i=0; i<size; i++) aRoute.add(null);
  }

  public ARoute(Cities cities, Random randomno) {
    for (int i=0; i<cities.getSize(); i++) aRoute.add(cities.getCity(i));
    Collections.shuffle(aRoute, randomno);
  }

  public int getTotalRouteDist() {
    int totalDist = 0;
    for (int i=0; i<aRoute.size(); i++) {
      if (i+1 < aRoute.size()) totalDist += aRoute.get(i).getDist_UsingEUC(aRoute.get(i+1));
      else totalDist += aRoute.get(i).getDist_UsingEUC(aRoute.get(0));
    }
    return totalDist;
  }

  public boolean containsC(City c) {
    return aRoute.contains(c);
  }

  public double getFitLevel() {
    return 1.0/(double)getTotalRouteDist();
  }

  public int getRouteSize() {
    return aRoute.size();
  }

  public void setC(int idx, City c) {
    aRoute.set(idx, c);
  }

  public City getC(int idx) {
    return aRoute.get(idx);
  }
}
