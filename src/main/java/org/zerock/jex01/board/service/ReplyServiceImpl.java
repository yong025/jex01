package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.jex01.board.dto.ReplyDTO;
import org.zerock.jex01.board.mapper.BoardMapper;
import org.zerock.jex01.board.mapper.ReplyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    //생성자를 통한 주입 방법이기 때문에 @RequiredArgsConstructor 사용 -> 가장 안전하고 짧게 쓸 수 있어서 많이 사용
    private final ReplyMapper replyMapper;
    private final BoardMapper boardMapper;

    @Override //댓글 추가하는 기능
    public int addReply(ReplyDTO replyDTO) {
        int count = replyMapper.insert(dtoToEntity(replyDTO));
        boardMapper.updateReplyCnt(replyDTO.getBno(), 1);

        return count;
    }

    @Override
    public List<ReplyDTO> getRepliesWithBno(Long bno) {
        return replyMapper.getListWithBoard(bno).stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    public int remove(Long rno) {

        return replyMapper.delete(rno);
    }

    @Override
    public int modify(ReplyDTO replyDTO) {
        return replyMapper.update(dtoToEntity(replyDTO));
    }
}
