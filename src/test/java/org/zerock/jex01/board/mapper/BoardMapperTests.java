package org.zerock.jex01.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.common.config.RootConfig;
import org.zerock.jex01.common.dto.PageRequestDTO;

import java.util.stream.IntStream;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BoardRootConfig.class, RootConfig.class}) //경로 지정 필요
public class    BoardMapperTests {

    @Autowired //test코드에서는 autowired 필요
    BoardMapper boardMapper;

    @Test
    public void testDummies() {

        IntStream.rangeClosed(1,200).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("user" + (i%10))
                    .build();

            boardMapper.insert(board);
        });
    }

    @Test
    public void testGetList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("TC")
                .keyword("23")
                .build();

        log.info(pageRequestDTO);

        boardMapper.getList(pageRequestDTO).forEach(board -> log.info(board));

    }

    @Test
    public void testSelect() {

        Board board = boardMapper.select(546L);

        log.info(board);
        log.info("--------------------------");
        board.getAttachList().forEach(boardAttach -> log.info(boardAttach));

    }

    @Test
    public void testDelete() {
        long bno = 531;
        log.info("test delete..................");
        log.info((boardMapper.delete(bno)));
    }

    @Test
    public void testUpdate() {
        log.info("test update...................");
        Board board = Board.builder()
                .bno(528L)
                .title("수정제목")
                .content("수정내용")
                .build();

        log.info(boardMapper.update(board));
    }

}
