import com.github.pjm03.neis_api4j.NeisAPI;
import com.github.pjm03.neis_api4j.argument.Arg;
import com.github.pjm03.neis_api4j.data.SchoolInfo;
import com.github.pjm03.neis_api4j.data.SchoolMealInfo;
import com.github.pjm03.neis_api4j.data.SchoolSchedule;
import com.github.pjm03.neis_api4j.exception.NeisAPIException;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApiTest {
    private static String key;

    @BeforeAll
    public static void beforeAll() throws IOException {
        key = Resources.toString(Resources.getResource("api.key"), Charsets.UTF_8);
    }

    @Test
    public void 금오공업고등학교_검색시_결과_1개() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key, 1, 10, true);
        List<SchoolInfo> schoolInfoList = neisAPI.getSchoolInfo(
                Arg.of(SchoolInfo.OptionalArgs.EDUCATION_OFFICE_CODE, "R10"), //경상북도교육청 코드
                Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "금오공업고등학교"));
        assertEquals(schoolInfoList.size(), 1);
    }

    @Test
    public void 학교정보_API_키_틀릴시_NeisApiException_발생() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key + "ㅁㄴㅇㅁㄴㅇ", 1, 10, true);
        assertThrows(NeisAPIException.class, neisAPI::getSchoolInfo);
    }

    @Test
    public void 여긴어디난누구_검색시_결과없음_NeisApiException_발생() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key, 1, 10, true);
        assertThrows(NeisAPIException.class, () -> neisAPI.getSchoolInfo(
                Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "여긴어디난누구")
        ));
    }

    //급식 API 테스트
    @Test
    public void 오늘_금오공업고등학교_급식_검색시_결과_3개() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key, 1, 10, true);
        SchoolInfo schoolInfo = neisAPI.getSchoolInfo(Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "금오공업고등학교")).get(0);
        List<SchoolMealInfo> schoolMealInfoList = neisAPI.getSchoolMealInfo(schoolInfo.getEducationOfficeCode(), schoolInfo.getSchoolCode(),
                Arg.of(SchoolMealInfo.OptionalArgs.MEAL_DATE, new Date()));
        assertEquals(schoolMealInfoList.size(), 3);
    }

    //학사일정 API 테스트
    @Test
    public void _2021년_금오공업고등학교_학사일정_조회시_결과_153개() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key, 1, 200, true);
        SchoolInfo schoolInfo = neisAPI.getSchoolInfo(Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "금오공업고등학교")).get(0);
        List<SchoolSchedule> schoolScheduleList = neisAPI.getSchoolSchedule(schoolInfo.getEducationOfficeCode(), schoolInfo.getSchoolCode(),
                Arg.of(SchoolSchedule.OptionalArgs.SCHEDULE_DATE, new Date()));
        assertEquals(schoolScheduleList.size(), 153);
    }
}
