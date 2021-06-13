package com.github.pjm03.neis_api4j.data;

import com.github.pjm03.neis_api4j.NeisAPI;
import com.github.pjm03.neis_api4j.adapter.BrSplitAdapter;
import com.github.pjm03.neis_api4j.adapter.DateAdapter;
import com.github.pjm03.neis_api4j.adapter.MealMenuAdapter;
import com.github.pjm03.neis_api4j.argument.ArgInfo;
import com.google.common.base.Enums;
import com.google.common.collect.Streams;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class SchoolMealInfo {

    /**
     * 시도교육청 코드
     */
    @SerializedName("ATPT_OFCDC_SC_CODE")
    private String educationOfficeCode;
    /**
     * 시도교육청명
     */
    @SerializedName("ATPT_OFCDC_SC_NM")
    private String educationOfficeName;
    /**
     * 표준학교코드
     */
    @SerializedName("SD_SCHUL_CODE")
    private String schoolCode;
    /**
     * 학교명
     */
    @SerializedName("SCHUL_NM")
    private String schoolName;
    /**
     * 식사코드
     */
    @SerializedName("MMEAL_SC_CODE")
    private String mealCode;
    /**
     * 식사명
     */
    @SerializedName("MMEAL_SC_NM")
    private String mealName;
    /**
     * 급식일자
     */
    @JsonAdapter(DateAdapter.class)
    @SerializedName("MLSV_YMD")
    private Date mealDate;
    /**
     * 급식인원수
     */
    @SerializedName("MLSV_FGR")
    private int peopleCount;
    /**
     * 요리명
     */
    @JsonAdapter(MealMenuAdapter.class)
    @SerializedName("DDISH_NM")
    private List<MealMenu> mealMenus;
    /**
     * 원산지 정보
     */
    @JsonAdapter(BrSplitAdapter.class)
    @SerializedName("ORPLC_INFO")
    private List<String> originCountries;
    /**
     * 칼로리 정보
     */
    @SerializedName("CAL_INFO")
    private String calorieInfo;
    /**
     * 영양정보
     */
    @JsonAdapter(BrSplitAdapter.class)
    @SerializedName("NTR_INFO")
    private List<String> nutritionInfos;
    /**
     * 급식 시작일자
     */
    @JsonAdapter(DateAdapter.class)
    @SerializedName("MLSV_FROM_YMD")
    private Date mealStartDate;
    /**
     * 급식 종료일자
     */
    @JsonAdapter(DateAdapter.class)
    @SerializedName("MLSV_TO_YMD")
    private Date mealEndDate;

    @RequiredArgsConstructor
    @Getter
    @ToString
    public static class MealMenu {
        private final String name;
        private final Allergy[] allergies;

        public String serialize() {
            return name + String.join(".", Arrays.stream(allergies).map(allergy -> Integer.toString(allergy.num)).collect(Collectors.toList()));
        }
    }

    @AllArgsConstructor
    @Getter
    public
    enum Allergy {
        EGGS(1),
        MILK(2),
        BUCKWHEAT(3),
        PEANUT(4),
        SOYBEAN(5),
        WHEAT(6),
        MACKEREL(7),
        CRAB(8),
        SHRIMP(9),
        PORK(10),
        PEACH(11),
        TOMATO(12),
        SULFURIC_ACID(13),
        WALNUT(14),
        CHICKEN(15),
        BEEF(16),
        SQUID(17),
        SHELLFISH(18),
        PINE_NUT(19);

        int num;

        public static final Allergy getByNum(int num) {
            return values()[num-1];
        }
    }

    @AllArgsConstructor
    @Getter
    public static class OptionalArgs<T> implements ArgInfo<T> {
        public static final OptionalArgs<String> MEAL_CODE = new OptionalArgs<>("MMEAL_SC_CODE", String.class);
        public static final OptionalArgs<Date> MEAL_DATE = new OptionalArgs<>("MLSV_YMD", Date.class) {
            @Override
            public String valueToString(Date value) {
                return NeisAPI.DATE_FORMAT.format(value);
            }
        };
        public static final OptionalArgs<Date> MEAL_START_DATE = new OptionalArgs<>("MLSV_FROM_YMD", Date.class) {
            @Override
            public String valueToString(Date value) {
                return NeisAPI.DATE_FORMAT.format(value);
            }
        };
        public static final OptionalArgs<Date> MEAL_END_DATE = new OptionalArgs<>("MLSV_TO_YMD", Date.class) {
            @Override
            public String valueToString(Date value) {
                return NeisAPI.DATE_FORMAT.format(value);
            }
        };

        private String name;
        private Class<T> type;

        @Override
        public String valueToString(T value) {
            return value.toString();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class RequireArgs<T> implements ArgInfo<T> {

        public static final RequireArgs<String> EDUCATION_OFFICE_CODE = new RequireArgs<>("ATPT_OFCDC_SC_CODE", String.class);
        public static final RequireArgs<String> SCHOOL_CODE = new RequireArgs<>("SD_SCHUL_CODE", String.class);

        private String name;
        private Class<T> type;

        @Override
        public String valueToString(T value) {
            return value.toString();
        }
    }
}
