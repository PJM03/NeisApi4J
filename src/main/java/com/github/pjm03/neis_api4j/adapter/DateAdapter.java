package com.github.pjm03.neis_api4j.adapter;

import com.github.pjm03.neis_api4j.NeisAPI;
import com.github.pjm03.neis_api4j.exception.NeisAPIException;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String dateString = context.deserialize(json, String.class);
        try {
            return NeisAPI.DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new NeisAPIException("날짜 파싱 중 오류가 발생했습니다. (value: " + dateString + ")", e);
        }
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
        JsonElement json = context.serialize(NeisAPI.DATE_FORMAT.format(date), String.class);
        JsonObject ob = json.getAsJsonObject();
        ob.addProperty("test", "hi");
        return ob;
    }
}
