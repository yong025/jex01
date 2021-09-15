package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.jex01.board.mapper.TimeMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService{

    private final TimeMapper timeMapper;

    @Override
    public String getNow() { //옆에 m모양은 advice가 걸렸다는 표시

        //log.info("service...........getNow()"); ->이 기능을 aspect를 이용해서 출력하게 만듦

        return timeMapper.getTime2(); //xml로 작성한 코드
    }

    @Override
    public void addString(String str) {
        timeMapper.insertE1(str);
        timeMapper.insertE2(str);
    }
}
