package team.wucaipintu.pinyipin.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

    public static int getValue(String json, String key) {
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        return object.get(key).getAsInt();
    }
}
