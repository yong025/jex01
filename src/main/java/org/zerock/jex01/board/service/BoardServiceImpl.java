package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.board.mapper.BoardMapper;
import org.zerock.jex01.common.dto.PageRequestDTO;
import org.zerock.jex01.common.dto.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = boardDTO.getDomain();

        boardMapper.insert(board);

        Long bno = board.getBno();

        //bno는 게시글이 등록할 때 만들어지므로 따로 bno를 넣어주어야함.
        board.getAttachList().forEach(boardAttach -> {
            boardAttach.setBno(bno);
            boardMapper.insertAttach(boardAttach);
        });

        return board.getBno();
    }

    @Override
    public PageResponseDTO<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO) {
        List<BoardDTO> dtoList = boardMapper.getList(pageRequestDTO).stream().map(board -> board.getDTO()).collect(Collectors.toList());
        int count = boardMapper.getCount(pageRequestDTO);

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>builder()
                .dtoList(dtoList)
                .count(count)
                .build();

        return pageResponseDTO;
    }

    @Override
    public BoardDTO read(Long bno) {
        Board board = boardMapper.select(bno);

        if (board != null) { //board에 값이 들어있는 경우 getDTO로 변환한 다음 리턴.
            return board.getDTO();
        }
        return null;
    }

    @Override
    public boolean remove(Long bno) {
        return boardMapper.delete(bno) > 0;
    }

    @Override
    public boolean modify(BoardDTO boardDTO) {

        //전체 attach 파일들 모두 삭제
        boardMapper.deleteAttach(boardDTO.getBno());

        Board board = boardDTO.getDomain();
        Long bno = board.getBno();

        //그 후 다시 attach 목록들을 갱신(insert)
        board.getAttachList().forEach(boardAttach -> {
            boardAttach.setBno(bno);
            boardMapper.insertAttach(boardAttach);
        });

        return boardMapper.update(board) > 0;
    }


}
