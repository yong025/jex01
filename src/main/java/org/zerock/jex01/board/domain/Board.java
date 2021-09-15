package org.zerock.jex01.board.domain;

import lombok.*;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.common.dto.UploadResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//읽기전용,불변
//DB와 1:1로 대응하여 형성
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    private long bno;
    private String title,content,writer;
    private LocalDateTime regDate,modDate;
    private int replyCnt;

    @Builder.Default
    private List<BoardAttach> attachList =  new ArrayList<>();

    public BoardDTO getDTO() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .replyCnt(replyCnt)
                .build();

        //BoardAttach -> UploadResponseDTO로 변환 -> 다시 List로 묶음
        List<UploadResponseDTO> uploadResponseDTOList = attachList.stream().map(boardAttach -> {
            UploadResponseDTO uploadResponseDTO = UploadResponseDTO.builder()
                    .uuid(boardAttach.getUuid())
                    .fileName(boardAttach.getFileName())
                    .uploadPath(boardAttach.getPath())
                    .image(boardAttach.isImage()) //image의 경우 boolean이라 isImage
                    .build();
            return uploadResponseDTO;
        }).collect(Collectors.toList());

        //boardDTO에 원래 boardDTO가 가지고 있던 값과 uploadResponseDTO의 값들을 넣어줌
        boardDTO.setFiles(uploadResponseDTOList);

        return boardDTO;
    }

    public void setBno(long bno) {
        this.bno = bno;
    }

    //attachList 에 boardAttach 를 추가.
    public void addAttach(BoardAttach boardAttach) {
        attachList.add(boardAttach);
    }


}
