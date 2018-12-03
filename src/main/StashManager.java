package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import image.ImageManager;
import item.Stash;
import ui.StashUI;
import util.Utils;

// set ContentPane and use getContentPane();
public class StashManager extends JFrame {
  private static final long serialVersionUID = 8372458581121010956L;

  public Stash stash;
  public StashUI stashUI;
  
  public void init() {
    setTitle("StashManager");
    
    initFonts();
    
    // Init stash data
    initData();
    
    // Add components
    initComponents();
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setLocationByPlatform(true);
    setVisible(true);

    resizeStashManager();
  }
  
  public void initFonts() {
    try {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/Fontin-Bold.ttf"))); // Fontin Bold
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/Fontin-Italic.ttf"))); // Fontin Italic
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/Fontin-Regular.ttf"))); // Fontin Regular
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/Fontin-SmallCaps.ttf"))); // Fontin SmallCaps
      
    } catch (IOException|FontFormatException e) {
      e.printStackTrace();
    }
  }
  
  public void initData() {
    //StashTab stashTab = StashTab.readStashTab(StashTab.getStashTabTestData());
    //System.out.println(stashTab.toString());
    
    stash = Stash.readStash(Stash.testJsonData());
    System.out.println(stash.toString());
  }

  public void initComponents() {
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    String iconURL = "http://web.poecdn.com//image//Art//2DItems//Gems//AssassinsMark.png";
    // add(new JLabel(new ImageIcon(ImageManager.getImageManager().getIcon(iconURL))), c);
    
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 1;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    stashUI = new StashUI(stash);
    getContentPane().add(stashUI, c);
  }
  
  public void resizeStashManager() {
    Dimension size = this.getSize();
    System.out.println("Frame size: " + getSize().toString());
    System.out.println("Inner size: " + Utils.getInnerSize(this).toString());
    System.out.println("StashUI size: " + stashUI.getSize().toString());
    System.out.println("StashUI StashTab size: " + stashUI.getStashSize().toString());
    
    Dimension innerSize = Utils.getInnerSize(this);
    double borderWidth = size.getWidth() - innerSize.getWidth();
    double borderHeight = size.getHeight() - innerSize.getHeight();
    System.out.println("Border size; width=" + borderWidth + ", height=" + borderHeight);
    
    Dimension stashSize = stashUI.getStashSize();
    double width = stashSize.getWidth() + borderWidth;
    double height = stashSize.getHeight() + borderHeight;
    System.out.println("width=" + width + ", height=" + height);
    
    size.setSize(width, height);
    System.out.println("Size: " + size.toString());
    this.setSize(size);
  }
  
  public void test() {
    String iconURL = "http://web.poecdn.com//image//Art//2DItems//Gems//AssassinsMark.png";
    add(new JLabel(new ImageIcon(ImageManager.getImageManager().getIcon(iconURL))));
  }
  
  public static void main(String[] args) {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        new StashManager().init();
      }
    };
    
    EventQueue.invokeLater(runnable);
  }
	

	
	
	
	
	
	

	
//	public static Dimension getWindowBorderDimension() {
//	  return new Dimension(6, 29);
//	}
}
