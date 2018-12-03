package tools;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import util.Path;

// Downloads all Images from the Path of Exile site found in the .css File
public class ImageDownloader {
  
  public String getParentPath() {
    String data;
    try {
      data = Path.getPath();
      File parentFolder = new File(data);
      String folderPath = parentFolder.getParent();
      return folderPath;
      
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    
    return "";
  }
  
  public ImageDownloader() throws URISyntaxException {
    File dataFile = new File(getParentPath() + File.separator + "ExtractImageURL.txt");
    
    System.out.println("Path: " + dataFile.toString());
    
    downloadImages(dataFile);
  }
  
  public List<String> fetchURLs(File data) {
    List<String> urls = new ArrayList<String>();
    
    try {
      BufferedReader br = new BufferedReader(new FileReader(data));
      
      String line;
      while(true) {
        line = br.readLine();
        
        if(line == null)
          break;
        
//        String regex = "url('*');";
//        Matcher m = Pattern.compile(regex).matcher(regex);
        
          // Java 9 required
//        String regex = "url('*');";
//        String[] matches = Pattern.compile(regex)
//            .matcher(line)
//            .results()
//            .map(MatchResult::group)
//            .toArray(String[]::new);
        
//        String[] urlsLine = line.split();
//        for(int i = 0; i < urlsLine.length; i++) {
//          urls.add(urlsLine[i]);
//        }
        
        String regex = "url\\('(.*?)'\\);";
        Matcher m = Pattern.compile(regex).matcher(line);
        while(m.find()) {
          urls.add(m.group(1));
        }
      }
      
      br.close();
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    System.out.println("Found " + urls.size() + " URLs");
    
    for(String url : urls) {
      System.out.println(url);
    }
    
    return urls;
  }
  
  public void downloadImages(File data) {
    List<String> urls = fetchURLs(data);
    
    String outputPath = getParentPath() + File.separator + "imagesWebsite" + File.separator;
    File outputFolder = new File(outputPath);
    if(!outputFolder.exists()) {
      outputFolder.mkdirs();
    }
    
    String baseURL = "http://web.poecdn.com";
    File output;
    
    //int i = 0;
    String totalURL;
    
    String url = null;
    
    // TODO: Last start was from 300 to End. Next time do 0 to 300
    for(int i = 990; i < urls.size(); i++) {
      url = urls.get(i);
      
      //i++;
      totalURL = baseURL + url;
      System.out.print(i + " of " + urls.size() + " (" + totalURL + ") ");
      
      
      // Download Image
      BufferedImage img = null;
      try {
        img = ImageIO.read(new URL(totalURL));
        System.out.print("Downloaded. ");
      } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      if(img == null) {
        System.out.println();
        continue;
      }
      
      // Save Image
      int indexBeginTypeName = url.lastIndexOf("/");
      int indexQuestionMark = url.substring(indexBeginTypeName).indexOf("?");
      int indexEndType = url.length();
      if(indexQuestionMark != -1) {
        indexEndType = indexBeginTypeName + indexQuestionMark;
      }
      
      int indexBeginType = indexBeginTypeName + url.substring(indexBeginTypeName, indexEndType).indexOf(".");
      String fileType = url.substring(indexBeginType + 1, indexEndType);
      
      System.out.println("FileType: " + fileType);
//      if(i > 0) break;
      
      int indexBegin = url.lastIndexOf("/");
      int indexEnd = indexBegin + url.substring(indexBegin).indexOf(".");
      String fileName = url.substring(indexBegin, indexEnd);
      
      output = new File(outputPath + fileName + "." + fileType);
      
      int j = 2;
      while(output.exists()) {
        output = new File(outputPath + fileName + "_" + j + "." + fileType);
        j++;
      }
      
      try {
        ImageIO.write(img, fileType, output);
        System.out.println("Saved " + output.getAbsolutePath());
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
//      if(i >= 50) {
//        System.out.println("Testing successful");
//        break;
//      }
    }
  }
  
  public static void main(String[] args) throws URISyntaxException {
    new ImageDownloader();
  }

}
