package com.github.pjm03.neis_api4j.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

public class StringToIntegerAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Integer.parseInt(context.deserialize(json, String.class));
    }

    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.toString(), String.class);
    }
}
