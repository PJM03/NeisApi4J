package com.github.pjm03.neis_api4j.argument;

import com.github.pjm03.neis_api4j.data.SchoolInfo;
import com.github.pjm03.neis_api4j.data.SchoolMealInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiType<T> {
    public static final ApiType<SchoolInfo> SCHOOL_INFO = new ApiType<>("schoolInfo", SchoolInfo.class);
    public static final ApiType<SchoolMealInfo> SCHOOL_MEAL_INFO = new ApiType<>("mealServiceDietInfo", SchoolMealInfo.class);

    private String path;
    private Class<T> resultClass;
}
