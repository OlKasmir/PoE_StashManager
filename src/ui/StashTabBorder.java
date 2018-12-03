package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;

import javax.swing.border.AbstractBorder;

// TODO: Draw border from images and recolor
public class StashTabBorder extends AbstractBorder {
  private static final long serialVersionUID = -1205222912578307463L;

  private Color borderColour;
  private int gap;
  
  public StashTabBorder(Color colour, int g) {
    borderColour = colour;
    gap = g;
  }
  
  @Override
  public void paintBorder(Component c, Graphics g, int x, int y
                                                 , int width
                                                 , int height)
  {
      super.paintBorder(c, g, x, y, width, height);
      
      Graphics2D g2d = null;
      if (g instanceof Graphics2D)
      {
          g2d = (Graphics2D) g;
          g2d.setColor(borderColour);
          //Left Border
          g2d.drawArc(x + gap, y - gap, width, height, 15, 160);
          
          g2d.drawArc(x - gap, y - gap, width, height, -90, 110);
          
//          g2d.fill(new Rectangle2D.Double(
//                                (double)x + gap
//                              , (double)y + gap
//                              , rectWidth, rectHeight));
//          g2d.fill(new Rectangle2D.Double(
//                                (double)x + gap
//                              , (double)y + gap + rectHeight
//                              , rectHeight, rectWidth));
//          // Right Border
//          g2d.fill(new Rectangle2D.Double(
//                                (double)width - gap - rectWidth
//                              , (double)y + gap
//                              , rectWidth, rectHeight));
//          g2d.fill(new Rectangle2D.Double(
//                                (double)width - gap - rectHeight
//                              , (double)y + gap + rectHeight
//                              , rectHeight, rectWidth));
//          // Lower Left Border
//          g2d.fill(new Rectangle2D.Double(
//                                (double)x + gap
//                              , (double)height - gap - rectWidth
//                              , rectHeight, rectWidth));
//          g2d.fill(new Rectangle2D.Double(
//                                (double)x + gap
//                              , (double)height - gap
//                              , rectWidth, rectHeight));
//          // Lower Right Border
//          g2d.fill(new Rectangle2D.Double(
//                                (double)width - gap - rectHeight
//                              , (double)height - gap - rectWidth
//                              , rectHeight, rectWidth));
//          g2d.fill(new Rectangle2D.Double(
//                                (double)width - gap - rectWidth
//                              , (double)height - gap
//                              , rectWidth, rectHeight));
      }
  }

  @Override
  public Insets getBorderInsets(Component c)
  {
      return (getBorderInsets(c, new Insets(gap, gap, gap, gap)));
  }

  @Override
  public Insets getBorderInsets(Component c, Insets insets)
  {
      insets.left = insets.top = insets.right = insets.bottom = gap;
      return insets;
  }

  @Override
  public boolean isBorderOpaque()
  {
      return true;
  }
}
