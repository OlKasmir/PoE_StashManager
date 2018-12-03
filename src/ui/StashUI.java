package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import image.ImageManager;
import item.Stash;
import item.StashTab;

public class StashUI extends JPanel {
  private static final long serialVersionUID = -5572753945558981689L;
  
  public Stash stash;
  public List<StashTabUI> stashTabsUI;
  
  int totalBorderWidth;
  int totalBorderHeight;
  int borderSize = 3;
  
  int tabHeight = 10;
  
  public JTabbedPane tabbedPane;
  
  public StashUI (Stash stash) {
    stashTabsUI = new ArrayList<StashTabUI>();
    this.stash = stash;
    
    initStashUI();
  }
  
  public void initStashUI() {
    /*
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 0;
    
    
    
    // TODO: Loop through all stash tabs
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 1;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    StashTabUI tab = initStashTabUI(0);
    add(tab, c);
    */
    
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    
    initTabbedPane(c);
    
    // setSize(getStashSize());
    //setBorder();
  }
  
  /*
  Top 26 Pixels = Unselected
  Bottom 26 Pixels = Selected

  Left 
  19x26

  Center
  30x26

  Right 
  19x26

  Total
  64x26
  */
//  UIDefaults def = UIManager.getLookAndFeelDefaults();
//  def.put( "TabbedPane.foreground", Color.RED );
//  def.put( "TabbedPane.textIconGap", new Integer(16) );
//  def.put( "TabbedPane.background", Color.BLUE );
//  def.put( "TabbedPane.tabInsets", new Insets(10,10,10,10) );
//  def.put( "TabbedPane.selectedTabPadInsets", new Insets(10,20,10,20) );
  
  // http://www.java2s.com/Tutorial/Java/0240__Swing/CustomizingaJTabbedPaneLookandFeel.htm
  // TODO: Replace tabbedPane with JPanel (preferably JScrollingPane horizontal without bar) containing buttons
  public void initTabbedPane(GridBagConstraints c) {
    UIManager.put("TabbedPane.tabInsets", new Insets(tabHeight / 2, 20, tabHeight / 2, 20));
    UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
    
    tabbedPane = new JTabbedPane();
    tabbedPane.setBorder(null);
    //StashTabbedPaneUI stashTabbedPaneUI = new StashTabbedPaneUI();
    //tabbedPane.setUI(stashTabbedPaneUI);
    

    
    add(tabbedPane, c);
    
    for(int i = 0; i < stash.getNumTabs(); i++) {
      System.out.println("Creating Stash Tab " + i);
      
      StashTabUI tabUI = new StashTabUI();
      StashTab tab = stash.getTab(i);
      stashTabsUI.add(tabUI);
      tabUI.applyTabImages(tab.getTabImages());
      
      setBorder(tabUI);
      tabbedPane.add(tab.name, tabUI);
    }
    
    tabbedPane.setUI(new StashTabbedPaneUI(stashTabsUI));
    //stashTabbedPaneUI.applyStashes(stashTabsUI);
    
  }
  
  public void setBorder(StashTabUI tabUI) {
  //Compound borders
    Border compound;
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border redline = BorderFactory.createLineBorder(Color.red);
    
    Border stashBorder = new StashTabBorder(Color.RED, 5);
    //this.setBorder(stashBorder);
    
    //This creates a nice frame.
    compound = BorderFactory.createCompoundBorder(
            raisedbevel, loweredbevel);
    //this.setBorder(compound);

    HashMap<String, Integer> hashListClr = stash.getTab(0).colour;
    //System.out.println("Test: " + stash.getTab(0).toString());
    Color clr = new Color(hashListClr.get("r"), hashListClr.get("g"), hashListClr.get("b"));
    
    Border matteBorder = BorderFactory.createMatteBorder(borderSize, borderSize, borderSize, borderSize, clr);
    
    Color clr1 = Color.GRAY;
    Color clr2 = Color.GRAY.darker();
    Color clr3 = Color.BLACK;
    Color clr4 = Color.darkGray;
    Border compoundSoft;
    Border raisedsoftbevel =BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, clr1, clr2, clr3, clr4);
    Border loweredsoftbevel = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, clr4, clr3, clr2, clr1);
    compoundSoft = BorderFactory.createCompoundBorder(matteBorder, loweredsoftbevel);
    //compoundSoft = BorderFactory.createCompoundBorder(compoundSoft, loweredsoftbevel);
    //compoundSoft = BorderFactory.createCompoundBorder(loweredsoftbevel, raisedsoftbevel);
    
    tabUI.setBorder(compoundSoft);
    
    
    //Add a red outline to the frame.
//    compound = BorderFactory.createCompoundBorder(
//            redline, compound);
//    this.setBorder(compound);

    //Add a title to the red-outlined frame.
//    compound = BorderFactory.createTitledBorder(
//            compound, "title",
//            TitledBorder.CENTER,
//            TitledBorder.BELOW_BOTTOM);
//    this.setBorder(compound);
    
  }
  
  public void setConstraints(GridBagConstraints c, int x, int y) {
    c.gridx = x;
    c.gridy = y;
  }
  
  public void setConstraints(GridBagConstraints c, int x, int y, int w, int h, double weightx, double weighty) {
    c.gridx = x;
    c.gridy = y;
    c.gridwidth = w;
    c.gridheight = h;
    c.weightx = weightx;
    c.weighty = weighty;
  }
  
  // TODO: Generate from Stash class
  public StashTabUI initStashTabUI(int tab) {
    // Test with one Stash Tab
    return new StashTabUI();
  }
  
  public void setBorder(Border border) {
    super.setBorder(border);
    
    if(border == null) {
      totalBorderWidth = 0;
      totalBorderHeight = 0;
    } else {
      Insets insets = border.getBorderInsets(this);
      totalBorderWidth = insets.left + insets.right;
      totalBorderHeight = insets.top + insets.bottom;
    }
  }
  
  public Dimension getStashSize() {
    BufferedImage stashTabImage = ImageManager.getImageManager().stashPanelGrid;
    int w = stashTabImage.getWidth() + totalBorderWidth;
    int h = stashTabImage.getHeight() + totalBorderHeight + tabHeight;
    return new Dimension(w, h);
  }
  
  public static class StashTabbedPaneUI extends BasicTabbedPaneUI {

    public List<StashTabUI> stashTabsUI;
    
    public StashTabbedPaneUI(List<StashTabUI> stashTabsUI) {
      this.stashTabsUI = stashTabsUI;
    
      contentBorderInsets = new Insets(0, 0, 0, 0);
    }
    
//    public void applyStashes(List<StashTabUI> stashTabsUI) {
//      this.stashTabsUI = stashTabsUI;
//    }

    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, 
               int tabIndex, Rectangle iconRect, Rectangle textRect) {
        
      StashTabUI tabUI = stashTabsUI.get(tabIndex);
      BufferedImage tabImgL = tabUI.getTabImage(0, true);
      BufferedImage tabImgC = tabUI.getTabImage(1, true);
      BufferedImage tabImgR = tabUI.getTabImage(2, true);
      
      // Take height of left image but should be same for all 3 anyways or it will look wrong
      int tabHeight = tabImgL.getHeight();
      
      int x = rects[tabIndex].x;
      int y = rects[tabIndex].y;
      int w = rects[tabIndex].width;
      int h = rects[tabIndex].height;
      y = h - tabHeight;

      
      int centerWidth = w - (tabImgL.getWidth() + tabImgR.getWidth());
      
      //g.drawImage((Image) tabImgL, 0, 0, 0, 0, this);
      g.drawImage((Image) tabImgL, x, y, tabImgL.getWidth(), tabImgL.getHeight(), null);
      g.drawImage((Image) tabImgC, 
          x + tabImgL.getWidth(), 
          y, 
          centerWidth,
          tabImgC.getHeight(), null);
      
      g.drawImage((Image) tabImgR, 
          x + tabImgL.getWidth() + centerWidth, 
          y, 
          tabImgR.getWidth(), 
          tabImgR.getHeight(), null);
      
      /*
        Color savedColor = g.getColor();
        g.setColor(Color.PINK);
        g.fillRect(rects[tabIndex].x, rects[tabIndex].y, 
               rects[tabIndex].width, rects[tabIndex].height);
        g.setColor(Color.BLUE);
        g.drawRect(rects[tabIndex].x, rects[tabIndex].y, 
               rects[tabIndex].width, rects[tabIndex].height);
        g.setColor(savedColor);
        
      */
      
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      Font[] fonts = ge.getAllFonts();

      for (Font font : fonts) {
          System.out.print(font.getFontName() + " : ");
          System.out.println(font.getFamily());
      }
      
      Font font = new Font("Fontin SmallCaps", Font.PLAIN, 18);
      g.setFont(font);
      FontMetrics metrics = g.getFontMetrics(font);
      
      String text = "Test";
      
      int textWidth = metrics.stringWidth(text);
      //int textHeight = metrics.getHeight() / 2 + metrics.getAscent();
      x = (w - textWidth) / 2;
      y = y / 2 + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
      g.drawString(text, x, y);
    }
 }
}
