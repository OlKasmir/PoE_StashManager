package item;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StashTab {
  @JsonProperty("n")
  public String name;
  
  @JsonProperty("i")
  public int index;
  
  public String id;
  public String type;
  public boolean hidden;
  public boolean selected;
  public HashMap<String, Integer> colour;
  public String srcL, srcC, srcR; // URL to the Tab graphics
  
  public StashTab() {
    
  }
  
  public String toString() {
    StringBuilder b = new StringBuilder(500);
    b.append("name: " + name);
    b.append(System.getProperty("line.separator"));
    b.append("index: " + index);
    b.append(System.getProperty("line.separator"));
    b.append("id: " + id);
    b.append(System.getProperty("line.separator"));
    b.append("type: " + type);
    b.append(System.getProperty("line.separator"));
    b.append("hidden: " + hidden);
    b.append(System.getProperty("line.separator"));
    b.append("selected: " + selected);
    b.append(System.getProperty("line.separator"));
    b.append("colour (r=" + colour.get("r") + ", g=" + colour.get("g") + ", b=" + colour.get("b") + ")");
    b.append(System.getProperty("line.separator"));
    b.append("srcL: " + srcL);
    b.append(System.getProperty("line.separator"));
    b.append("srcC: " + srcC);
    b.append(System.getProperty("line.separator"));
    b.append("srcR: " + srcR);
    b.append(System.getProperty("line.separator"));
    
    return b.toString();
  }
  
  public static StashTab readStashTab(String json) {
    ObjectMapper mapper = new ObjectMapper();
    //mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    //JsonNode rootNode = mapper.readTree(json);
    
    StashTab stashTab = null;
    try {
      stashTab = mapper.readValue(json, StashTab.class);

    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return stashTab;
  }
  
  public static String getStashTabTestData() {
    String test;
    
    // Data of one stash tab
    test = "{\"n\":\"$\",\"i\":0,\"id\":\"e1fdee56db73c966a6486a12f40cb508bc26cf392a8e21c8154f553ae5f70505\",\"type\":\"CurrencyStash\",\"hidden\":false,\"selected\":false,\"colour\":{\"r\":255,\"g\":170,\"b\":0},\"srcL\":\"https:\\/\\/web.poecdn.com\\/gen\\/image\\/WzI0LCIwMmExOTc3ZDFkMDM0NDM2ZTc3MzlmODNkMTNiMjA3ZiIsWzIseyJ0IjoxLCJuIjoiIiwiYyI6LTIyMDE2fV1d\\/6178c7c614\\/Stash_TabL.png\",\"srcC\":\"https:\\/\\/web.poecdn.com\\/gen\\/image\\/WzI0LCIwMmExOTc3ZDFkMDM0NDM2ZTc3MzlmODNkMTNiMjA3ZiIsWzIseyJ0IjoyLCJuIjoiIiwiYyI6LTIyMDE2fV1d\\/f05becf861\\/Stash_TabC.png\",\"srcR\":\"https:\\/\\/web.poecdn.com\\/gen\\/image\\/WzI0LCIwMmExOTc3ZDFkMDM0NDM2ZTc3MzlmODNkMTNiMjA3ZiIsWzIseyJ0IjozLCJuIjoiIiwiYyI6LTIyMDE2fV1d\\/73ea31bc88\\/Stash_TabR.png\"}";
    
    return test;
  }
 
  public String[] getTabImages() {
    return new String[] {srcL, srcC, srcR};
  }
}

/*
{
    "!": {
        "X": 1.0
    }, 
    "$": {
        "X": 1.0
    }, 
    "&": {
        "X": 1.0
    }, 
    "/m": {
    }, 
 .....


ObjectMapper mapper = new ObjectMapper(); 
Map<String,Object> data = mapper.readValue(new File(FileName), Map.class);
Map<String, Double> tags = (Map) data.get("SomeInput");

double value = 0;
for (String tag : tags.keySet()) {
    value = tags.get(tag); //Here I get all the data from the tags inside the input. E.g.: 0.830168776371308
    System.out.println(value); //It will print ADP, ADV and X values.
}
*/