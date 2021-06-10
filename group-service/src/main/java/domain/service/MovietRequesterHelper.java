package domain.service;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

public class MovietRequesterHelper {

    // For custom parsing of json fields
    // Should contain adapter for all fields in responses without builtin builder
    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // class types
        builder.registerTypeAdapter(Integer.class,
                (JsonDeserializer<Integer>) (json, typeOfT, context) -> json.getAsInt());

        builder.registerTypeAdapter(String.class,
                (JsonDeserializer<String>) (json, typeOfT, context) -> json.getAsString());

        return builder;
    }
}
