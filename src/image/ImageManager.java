package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

// Example Icon URL
// Normal: 78x78
// http://web.poecdn.com//image//Art//2DItems//Gems//AssassinsMark.png
// Scaled: 47x47
// http:\/\/web.poecdn.com\/image\/Art\/2DItems\/Gems\/AssassinsMark.png?scale=1&scaleIndex=0&w=1&h=1&v=8c4b5e92ec890630d572e4f48d930ca2

// TODO: Add Thread for IconManager and update when done downloading (Show placeholder image while not downloaded)
//       Add List with Icons still downloading and set dirty in UI, so they get updated when done. Or notify the UI
public class ImageManager {
  // Singleton
  public static ImageManager imageManager = new ImageManager();
  public static ImageManager getImageManager() {
    return imageManager;
  }
  private ImageManager() {
    init();
  }
  
  public BufferedImage stashPanelGrid;
  
  // TODO: Save only image name (e.g. AssassinsMark.png) instead of whole URL
  public HashMap<String, BufferedImage> icons; // Name and Image
  public BufferedImage emptyIcon = null; // TODO: Set missing icon graphic (? Question mark on inv slot or Crossed Item /)
  public String iconFolderPath;
  
  //public static final String ICON_PATH = File.separator + "icons";
  public final String IMAGEFOLDER = "image" + File.separator + "icons";
  public final String IMAGE_FILETYPE = ".png";
  
  public final String IMAGEFOLDER_UI = "image/ui/";
  
  private void init() {
    icons = new HashMap<String, BufferedImage>();
    
    initImages();
    initIconFolder();
  }
  
  public File getImageFileUI(String imageName) {
    File file = new File(IMAGEFOLDER_UI + imageName);
    if(!file.exists()) {
      System.err.println(imageName + " doesn't exist at " + file.getAbsolutePath());
    }
    
    return file;
  }
  
  public void initImages() {
    try {
      stashPanelGrid = ImageIO.read(getImageFileUI("StashPanelGrid.png"));
    } catch (IOException e) {
      System.err.println("Couldn't load UI images.");
      e.printStackTrace();
    }
  }
  
  public void initIconFolder() {
    String path = "";
    
    try {
      path = new File(ImageManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

    } catch (URISyntaxException e) {
      // TODO: Don't save images on computer, instead buffer all while app running
      e.printStackTrace();
    }
    
    String iconPath = path + File.separator + IMAGEFOLDER;
    System.out.println("Icons Folder Path: " + iconPath);
    File iconFolder = new File(iconPath);
    
    if(!iconFolder.exists()) {
      boolean folderCreated = iconFolder.mkdir();
      
      if(!folderCreated) {
        // TODO: temporal icon saving (tempIcons = true)
      }
    }
    
    iconFolderPath = iconPath + File.separator;
  }
  
  protected String getImageNameFromURL(URL url) {
    return getImageNameFromURL(url.toString());
  }
  
  protected String getImageNameFromURL(String url) {
    String startStr = "//"; // Last occurence
    String endStr = "."; // First occurence after Last occurence of startStr;
    
    String str = url.toString();
    int indexStart = str.lastIndexOf(startStr) + startStr.length();
    int indexEnd = indexStart + str.substring(indexStart).indexOf(endStr);
//    System.out.println("Cutting name from index " + indexStart + " to " + indexEnd);
    String imageName = str.substring(indexStart, indexEnd);
    
//    System.out.println("Converted URL " + url.toString() + " to name " + imageName);
    return imageName;
  }
  
  protected BufferedImage downloadIcon(String url) throws IOException {
    return downloadIcon(new URL(url));
  }
  
  // TODO: Change ImageIO.read to manual download to add progress bar
  // TODO: If necessary convert // to \ (e.g. http://web.poecdn.com//image//Art//2DItems//Gems//AssassinsMark.png)
  //       Convert URL // to \ Paths.get(url.toURI()).toFile()
  protected BufferedImage downloadIcon(URL url) throws IOException {
    System.out.println("Downloading Icon " + url);
    BufferedImage img = ImageIO.read(url);
    saveIcon(img, getImageNameFromURL(url)); // TODO: Cut name from url
    return img;
  }
  
  protected void saveIcon(BufferedImage icon, String name) {
    File outputFile = new File(iconFolderPath + name + IMAGE_FILETYPE);
    
    try {
      ImageIO.write(icon, "png", outputFile);
    } catch (IOException e) {
      System.err.println("Couldn't save image " + name + " to path " + iconFolderPath);
      e.printStackTrace();
    }
    
//    try {
//      FileOutputStream fos = new FileOutputStream(iconFolderPath + name);
//      fos.write(arg0);
//      
//    } catch (FileNotFoundException e) {
//      System.err.println("Couldn't save image " + name + " to path " + iconFolderPath);
//      e.printStackTrace();
//    }
  }
  
  protected BufferedImage loadIcon(String url) {
    String imageName = getImageNameFromURL(url);
    File imageFile = new File(iconFolderPath + imageName + IMAGE_FILETYPE);

    if(!imageFile.exists())
      return null;
    
    try {
      return ImageIO.read(imageFile);
      
    } catch (IOException e) {
      System.err.println("Failed loading " + imageName + " from path " + iconFolderPath);
      System.err.println("Tried loading file " + imageFile.getPath());
      e.printStackTrace();
    }
    
    return null;
  }
  
  public BufferedImage getIcon(String url) {
    if(icons.containsKey(url))
      return icons.get(url);
    
    // Try to find saved image on computer to prevent necessity of downloading again
    BufferedImage img = loadIcon(url);
    if(img != null) {
      icons.put(getImageNameFromURL(url), img);
      return img;
    }
    
    // Download the required image
    try {
      img = downloadIcon(url);
      icons.put(getImageNameFromURL(url), img);
      return img;
      
    } catch (IOException e) {
      System.out.println("Error downloading icon from URL: " + url);
      e.printStackTrace();
    }
    
    return emptyIcon;
  }
  
  // TODO: Delete all saved icons
  public void resetTempData() {}
}
