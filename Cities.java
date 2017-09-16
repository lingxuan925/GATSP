import java.util.Vector;

public class Cities {

  private Vector<City> cities;
  private int numOfCities;

  public Cities(int num) {
    cities = new Vector<City>(num);
    numOfCities = num;
  }

  public int getSize() {
    return numOfCities;
  }

  public void addCity(City c) {
    cities.add(c);
  }

  public City getCity(int idx) {
    return cities.get(idx);
  }
}
