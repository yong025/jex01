package org.zerock.jex01.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder //제네릭은 Builder 사용에 제한적임 -> 문법이 달라짐
@AllArgsConstructor
@NoArgsConstructor
//제네릭을 이용하여 Board,Member 등에서 재활용 가능
//여기서 e는 엘리먼트(element)
public class PageResponseDTO<E> {

    private List<E> dtoList;
    private int count;

}
