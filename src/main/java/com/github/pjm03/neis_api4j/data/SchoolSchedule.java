package com.github.pjm03.neis_api4j.data;

import com.github.pjm03.neis_api4j.NeisAPI;
import com.github.pjm03.neis_api4j.adapter.DateAdapter;
import com.github.pjm03.neis_api4j.adapter.EventStatusAdapter;
import com.github.pjm03.neis_api4j.adapter.StringToIntegerAdapter;
import com.github.pjm03.neis_api4j.argument.ArgInfo;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * <a href="https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17220190722175038389180&infSeq=2">학사일정 API</a>
 * 의 결과값을 담은 클래스
 *
 * @author Park Jung Min
 */
@ToString
@Getter
public class SchoolSchedule {

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
     * 학년도
     */
    @JsonAdapter(StringToIntegerAdapter.class)
    @SerializedName("AY")
    private int year;
    /**
     * 주야과정명
     */
    @SerializedName("DGHT_CRSE_SC_NM")
    private String dayNightType;
    /**
     * 학교과정명
     */
    @SerializedName("SCHUL_CRSE_SC_NM")
    private String schoolType;

    @SerializedName("SBTR_DD_SC_NM")
    private String scheduleType;

    @JsonAdapter(DateAdapter.class)
    @SerializedName("AA_YMD")
    private Date scheduleDate;

    @SerializedName("EVENT_NM")
    private String scheduleName;

    @SerializedName("EVENT_CNTNT")
    private String scheduleContent;

    @JsonAdapter(EventStatusAdapter.class)
    @SerializedName("ONE_GRADE_EVENT_YN")
    private EventStatus isFirstGradeEvent;

    @JsonAdapter(EventStatusAdapter.class)
    @SerializedName("TW_GRADE_EVENT_YN")
    private EventStatus isSecondGradeEvent;

    @JsonAdapter(EventStatusAdapter.class)
    @SerializedName("THREE_GRADE_EVENT_YN")
    private EventStatus isThirdGradeEvent;

    @JsonAdapter(EventStatusAdapter.class)
    @SerializedName("FR_GRADE_EVENT_YN")
    private EventStatus isFourthGradeEvent;

    @JsonAdapter(EventStatusAdapter.class)
    @SerializedName("FIV_GRADE_EVENT_YN")
    private EventStatus isFifthGradeEvent;

    @JsonAdapter(EventStatusAdapter.class)
    @SerializedName("SIX_GRADE_EVENT_YN")
    private EventStatus isSixthGradeEvent;

    @JsonAdapter(DateAdapter.class)
    @SerializedName("LOAD_DTM")
    private Date editDate;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class OptionalArgs<T> implements ArgInfo<T> {
        /**
         * 주야과정명
         */
        public static final OptionalArgs<String> DAY_NIGHT_TYPE = new OptionalArgs<>("DGHT_CRSE_SC_NM", String.class);
        /**
         * 학교과정명
         */
        public static final OptionalArgs<String> SCHOOL_TYPE = new OptionalArgs<>("SCHUL_CRSE_SC_NM", String.class);
        /**
         * 학사일자
         */
        public static final OptionalArgs<Date> SCHEDULE_DATE = new OptionalArgs<>("AA_YMD", Date.class) {
            @Override
            public String valueToString(Date value) {
                return NeisAPI.DATE_FORMAT.format(value).substring(0, 4);
            }
        };
        /**
         * 학사시작일자
         */
        public static final OptionalArgs<Date> SCHEDULE_DATE_FROM = new OptionalArgs<>("AA_FROM_YMD", Date.class) {
            @Override
            public String valueToString(Date value) {
                return NeisAPI.DATE_FORMAT.format(value).substring(0, 4);
            }
        };
        /**
         * 학사종료일자
         */
        public static final OptionalArgs<Date> SCHEDULE_DATE_TO = new OptionalArgs<>("AA_TO_YMD", Date.class) {
            @Override
            public String valueToString(Date value) {
                return NeisAPI.DATE_FORMAT.format(value).substring(0, 4);
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
    public enum EventStatus {
        /**
         * Yes
         */
        YES("Y"),
        /**
         * No
         */
        NO("N"),
        /**
         * 존재하지 않는 학년
         */
        X("*");

        private String value;
        public static final EventStatus getByValue(String value) {
            if(value.equalsIgnoreCase("Y")) return YES;
            else if(value.equalsIgnoreCase("N")) return NO;
            else return X;
        }
    }
}
