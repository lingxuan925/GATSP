public class City {
  int nodeNum;
  double x_coor;
  double y_coor;

  public City(int node, double x, double y) {
    this.x_coor = x;
    this.y_coor = y;
    this.nodeNum = node;
  }

  public int getNodeNum() {
    return nodeNum;
  }

  public double getXCoor() {
    return this.x_coor;
  }

  public double getYCoor() {
    return this.y_coor;
  }

  public double getDist_UsingEUC(City c) {
    return Math.sqrt(Math.pow(Math.abs(this.x_coor-c.getXCoor()),2)+Math.pow(Math.abs(this.y_coor-c.getYCoor()),2));
  }
}
