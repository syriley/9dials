package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils {
    public static JsonObject setJsonObject(JsonObject root, JsonObject node, String path) {
        
        JsonElement parent = getJsonElement(root, path);
        JsonArray newJsonArray = (JsonArray)setJsonElement(parent, node);
        while(getParentPath(path) != null)
        root.add(path, newJsonArray);
        return root;
    }
    
    public static JsonElement setJsonElement(JsonElement parent, JsonObject child) {
        if(parent.isJsonArray()) {
            JsonArray jsonArray = parent.getAsJsonArray();
            JsonArray newJsonArray = new JsonArray();
            
            boolean alreadyContained = false;
            for (JsonElement jsonElement : jsonArray) {
                JsonObject item = (JsonObject)jsonElement;
                if(item.get("id").getAsInt() == child.get("id").getAsInt()) {
                    newJsonArray.add(child);
                    alreadyContained = true;
                }
                else {
                    newJsonArray.add(jsonElement);
                }
            }
            if(!alreadyContained) {
                newJsonArray.add(child);
            }
            return newJsonArray;
        }
        return null;
    }
    
    public static String getParentPath(String path) { 
        List<String> paths;
        paths = Arrays.asList(path.split("/"));
        return StringUtils.join(paths.subList(0, paths.size() -1), "/");
    }
    
    public static JsonElement getJsonElement(JsonElement json, String path) {
        List<String> paths;
        paths = Arrays.asList(path.split("/"));
         
        for (String pathElement : paths) {
            if(json.isJsonArray()) {
                try { 
                    int index = Integer.parseInt(pathElement);
                    json = ((JsonArray)json).get(index - 1);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected Array index for " + json.toString());
                }
            }
            else if(json.isJsonObject()) {
                json = ((JsonObject)json).get(pathElement);
            }
        }
        return json;
    }
}
