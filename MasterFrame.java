import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MasterFrame extends JPanel {

  // Timer t = new Timer(200, this);
  private ARoute r;

  public MasterFrame(ARoute ar) {
    r = ar;
  }

  public void drawPaths(Graphics2D g2d) {
    for (int i=0; i<r.getRouteSize(); i++) {
      g2d.fillOval((int)r.getC(i).getXCoor()/2,(int)r.getC(i).getYCoor()/2, 5, 5);
      g2d.drawString(String.valueOf(r.getC(i).getNodeNum()), (int)r.getC(i).getXCoor()/2, (int)r.getC(i).getYCoor()/2);
    }

    // for (int i=0; i<r.getRouteSize(); i++) {
    //   if (i+1 < r.getRouteSize()) {
    //     g2d.drawLine((int)r.getC(i).getXCoor(),(int)r.getC(i).getYCoor(),(int)r.getC(i+1).getXCoor(),(int)r.getC(i+1).getYCoor());
    //   }
    //   else {
    //     g2d.drawLine((int)r.getC(i).getXCoor(),(int)r.getC(i).getYCoor(),(int)r.getC(0).getXCoor(),(int)r.getC(0).getYCoor());
    //   }
    // }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.red);
    g2d.setStroke(new BasicStroke(2));

    drawPaths(g2d);
    //t.start();
  }

  // public void actionPerformed(ActionEvent e) {
  //   //repaint();
  // }
}
