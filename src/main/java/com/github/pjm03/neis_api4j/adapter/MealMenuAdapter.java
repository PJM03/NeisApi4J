package com.github.pjm03.neis_api4j.adapter;

import com.github.pjm03.neis_api4j.data.SchoolMealInfo;
import com.github.pjm03.neis_api4j.data.SchoolMealInfo.Allergy;
import com.github.pjm03.neis_api4j.data.SchoolMealInfo.MealMenu;
import com.google.gson.*;
import org.apache.commons.codec.binary.StringUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MealMenuAdapter implements JsonSerializer<List<MealMenu>>, JsonDeserializer<List<MealMenu>> {
    @Override
    public List<MealMenu> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String data = context.deserialize(json, String.class);
        return Arrays.stream(data.split("<br/>")).map(menu -> {
            int numberIdx = menu.indexOf(".") - 1;
            String menuName = menu.substring(0, numberIdx);
            Allergy[] allergies = Arrays.stream(menu.substring(numberIdx, menu.length()).split("\\."))
                    .map(s -> Allergy.getByNum(Integer.parseInt(s))).toArray(Allergy[]::new);
            return new MealMenu(menuName, allergies);
        }).collect(Collectors.toList());
    }

    @Override
    public JsonElement serialize(List<MealMenu> mealMenuList, Type type, JsonSerializationContext context) {
        return context.serialize(String.join("<br/>", mealMenuList.stream().map(MealMenu::serialize).collect(Collectors.toList())), String.class);
    }

    //[볶음밥5.10.13., 양파소시지볶음2.5.6.10.12.13., 계란후라이1.5., 배추김치9.13., 바닐라크라운1.2.5.6.13., 김치찌개5.9.10.13.]
}
