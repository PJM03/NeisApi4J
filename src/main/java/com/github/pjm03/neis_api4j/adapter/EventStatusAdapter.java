package com.github.pjm03.neis_api4j.adapter;

import com.github.pjm03.neis_api4j.data.SchoolSchedule;
import com.google.gson.*;

import java.lang.reflect.Type;

public class EventStatusAdapter implements JsonSerializer<SchoolSchedule.EventStatus>, JsonDeserializer<SchoolSchedule.EventStatus> {
    @Override
    public SchoolSchedule.EventStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return SchoolSchedule.EventStatus.getByValue(context.deserialize(json, String.class));
    }

    @Override
    public JsonElement serialize(SchoolSchedule.EventStatus src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getValue(), String.class);
    }
}
