package org.zerock.jex01.board.domain;

import lombok.*;

import java.time.LocalDateTime;

//Board와 마찬가지도 읽기전용으로 사용하기 위해서 Getter만 선언
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    private Long rno;
    private Long bno;
    private String replyer;
    private String reply;
    private LocalDateTime replyDate;
    private LocalDateTime modDate;

}
