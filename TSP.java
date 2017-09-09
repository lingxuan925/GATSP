import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.util.Vector;
import java.util.Collections;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TSP extends JPanel implements ActionListener {

  Vector<City> cities;
  Vector< Vector<City> > routes;
  int numOfCities, cluster_size, w, h;
  Timer t = new Timer(200, this);
  public static JFrame frame = new JFrame("TSP");

  public TSP(int numOfCities, int cluster_size, int w, int h) {
    this.numOfCities = numOfCities;
    this.cluster_size = cluster_size;
    cities = new Vector<>(numOfCities);
    routes = new Vector< Vector<City> >(cluster_size);
    this.w = w;
    this.h = h;
    for (int i=0; i<numOfCities; i++) cities.add(new City(w, h));
    for (int i=0; i<cluster_size; i++) {
      Collections.shuffle(cities);
      routes.add(cities);
    }
  }

  public void drawPaths(Graphics2D g2d) {
    for (int i=0; i<cities.size(); i++) {
      g2d.fillOval(cities.get(i).getXCoor(),cities.get(i).getYCoor(), 1, 1);
    }

    for (int i=0; i<cities.size(); i++) {
      if (i+1 < cities.size()) {
        g2d.drawLine(cities.get(i).getXCoor(),cities.get(i).getYCoor(),cities.get(i+1).getXCoor(),cities.get(i+1).getYCoor());
      }
      else {
        g2d.drawLine(cities.get(i).getXCoor(),cities.get(i).getYCoor(),cities.get(0).getXCoor(),cities.get(0).getYCoor());
      }
    }
  }

  public Vector<City> getFittestRoute() {
    Vector<City> fittest = routes.get(0);
    int fitness = 0;
    for (int i=0; i<cluster_size; i++) {
      int totalDist = 0;
      for (int j=0; j<numOfCities; j++) {
        if (j+1 < numOfCities) totalDist += routes.get(i).get(j).getDistTo(routes.get(i).get(j+1));
        else totalDist += routes.get(i).get(j).getDistTo(routes.get(i).get(0));
      }
      if ((1/(double)totalDist) >= fitness) fittest = routes.get(i);
    }
    return fittest;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.red);
    g2d.setStroke(new BasicStroke(1));

    drawPaths(g2d);
    t.start();
  }

  public void actionPerformed(ActionEvent e) {
    Collections.shuffle(cities);
    repaint();
  }

  public static void main(String[] args) {
    int w=1000, h=700;
    TSP tsp = new TSP(5, 5, w, h);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(w, h);
    frame.add(tsp);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
