package ui;

import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import image.ImageManager;

public class StashTabUI extends JPanel {
  private static final long serialVersionUID = -3433759135302997031L;
  
  protected BufferedImage background = ImageManager.getImageManager().stashPanelGrid;
  
  // Image parts for the tab selection background
  // TODO: Update when colour of tab changes
  private BufferedImage tabImgL;
  private BufferedImage tabImgC;
  private BufferedImage tabImgR;
  
  public StashTabUI() {
    init();
  }
  
  public void init() {
    this.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent componentEvent) {
        
      }
    });
  }
  
  public void update(String jsonData) {
    
  }
  
  public BufferedImage getTabImgL() {
    return tabImgL;
  }
  
  public BufferedImage getTabImgC() {
    return tabImgC;
  }
  
  public BufferedImage getTabImgR() {
    return tabImgR;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // g.drawImage(background, 0, 0, this);
    g.drawImage(background, 0, 0, (int) this.getSize().getWidth(), (int) this.getSize().getHeight(), this);
  }

  // TODO: Implement threading
  // TODO: Use ImageManager to save tab images. Check on load if changed, if yes then reload
  // TODO: Use URL[] array as input
  public void applyTabImages(String[] tabImages) {
    // ImageManager.getImageManager().downloadImage(tabImages[0]);
    
    try {
      tabImgL = ImageIO.read(new URL(tabImages[0]));
      tabImgC = ImageIO.read(new URL(tabImages[1]));
      tabImgR = ImageIO.read(new URL(tabImages[2]));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // TODO: Add function to draw Tab Image with parameters x, y, width, height from here. Or to return the resulting image would be even better
  
  /*
   * type 0 = Left; type 1 = center; type 2 = right
   */
  public BufferedImage getTabImage(int type, boolean selected) {
    if(type > 2 || type < 0) {
      System.err.println("Invalid tab image type. 0-2 is valid. Left, Center, Right");
      return null;
    }
    
    BufferedImage img = null;
    switch(type) {
    case 0:
      img = tabImgL;
      break;
    case 1:
      img = tabImgC;
      break;
    case 2:
      img = tabImgR;
      break;
    }
    
    if(selected) {
      img = img.getSubimage(0, img.getHeight() / 2, img.getWidth(), img.getHeight() / 2);
    } else {
      img = img.getSubimage(0, 0, img.getWidth(), img.getHeight() / 2);
    }
    
    return img;
  }
}
