package org.zerock.jex01.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default //값이 없다면 기본적으로 값을 설정
    private int page = 1;

    @Builder.Default
    private int size = 10;

    //검색하기위한 컨텐츠 type과 검색하려는 keyword 공통으로 제공하기 위해서 PageRequestDTO에서 선언
    private String type;
    private String keyword;

    //mybatis는 getter가 내장되어있어서 get이 붙은 메서드를 가져올 수 있음 getSkip -> skip으로
    public int getSkip() {
        return (page - 1) * size; //skip해야하는 int를 구하는 메서드
    }

    public String getType() { //sql if 조건을위해 type이 존재하는지 빈란인지 식별한
        if (type == null || type.trim().length() == 0) { //trim은 공백을 제거하는 메서드
            return null;
        }
        return this.type;
    }

    public String[] getArr() {
        return type == null? new String[]{} : type.split("");
    } //split 기준으로 자르고 문자열로 저장(검색 좀 더 해볼 것)

}
