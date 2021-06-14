import com.github.pjm03.neis_api4j.NeisAPI;
import com.github.pjm03.neis_api4j.data.SchoolInfo;
import com.github.pjm03.neis_api4j.data.SchoolMealInfo;
import com.github.pjm03.neis_api4j.exception.NeisAPIException;
import com.github.pjm03.neis_api4j.argument.Arg;
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
    public void getSchoolInfo_테스트_성공() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key, 1, 10, true);
        List<SchoolInfo> schoolInfoList = neisAPI.getSchoolInfo(
                Arg.of(SchoolInfo.OptionalArgs.EDUCATION_OFFICE_CODE, "R10"), //경상북도교육청 코드
                Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "금오공업고등학교"));
        assertEquals(schoolInfoList.size(), 1);
    }

    @Test
    public void getSchoolInfo_테스트_실패_키오류() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key + "ㅁㄴㅇㅁㄴㅇ", 1, 10, true);
        assertThrows(NeisAPIException.class, neisAPI::getSchoolInfo);
    }

    @Test
    public void getSchoolInfo_테스트_실패_데이터없음() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key + "ㅁㄴㅇㅁㄴㅇ", 1, 10, true);
        assertThrows(NeisAPIException.class, () -> neisAPI.getSchoolInfo(
                Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "여긴어디난누구")
        ));
    }

    //급식 API 테스트
    @Test
    public void getSchoolMealInfo_테스트_성공() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key, 1, 10, true);
        SchoolInfo schoolInfo = neisAPI.getSchoolInfo(Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "금오공업고등학교")).get(0);
        List<SchoolMealInfo> schoolMealInfoList = neisAPI.getSchoolMealInfo(schoolInfo.getEducationOfficeCode(), schoolInfo.getSchoolCode(),
                Arg.of(SchoolMealInfo.OptionalArgs.MEAL_DATE, new Date()));
        assertEquals(schoolMealInfoList.size(), 3);
    }

    @Test
    public void getSchoolMealInfo_테스트_실패_키오류() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key + "ㅁㄴㅇㅁㄴㅇ", 1, 10, true);
        assertThrows(NeisAPIException.class, neisAPI::getSchoolInfo);
    }

    @Test
    public void getSchoolMealInfo_테스트_실패_데이터없음() throws IOException {
        NeisAPI neisAPI = new NeisAPI(key + "ㅁㄴㅇㅁㄴㅇ", 1, 10, true);
        assertThrows(NeisAPIException.class, () -> neisAPI.getSchoolInfo(
                Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "여긴어디난누구")
        ));
    }
}
