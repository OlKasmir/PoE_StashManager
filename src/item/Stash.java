package item;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

// Saves all items contained in the players stash
public class Stash {
  ///@JsonProperty("name")
  //public String name;
  
  public int numTabs;
  public List<StashTab> tabs;
  
  public static Stash readStash(String json) {
    ObjectMapper mapper = new ObjectMapper();
    //JsonNode rootNode = mapper.readTree(json);
    
    Stash stash = null;
    try {
      stash = mapper.readValue(json, Stash.class);

    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return stash;
  }
  
  public static String testJsonData() {
    return "{\"numTabs\":123,\"tabs\":[{\"n\":\"$\",\"i\":0,\"id\":\"e1fdee56db73c966a6486a12f40cb508bc26cf392a8e21c8154f553ae5f70505\",\"type\":\"CurrencyStash\",\"hidden\":false,\"selected\":false,\"colour\":{\"r\":255,\"g\":170,\"b\":0},\"srcL\":\"https:\\/\\/web.poecdn.com\\/gen\\/image\\/WzI0LCIwMmExOTc3ZDFkMDM0NDM2ZTc3MzlmODNkMTNiMjA3ZiIsWzIseyJ0IjoxLCJuIjoiIiwiYyI6LTIyMDE2fV1d\\/6178c7c614\\/Stash_TabL.png\",\"srcC\":\"https:\\/\\/web.poecdn.com\\/gen\\/image\\/WzI0LCIwMmExOTc3ZDFkMDM0NDM2ZTc3MzlmODNkMTNiMjA3ZiIsWzIseyJ0IjoyLCJuIjoiIiwiYyI6LTIyMDE2fV1d\\/f05becf861\\/Stash_TabC.png\",\"srcR\":\"https:\\/\\/web.poecdn.com\\/gen\\/image\\/WzI0LCIwMmExOTc3ZDFkMDM0NDM2ZTc3MzlmODNkMTNiMjA3ZiIsWzIseyJ0IjozLCJuIjoiIiwiYyI6LTIyMDE2fV1d\\/73ea31bc88\\/Stash_TabR.png\"}]}";
  }
  
  public StashTab getTab(int index) {
    return tabs.get(index);
  }
  
  public String toString() {
    StringBuilder b = new StringBuilder(500);
    b.append("numTabs: " + numTabs);
    b.append(System.getProperty("line.separator"));
    b.append(System.getProperty("line.separator"));
    
    for(int i = 0; i < tabs.size(); i++) {
      b.append(tabs.get(i).toString());
      b.append(System.getProperty("line.separator"));
    }
    
    return b.toString();
  }

  public int getNumTabs() {
    return tabs.size();
  }
}


// EXAMPLE Conversion
/*
------- THE JAVA CLASS -------
public class Staff {
  private String name;
  private int age;
  private String position;
  private BigDecimal salary;
  private List<String> skills;
}

------- THE JSON DATA -------
{"name":"mkyong","age":33,"position":"Developer","salary":7500,"skills":["java","python"]}

------- THE JSON DATA (formatted for easier reading) -------
{
  "name" : "mkyong",
  "age" : 33,
  "position" : "Developer",
  "salary" : 7500,
  "skills" : [ "java", "python" ]
}

*/



//ObjectMapper mapper = new ObjectMapper();
//String jsonInString = "{'name' : 'mkyong'}";
//
////JSON from file to Object
//Staff obj = mapper.readValue(new File("c:\\file.json"), Staff.class);
//
////JSON from URL to Object
//Staff obj = mapper.readValue(new URL("http://mkyong.com/api/staff.json"), Staff.class);
//
////JSON from String to Object
//Staff obj = mapper.readValue(jsonInString, Staff.class);
