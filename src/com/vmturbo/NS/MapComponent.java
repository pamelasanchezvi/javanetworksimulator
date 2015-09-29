
package com.vmturbo.NS;
import javax.swing.*;
import java.awt.*;

/**
 * @author pamelasanchez
 *
 */
public class MapComponent extends JComponent {

    private MapShape m;

    public MapComponent(MapShape m) {
    this.m = m;
    setPreferredSize(m.getSize());
    }


    @Override
    public void paintComponent(Graphics g) {
        // put logic for my edges here and repaint in action listeber
    Graphics2D g2 = (Graphics2D)g;
    //g2.clearRect(0,0,getSize().width,getSize().height);
    m.draw(g2);

    }

}