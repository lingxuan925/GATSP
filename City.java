import java.util.Random;

public class City {
  int x_coor;
  int y_coor;
  Random rand = new Random();

  public City(int mapWidth, int mapHeight) {
    this.x_coor = rand.nextInt(mapWidth);
    this.y_coor = rand.nextInt(mapHeight);
  }

  public int getXCoor() {
    return this.x_coor;
  }

  public int getYCoor() {
    return this.y_coor;
  }

  public double getDistTo(City c) {
    return Math.sqrt(Math.pow(Math.abs(this.x_coor-c.getXCoor()),2)+Math.pow(Math.abs(this.y_coor-c.getYCoor()),2));
  }
}
