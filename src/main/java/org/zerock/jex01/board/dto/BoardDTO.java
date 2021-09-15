package org.zerock.jex01.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.domain.BoardAttach;
import org.zerock.jex01.common.dto.UploadResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BoardDTO {

    private long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCnt;

    @Builder.Default //기본값이 null이 아니라 기본 배열로 설정
    private List<UploadResponseDTO> files = new ArrayList<>();

    //dto를 vo로 변환
    public Board getDomain() {
        Board board = Board.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .replyCnt(replyCnt)
                .build();

        //files에 attach(한 게시글에 2개 이상 파일을 올리는 경우가 있으므로)가 uploadResponseDTO로 변환하는 반복문
        files.forEach(uploadResponseDTO -> {
            BoardAttach attach = BoardAttach.builder()
                    .fileName(uploadResponseDTO.getFileName())
                    .uuid(uploadResponseDTO.getUuid())
                    .path(uploadResponseDTO.getUploadPath())
                    .image(uploadResponseDTO.isImage())
                    .build();

            board.addAttach(attach);

        });

        return board;
    }
}
