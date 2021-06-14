package com.github.pjm03.neis_api4j;

import com.github.pjm03.neis_api4j.data.SchoolInfo;
import com.github.pjm03.neis_api4j.data.SchoolMealInfo;
import com.github.pjm03.neis_api4j.exception.NeisAPIException;
import com.github.pjm03.neis_api4j.argument.ApiType;
import com.github.pjm03.neis_api4j.argument.Arg;
import com.github.pjm03.neis_api4j.argument.ArgInfo;
import com.google.gson.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Neis-api4j (Neis Open Api for Java)
 *
 * @author Park Jung Min
 * @version 1.0.0
 */

@AllArgsConstructor
@Getter
@Setter
public class NeisAPI {
    private static URI neisURI;
    private static Gson gson;
    private static Logger logger;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    static {
        try {
            neisURI = new URI("https://open.neis.go.kr/");
            gson = new GsonBuilder().disableHtmlEscaping().create();
            logger = Logger.getLogger("neis-api4j");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 나이스 API 인증키
     */
    private String key;
    /**
     * 페이지 위치
     */
    private Integer pIndex;
    /**
     * 페이지 당 신청 수
     */
    private Integer pSize;
    /**
     * 요청 Uri 출력 여부
     */
    private boolean showUri;

    /**
     * <a href="https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17020190531110010104913&infSeq=1">학교기본정보 API</a>
     * 를 조회하는 메서드
     * @param args 신청인자(선택인자)
     * @see Arg#of(ArgInfo, Object)
     * @see SchoolInfo.OptionalArgs
     * @see SchoolInfo
     * @return API 조회 결과를 {@link Gson}을 이용해 {@link SchoolInfo}객체로 매핑해 반환
     * @throws IOException from
     * <a href="https://javadoc.io/static/org.apache.httpcomponents/httpclient/4.5.13/org/apache/http/client/HttpClient.html#execute(org.apache.http.client.methods.HttpUriRequest)">
     * {@link HttpClient#execute(HttpUriRequest)}</a>
     * and
     * <a href="https://javadoc.io/static/org.apache.httpcomponents/httpcore/4.4.14/org/apache/http/util/EntityUtils.html#toString(org.apache.http.HttpEntity)">
     * {@link EntityUtils#toString(HttpEntity)}
     * </a>
     */
    public final List<SchoolInfo> getSchoolInfo(Arg... args) throws IOException {
        return sendRequest(ApiType.SCHOOL_INFO, Arrays.asList(args));
    }

    /**
     * <a href="https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17320190722180924242823&infSeq=1">급식식단정보 API</a>
     * 를 조회하는 메서드
     * @param educationOfficeCode 시도교육청 코드
     * @param schoolCode 표준 학교코드
     * @param args 신청인자(선택인자)
     * @see Arg#of(ArgInfo, Object)
     * @see SchoolMealInfo.OptionalArgs
     * @see SchoolMealInfo
     * @return API 조회 결과를 {@link Gson}을 이용해 {@link SchoolMealInfo}객체로 매핑해 반환
     * @throws IOException from
     * <a href="https://javadoc.io/static/org.apache.httpcomponents/httpclient/4.5.13/org/apache/http/client/HttpClient.html#execute(org.apache.http.client.methods.HttpUriRequest)">
     * {@link HttpClient#execute(HttpUriRequest)}</a>
     * and
     * <a href="https://javadoc.io/static/org.apache.httpcomponents/httpcore/4.4.14/org/apache/http/util/EntityUtils.html#toString(org.apache.http.HttpEntity)">
     * {@link EntityUtils#toString(HttpEntity)}
     * </a>
     */
    public final List<SchoolMealInfo> getSchoolMealInfo(String educationOfficeCode, String schoolCode, Arg<?>... args) throws IOException {
        return sendRequest(ApiType.SCHOOL_MEAL_INFO, new ArrayList<>() {{
            add(Arg.of(SchoolMealInfo.RequireArgs.EDUCATION_OFFICE_CODE, educationOfficeCode));
            add(Arg.of(SchoolMealInfo.RequireArgs.SCHOOL_CODE, schoolCode));
            addAll(Arrays.asList(args));
        }});
    }

    private final <T> List<T> sendRequest(ApiType<T> apiType, List<Arg<?>> args) throws IOException{
        try {
            URIBuilder uriBuilder = createURIBuilder(apiType.getPath());
            for (Arg<?> arg : args) uriBuilder.addParameter(arg.getName(), arg.getValue());

            HttpClient client = HttpClientBuilder.create().build();
            URI uri = uriBuilder.build();
            if(showUri) logger.log(Level.INFO, uri.toString());
            HttpResponse response = client.execute(new HttpGet(uri));
            String jsonString = EntityUtils.toString(response.getEntity());

            JsonObject json = gson.fromJson(jsonString, JsonObject.class);
            if (json.has("RESULT")) {
                JsonObject result = json.getAsJsonObject("RESULT");
                throw new NeisAPIException(result.get("CODE").getAsString(), result.get("MESSAGE").getAsString());
            }

            List<T> resultList = new ArrayList<>();
            JsonArray resultArray = json.getAsJsonArray(apiType.getPath()).get(1).getAsJsonObject().getAsJsonArray("row");
            resultArray.forEach(jsonElement -> {
                T result = gson.fromJson(jsonElement, apiType.getResultClass());
                resultList.add(result);
            });
            return resultList;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (JsonParseException e) {
            throw new NeisAPIException("Json 파싱 중 오류가 발생했습니다. 개발자에게 문의해 주세요.", e);
        }
    }

    private final URIBuilder createURIBuilder(String path) {
        return new URIBuilder(neisURI)
                .setPath("hub/" + path)
                .addParameter("KEY", key)
                .addParameter("Type", "json")
                .addParameter("pIndex", pIndex.toString())
                .addParameter("pSize", pSize.toString());
    }


}
