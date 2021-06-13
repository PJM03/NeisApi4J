package com.github.pjm03.neis_api4j.data;

import com.github.pjm03.neis_api4j.NeisAPI;
import com.github.pjm03.neis_api4j.adapter.DateAdapter;
import com.github.pjm03.neis_api4j.argument.Arg;
import com.github.pjm03.neis_api4j.argument.ArgInfo;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * <a href="https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17020190531110010104913&infSeq=1">학교기본정보 API</a>
 * 의 결과값을 담은 클래스
 * @author Park Jung Min
 */
@Getter
@ToString
public class SchoolInfo {

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
     * 영문학교명
     */
    @SerializedName("ENG_SCHUL_NM")
    private String EnglishSchoolName;
    /**
     * 학교종류명
     */
    @SerializedName("SCHUL_KND_SC_NM")
    private String schoolType;
    /**
     * 소재지명
     */
    @SerializedName("LCTN_SC_NM")
    private String locationName;
    /**
     * 관할조직명
     */
    @SerializedName("JU_ORG_NM")
    private String jurisdictionName;
    /**
     * 설립명
     */
    @SerializedName("FOND_SC_NM")
    private String establishmentName;
    /**
     * 도로명 우편번호
     */
    @SerializedName("ORG_RDNZC")
    private String postcode;
    /**
     * 도로명주소
     */
    @SerializedName("ORG_RDNMA")
    private String address;
    /**
     * 도로명상세주소
     */
    @SerializedName("ORG_RDNDA")
    private String addressDetail;
    /**
     * 전화번호
     */
    @SerializedName("ORG_TELNO")
    private String tellNumber;
    /**
     * 홈페이지 주소
     */
    @SerializedName("HMPG_ADRES")
    private String homepage;
    /**
     * 남녀공학 구분명
     */
    @SerializedName("COEDU_SC_NM")
    private String coeducationName;
    /**
     * 팩스 번호
     */
    @SerializedName("ORG_FAXNO")
    private String faxNumber;
    /**
     * 고등학교 구분명
     */
    @SerializedName("HS_SC_NM")
    private String highSchoolType;
    /**
     * 산업체 특별학급 존재여부
     */
    @SerializedName("INDST_SPECL_CCCCL_EXST_YN")
    private String industryClassExist;
    /**
     * 고등학교 일반/실업 구분명
     */
    @SerializedName("HS_GNRL_BUSNS_SC_NM")
    private String highSchoolTypeDetail;
    /**
     * 특수목적고등학교 계열명
     */
    @SerializedName("SPCLY_PURPS_HS_ORD_NM")
    private String specialHighSchoolCurriculum;
    /**
     * 입시전후 구분명
     */
    @SerializedName("ENE_BFE_SEHF_SC_NM")
    private String entranceExamPeriod;
    /**
     * 주/야 구분명
     */
    @SerializedName("DGHT_SC_NM")
    private String dayNightType;
    /**
     * 설립일자
     */
    @JsonAdapter(DateAdapter.class)
    @SerializedName("FOND_YMD")
    private Date establishmentDate;
    /**
     * 개교기념일
     */
    @JsonAdapter(DateAdapter.class)
    @SerializedName("FOAS_MEMRD")
    private Date schoolAnniversary;
    /**
     * 수정일
     */
    @JsonAdapter(DateAdapter.class)
    @SerializedName("LOAD_DTM")
    private Date editDate;

    /**
     * <a href="https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17020190531110010104913&infSeq=1">학교기본정보 API</a>
     * 를 요청하는데 사용되는 선택 인자
     * @see ArgInfo
     * @see NeisAPI#getSchoolInfo(Arg...)
     */
    @AllArgsConstructor
    @Getter
    public static class OptionalArgs<T> implements ArgInfo<T> {
        /**
         * 시도교육청 코드
         */
        public static final OptionalArgs<String> EDUCATION_OFFICE_CODE = new OptionalArgs<>("ATPT_OFCDC_SC_CODE", String.class);
        /**
         * 표준 학교코드
         */
        public static final OptionalArgs<String> SCHOOL_CODE = new OptionalArgs<>("SD_SCHUL_CODE", String.class);
        /**
         * 학교명
         */
        public static final OptionalArgs<String> SCHOOL_NAME = new OptionalArgs<>("SCHUL_NM", String.class);
        /**
         * 학교종류명
         */
        public static final OptionalArgs<String> SCHOOL_TYPE = new OptionalArgs<>("SCHUL_KND_SC_NM", String.class);
        /**
         * 소재지명
         */
        public static final OptionalArgs<String> LOCATION_NAME = new OptionalArgs<>("LCTN_SC_NM", String.class);
        /**
         * 설립명
         */
        public static final OptionalArgs<String> ESTABLISHMENT_NAME = new OptionalArgs<>("FOND_SC_NM", String.class);

        private String name;
        private Class<T> type;

        @Override
        public String valueToString(T value) {
            return value.toString();
        }
    }

//    public enum Establishment {
//        PUBLIC("공립"),
//        PRIVATE("사립"),
//        NATIONAL("국립");
//
//        private String value;
//
//        Establishment(String value) {
//            this.value = value;
//        }
//
//        public String getValue() {
//            return value;
//        }
//    }
}
