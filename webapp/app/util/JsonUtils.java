package util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {
    
    public static JsonParser parser = new JsonParser();
    
    public static JsonObject mergeJsonObjects(String rootString, String nodeString, String path) {
        JsonObject node = (JsonObject)getJsonElement(nodeString);
        JsonObject root = (JsonObject)getJsonElement(rootString);
        return mergeJsonObjects(root, node, path);
    }
    
    public static JsonObject mergeJsonObjects(JsonObject root, String nodeString, String path) {
        JsonObject node = (JsonObject)getJsonElement(nodeString);
        return mergeJsonObjects(root, node, path);
    }
    
    public static JsonObject mergeJsonObjects(JsonObject root, JsonObject node, String path) {
        JsonElement parent = getJsonElement(root, path);
        mergeJsonElement(parent, node);
        return root;
    }
    
    public static void mergeJsonElement(JsonElement parent, JsonObject child) {
        if(parent.isJsonArray()) {
            JsonArray jsonArray = parent.getAsJsonArray();
            boolean alreadyInArray = false;
            
            for (JsonElement jsonElement : jsonArray) {
                JsonObject item = (JsonObject)jsonElement;
                if(item.get("id").getAsInt() == child.get("id").getAsInt()) {
                    for(Map.Entry<String,JsonElement> entry : child.entrySet()) {
                        item.add(entry.getKey(), entry.getValue());
                    }
                    alreadyInArray = true;
                    break;
                }
            }
            if(!alreadyInArray){
                jsonArray.add(child);
            }
        }
        else if(parent.isJsonObject()){
            for(Map.Entry<String,JsonElement> entry : child.entrySet()) {
                parent.getAsJsonObject().add(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public static JsonObject getJsonObject(String jsonString) {
        return parser.parse(jsonString).getAsJsonObject();
    }
    
    public static JsonArray getJsonArray(String jsonString) {
        return parser.parse(jsonString).getAsJsonArray();
    }
    
    public static String getString(String bodyString, String jsonString) {
        JsonObject body = parser.parse(bodyString).getAsJsonObject();
        return body.get(jsonString).getAsString();
    }
    
    public static JsonElement getJsonElement(String jsonString) {
        return parser.parse(jsonString);
    }
    
    public static String getParentPath(String path) { 
        List<String> paths;
        paths = Arrays.asList(path.split("/"));
        if(paths.size() == 1) {
            return null;
        }
        return StringUtils.join(paths.subList(0, paths.size() -1), "/");
    }
    
    public static JsonElement getJsonElement(JsonElement json, String path) {
        if (StringUtils.isEmpty(path)){
            return json;
        }
        List<String> paths;
        paths = Arrays.asList(path.split("/"));
         
        for (String pathElement : paths) {
            if(json.isJsonArray()) {
                try { 
                    int id = Integer.parseInt(pathElement);
                    json = getById(json.getAsJsonArray(), id);
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
    
    public static JsonObject getById(JsonArray array, int id) {
        for (JsonElement element : array) {
            JsonObject jsonElement = element.getAsJsonObject();
            if(jsonElement.get("id").getAsInt() == id) {
                return jsonElement.getAsJsonObject();
            }
        }
        return null;
    }
}
