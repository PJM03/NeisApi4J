package com.github.pjm03.neis_api4j.exception;

/**
 * Api 조회 결과에서 오류가 발생하면 발생하는 Exception <br>
 * <a href="https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17020190531110010104913&infSeq=1">학교기본정보 API</a>
 *  하단의 메시지 설명란 참고
 */
public class NeisAPIException extends RuntimeException{
    public NeisAPIException(String errorCode, String message) {
        super("[" + errorCode + "] " + message);
    }

    public NeisAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeisAPIException(String message) {
        super(message);
    }
}
