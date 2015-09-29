/**
 *
 */
package com.vmturbo.NS;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.JLabel;
/**
 * @author pamelasanchez
 * A spine leaf architecture.
*/
public class MapShape
{
    private ArrayList<MapNode> list;
    private ArrayList<Link> edges;

    private boolean paintEdge1=true;
    private boolean paintEdge2=true;
    private JLabel label;
    private int width;
   /**
      Constructs a network.

      @param x the left of the bounding rectangle
      @param y the top of the bounding rectangle
      @param width the width of the bounding rectangle
   */
   public MapShape(int width, ArrayList<MapNode> nodeList, ArrayList<Link> mapEdges)
   {
      this.list = nodeList;
      this.width = width;
      this.edges= mapEdges;
   }


   public void UpdateMap(ArrayList<MapNode> nodeList, ArrayList<Link> mapEdges){
       this.list = nodeList;
       this.edges= mapEdges;
   }
    public Dimension getSize() {
    return new Dimension(width,width);
    }

   public void draw(Graphics2D g2)
   {
      g2.drawString("Number of same index links:", 160, 595);
      label = new JLabel("NEW YORK");
      label.setLocation( 20, 150);
      g2.drawString("Number of Flows:", 440, 595);
//      g2.drawString("Shortest Path Length is:", 740, 595);

      // City stuff
      //attempt to draw all cities

      Ellipse2D.Double someCity;
      Line2D.Double cityLine;
      Point2D.Double V1=null;
      Point2D.Double V2=null;
        for(int  i=0; i< list.size(); i++ ){
            someCity  = new Ellipse2D.Double(50 + list.get(i).x/3, 500-this.list.get(i).y/3,
                    width / 6, width / 6);
            g2.draw(someCity);
            g2.drawString(list.get(i).name, 61 + (int)list.get(i).x/3, 509-(int)list.get(i).y/3);
        }
        System.out.println("In MapShape:");
        for(Link lnk:edges){
            paintEdge1 = true;
            paintEdge2 = true;
            Node from  = lnk.getSrcNode();
            Node to = lnk.getDestNode();
            V1= new Point2D.Double(51 + from.x/3 , 504-from.y/3);
            if(!from.paint){
                paintEdge1 = false;
            }
            V2= new Point2D.Double(51 + to.x/3 , 504-to.y/3);
            if(!to.paint){
                paintEdge2 = false;
            }



            /**
             * create edge red color in gui if both end nodes are in the path
             */
            cityLine = new Line2D.Double(V1,V2);
            if(paintEdge1 && paintEdge2){
                g2.setPaint(Color.RED);
                g2.draw(cityLine);
                System.out.println("  src:" + lnk.srcNode.getName() + " dest:" + lnk.destNode.getName());
                System.out.println("coordinates src:" + from.x+","+from.y+ " dest:" + to.x +","+to.y);
                if(lnk.getUtilization()>0){
                    System.out.println("coordinates src of string:" + (int)(from.x/3+(to.x/3-from.x/3)/2)+" , "+ (int)(500-to.y/3 + (to.y/3-from.y/3)*0.5));
                    System.out.println("node " + (int)(500-to.y/3 ));
                    System.out.println("difference" + (to.y-from.y) + "difference / 2" + (to.y-from.y)*0.5);

                    g2.drawString(Double.toString(lnk.getUtilization()), 40 + (int)(from.x/3+(to.x/3-from.x/3)/2), (int)(500-to.y/3 + (to.y/3-from.y/3)*0.5));
                }

            }else{
                g2.setPaint(Color.BLACK);
                g2.draw(cityLine);
            }
        }
   }

}


