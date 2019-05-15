package com.zianedu.api.utils;

import com.google.gson.*;
import com.zianedu.api.vo.ListOf;

import java.util.List;

public class GsonUtil {

    public static JsonObject convertStringToJsonObj(String jsonStr) {
        if (jsonStr == null || "".equals(jsonStr)) return null;
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonStr);
        JsonObject object = element.getAsJsonObject();
        return object;
    }

    public static JsonArray convertStringToJsonArray(String jsonStr) {
        if (jsonStr == null || "".equals(jsonStr)) return null;
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonStr);
        JsonArray jsonArray = element.getAsJsonArray();
        return jsonArray;
    }

    public static <T> List<T> getObjectFromJsonArray(JsonArray jsonArray, Class<T> type) {
        return new Gson().fromJson(jsonArray.toString(), new ListOf<>(type));
    }
}
