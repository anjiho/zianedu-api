package com.zianedu.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.zianedu.api.vo.ListOf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class JsonUtils {

    /**
     * <PRE>
     * 1. Comment : JsonString -> JsonArray 변환
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 06 .27
     * </PRE>
     * @param jsonStr
     * @return
     */
    public static JsonArray getJsonArrayFromJsonStr(String jsonStr) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonStr);
        JsonArray jsonArray = element.getAsJsonArray();
        return jsonArray;
    }

    /**
     * <PRE>
     * 1. Comment : JsonString -> JsonObject 변환
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 06 .27
     * </PRE>
     * @param jsonStr
     * @return
     */
    public static JsonObject getJsonObjectFromJsonStr(String jsonStr) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(jsonStr);
        JsonObject object = element.getAsJsonObject();
        return object;
    }

    /**
     * <PRE>
     * 1. Comment : URL GET방식 json 파싱
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 06 .27
     * </PRE>
     * @param surl
     * @return
     * @throws IOException
     */
    public static JsonObject getJsonObjectAtHttpGet(String surl) throws IOException {
        URL url = new URL(surl);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject rootObj = root.getAsJsonObject();
        return rootObj;
    }

    /**
     * <PRE>
     * 1. Comment : 객체를 String으로 변환
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 06 .27
     * </PRE>
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public static<T> String convertToJsonStr(T t) throws Exception {
        return new ObjectMapper().writeValueAsString(t);
    }

    /**
     * <PRE>
     * 1. Comment : jsonArray, Class Object를 파라미터로 넣으면 jsonArray안의 값을 객체로 담는 메서트(Gson.fromJson참조)
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 06 .28
     * 4. 예 : List<TestDto> list = getObjectFromJson(jsonArr, TestDto.class);
     * </PRE>
     * @param jsonArray
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getObjectFromJson(JsonArray jsonArray, Class<T> type) {
        return new Gson().fromJson(jsonArray.toString(), new ListOf<T>(type));
    }

    public static void main(String[] args) {

    }
}
