package com.github.pjm03.neis_api4j.argument;

import com.github.pjm03.neis_api4j.NeisAPI;
import com.github.pjm03.neis_api4j.data.SchoolInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 선택인자 전달용 클래스
 * @author Park Jung Min
 * @see NeisAPI
 * @see SchoolInfo.OptionalArgs
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Arg<T> {
    private final ArgInfo<T> argInfo;
    private final T value;

    /**
     * 객체 생성자
     * @param argInfo 인자 정보를 가지고 있는 객체
     * @param value 인자 값
     * @see ArgInfo
     * @return 선택인자 전달 시 사용하는 Arg 객체 반환
     */
    public static final <K> Arg of(ArgInfo<K> argInfo, K value) {
        return new Arg<>(argInfo, value);
    }

    public String getName() {
        return argInfo.getName();
    }

    public String getValue() {
        return argInfo.valueToString(value);
    }
}
