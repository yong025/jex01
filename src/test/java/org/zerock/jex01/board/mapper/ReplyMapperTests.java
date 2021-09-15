package org.zerock.jex01.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.board.domain.Reply;
import org.zerock.jex01.common.config.RootConfig;

import java.util.stream.IntStream;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BoardRootConfig.class, RootConfig.class})
public class ReplyMapperTests {

    @Autowired(required = false) //spring이 로딩 할 때 체크하지 않음 -> 에러메시지가 안뜨게됨.
    private ReplyMapper replyMapper;

    @Test
    public void insertDummies() {
        log.info("test reply insert...........");

        long[] arr = {539L, 538L, 533L, 532L, 530L}; //bno로 사용

        IntStream.rangeClosed(1, 100).forEach(num -> {

            long bno = arr[((int) (Math.random() * 1000)) % 5]; //
            Reply reply = Reply.builder()
                    .bno(bno)
                    .reply("댓글..." + num)
                    .replyer("user" + (num) % 10)
                    .build();

            replyMapper.insert(reply);
        });
    }

    @Test
    public void testList() {
        Long bno = 538L;

        replyMapper.getListWithBoard(bno).forEach(reply -> log.info(reply));
    }




}
