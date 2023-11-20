package jpabasic.notice.domain;

import jakarta.persistence.AttributeConverter;

import java.util.Objects;

public class BooleanYesNoConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return Objects.equals(Boolean.TRUE, attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData) ? true : false;
    }
}
/*
 * 매핑을 지원하지 않는 자바 타입과 DB 타입간의 변환을 처리한다
 * ex) boolean - char(1) 타입 간의 변환
 * -> true / false를 Y / N 으로 변환하여 DB에 저장하고자 할 때 사용
 * 
 * 사용법
 * - AttributeConverter interface를 구현하여 사용한다
 */
