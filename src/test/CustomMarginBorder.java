package test;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

public class CustomMarginBorder
{
    private JPanel contentPane;
    private CustomThinBorder customBorder;
    private CustomThickBorder customThickBorder;
    
    private void displayGUI()
    {
        JFrame frame = new JFrame("Custom Arrow Border Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        customBorder = new CustomThinBorder(Color.BLUE, 15);
        customThickBorder = new CustomThickBorder(Color.RED, 5, 50, 3);
        Border combined = BorderFactory.createCompoundBorder(customBorder, customThickBorder);
        
        contentPane = new JPanel();     
        // contentPane.setBorder(customBorder);    
        contentPane.setBorder(combined); 
        
        frame.setContentPane(contentPane);
        frame.setSize(300, 300);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                new CustomMarginBorder().displayGUI();
            }
        };
        EventQueue.invokeLater(runnable);
    }
}

class CustomThinBorder extends AbstractBorder
{
  private static final long serialVersionUID = -1807160273445378181L;
  
    private Color borderColour;
    private int gap;

    public CustomThinBorder(Color colour, int g)
    {
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
            g2d.draw(new Line2D.Double((double)x + 10, (double)y + 10
                                , (double)x + 10, (double)y + 20));
            g2d.draw(new Line2D.Double( (double)x + 10, (double)y + 10
                                , (double)x + 20, (double)y + 10));
            // Right Border
            g2d.draw(new Line2D.Double( (double)width - 10, (double)y + 10
                                , (double)width - 10, (double)y + 20));
            g2d.draw(new Line2D.Double( (double)width - 10, (double)y + 10
                                , (double)width - 20, (double)y + 10));
            // Lower Left Border
            g2d.draw(new Line2D.Double( (double)x + 10, (double)height - 10
                                , (double)x + 20, (double)height - 10));
            g2d.draw(new Line2D.Double( (double)x + 10, (double)height - 10
                                , (double)x + 10, (double)height - 20));
            // Lower Right Border
            g2d.draw(new Line2D.Double( (double)width - 10, (double)height - 10
                                , (double)width - 20, (double)height - 10));
            g2d.draw(new Line2D.Double( (double)width - 10, (double)height - 10
                                , (double)width - 10, (double)height - 20));
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

class CustomThickBorder extends AbstractBorder
{
  private static final long serialVersionUID = 226777575665339401L;
  
    private Color borderColour;
    private int gap;
    private double rectWidth;
    private double rectHeight;

    public CustomThickBorder(Color colour, int g, double w, double h)
    {
        borderColour = colour;
        gap = g;
        rectWidth = w;
        rectHeight = h;
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
            g2d.fill(new Rectangle2D.Double(
                                  (double)x + gap
                                , (double)y + gap
                                , rectWidth, rectHeight));
            g2d.fill(new Rectangle2D.Double(
                                  (double)x + gap
                                , (double)y + gap + rectHeight
                                , rectHeight, rectWidth));
            // Right Border
            g2d.fill(new Rectangle2D.Double(
                                  (double)width - gap - rectWidth
                                , (double)y + gap
                                , rectWidth, rectHeight));
            g2d.fill(new Rectangle2D.Double(
                                  (double)width - gap - rectHeight
                                , (double)y + gap + rectHeight
                                , rectHeight, rectWidth));
            // Lower Left Border
            g2d.fill(new Rectangle2D.Double(
                                  (double)x + gap
                                , (double)height - gap - rectWidth
                                , rectHeight, rectWidth));
            g2d.fill(new Rectangle2D.Double(
                                  (double)x + gap
                                , (double)height - gap
                                , rectWidth, rectHeight));
            // Lower Right Border
            g2d.fill(new Rectangle2D.Double(
                                  (double)width - gap - rectHeight
                                , (double)height - gap - rectWidth
                                , rectHeight, rectWidth));
            g2d.fill(new Rectangle2D.Double(
                                  (double)width - gap - rectWidth
                                , (double)height - gap
                                , rectWidth, rectHeight));
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