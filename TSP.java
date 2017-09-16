import java.io.FileReader;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;

public class TSP {

  private JFileChooser jFC;
  private FileReader fR;
  private File f;
  private BufferedReader bR;
  private String line;
  public static String EDGE_WEIGHT_TYPE;

  public TSP() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter population size: ");
    while (!sc.hasNextInt()) {
      System.out.print("Enter population size: ");
      sc.next();
    }
    int pop_size = sc.nextInt();

    System.out.print("How many generations: ");
    while (!sc.hasNextInt()) {
      System.out.print("How many generations: ");
      sc.next();
    }
    int gen_size = sc.nextInt();

    Cities cities = null;
    cities = readFileAndInit(cities);

    Routes routes = new Routes(pop_size, cities);
    System.out.println(routes.getFittestRoute().getTotalRouteDist());

    for (int i=0; i<gen_size; i++) {
      routes = GeneticAlgo.evolve(routes, cities);
    }
    System.out.println(routes.getFittestRoute().getTotalRouteDist());

    JFrame jF = new JFrame("TSP using GA");
    jF.getContentPane().add(new MasterFrame(routes.getFittestRoute()));
    jF.setSize(1000, 700);
    jF.setVisible(true);
    jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private Cities readFileAndInit(Cities cities) {
    System.out.println("here");
    //jFC = new JFileChooser();
    try {
      //jFC.showOpenDialog(null);
      //f = jFC.getSelectedFile();
      f = new File("/Users/lingxuan925/Desktop/berlin52.tsp");
      //System.out.println("You chose to open this file: " + f.getName());

      fR = new FileReader(f);
      bR = new BufferedReader(fR);

      Boolean isCoord = false;
      while (!(line = bR.readLine()).equals("EOF")) {
        if (line.contains("DIMENSION")) {
          cities = new Cities(new Integer(line.split(" ")[1]));
          System.out.println(cities.getSize());
        }
        else if (line.contains("EDGE_WEIGHT_TYPE")) {
          EDGE_WEIGHT_TYPE = line.split(" ")[1];
          System.out.println(EDGE_WEIGHT_TYPE);
        }
        else if (line.contains("NODE_COORD_SECTION")){
          isCoord = true;
          continue;
        }

        if (isCoord) {
          City newCity = new City(new Integer(line.split(" ")[0]).intValue(), new Double(line.split(" ")[1]).doubleValue(), new Double(line.split(" ")[2]).doubleValue());
          cities.addCity(newCity);
        }
      }

      bR.close();
    } catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + f.getName() + "'");
    } catch(IOException ex) {
      ex.printStackTrace();
    }
    return cities;
  }

  public static void main(String[] args) {
    new TSP();
  }
}
