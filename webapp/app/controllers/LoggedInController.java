package controllers;

import play.mvc.With;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controllers.securesocial.SecureSocial;

@With( SecureSocial.class )
public class LoggedInController extends UserCheckController {
    public static JsonElement toJson(Object o, ExclusionStrategy exclusionStrategy) {
        Gson gson = new GsonBuilder()
            .setExclusionStrategies(exclusionStrategy)
            .serializeNulls()
            .create();
        return gson.toJsonTree(o);
      } 
}

