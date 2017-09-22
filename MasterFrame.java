import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

public class MasterFrame extends JFrame {

  private ARoute r;
  private Panel p;
  private int cnter;

  public MasterFrame() {
    cnter = 0;
    p = new Panel();
    p.setPreferredSize(new Dimension(1000, 700));
    //p.setBackground(Color.darkGray);
    Container container = getContentPane();
    container.add(p);
    setResizable(false);
    pack();
    setTitle("TSP using GA");
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void updatePanel(ARoute route) {
    this.r = route;
    cnter += 1;
    repaint();
  }

  private class Panel extends JPanel {

    private void drawPathsAndNodes(Graphics2D g2d) {
      for (int i=0; i<r.getRouteSize(); i++) {
        g2d.fillOval((int)r.getC(i).getXCoor()/2,(int)r.getC(i).getYCoor()/2, 5, 5);
        g2d.drawString(String.valueOf(r.getC(i).getNodeNum()), (int)r.getC(i).getXCoor()/2, (int)r.getC(i).getYCoor()/2);
      }
      for (int i=0; i<r.getRouteSize(); i++) {
        if (i+1 < r.getRouteSize()) g2d.drawLine((int)r.getC(i).getXCoor()/2,(int)r.getC(i).getYCoor()/2,(int)r.getC(i+1).getXCoor()/2,(int)r.getC(i+1).getYCoor()/2);
        else g2d.drawLine((int)r.getC(i).getXCoor()/2,(int)r.getC(i).getYCoor()/2,(int)r.getC(0).getXCoor()/2,(int)r.getC(0).getYCoor()/2);
      }
      g2d.drawString("Gen: "+String.valueOf(cnter-1), (int)(getWidth()-getWidth()*0.1), (int)(getHeight()-getHeight()*0.1));
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      g2d.setColor(Color.black);
      g2d.setStroke(new BasicStroke(1));

      if (r != null) drawPathsAndNodes(g2d);
    }
  }
}
