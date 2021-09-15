package org.zerock.jex01.board.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.common.dto.PageRequestDTO;
import org.zerock.jex01.common.dto.PageResponseDTO;

@Transactional
public interface BoardService {
    //DTO를 VO로 바꾸어주는 과정이 필요하다.
    //게시물이 등록되면 해당 게시물의 번호로 등록되었다고 알려주고 싶다.->
    //파라메터 수집은  DTO 형식으로.
    Long register(BoardDTO boardDTO);

    //BoardDTO와 count를 한 번에 반환해야하는데 어떻게 보낼 것인가, -> 메서드를 나눠서 보낼 것인가 같은 클래스를 보낼것인가
    PageResponseDTO<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO);

    BoardDTO read(Long bno);

    boolean remove(Long bno);

    boolean modify(BoardDTO boardDTO);



}
