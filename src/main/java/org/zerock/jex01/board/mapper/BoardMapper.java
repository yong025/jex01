package org.zerock.jex01.board.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.domain.BoardAttach;
import org.zerock.jex01.common.dto.PageRequestDTO;

import java.util.List;

public interface BoardMapper {

//    @Insert() 여기다가 안만드는게 좋음. -> 실무에서는 xml sql을 넣기 때문에

    void insert(Board board);

    //리스트출력 -> 파라미터를 설정하여 페이지와 사이즈를 가지고옴
    List<Board> getList(PageRequestDTO pageRequestDTO);
    //전체 개수를 구함
    int getCount(PageRequestDTO pageRequestDTO);

    Board select(Long bno);

    int delete(Long bno);

    int update(Board board);

    //마이바티스에서는 파라미터를 하나밖에 받지 못하지만 @Param을 이용하여 파라미터를 2개 받음
    int updateReplyCnt(@Param("bno") Long bno,@Param("num") int num);

    int insertAttach(BoardAttach boardAttach);

    int deleteAttach(Long bno);

}
