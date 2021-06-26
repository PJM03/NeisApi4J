package com.github.pjm03.neis_api4j.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class BrSplitAdapter implements JsonSerializer<List<String>>, JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String data = context.deserialize(json, String.class);
        return Arrays.asList(data.split("<br/>"));
    }

    @Override
    public JsonElement serialize(List<String> src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(String.join("<br/>", src));
    }
}
