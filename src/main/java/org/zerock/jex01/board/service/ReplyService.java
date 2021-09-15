package org.zerock.jex01.board.service;

import org.zerock.jex01.board.domain.Reply;
import org.zerock.jex01.board.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    int addReply(ReplyDTO replyDTO);

    List<ReplyDTO> getRepliesWithBno(Long bno);

    int remove(Long rno);

    int modify(ReplyDTO replyDTO);

    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .bno(replyDTO.getBno())
                .replyer(replyDTO.getReplyer())
                .reply(replyDTO.getReply())
                .replyDate(replyDTO.getReplyDate())
                .modDate(replyDTO.getModDate())
                .build();

        return reply;
    }

    //interface에서 default를 주면 바디를 가질 수 있음 -> 원래 인터페이스는 바디를 가질 수 없었음,,
    default ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .bno(reply.getBno())
                .reply(reply.getReply())
                .replyer(reply.getReplyer())
                .replyDate(reply.getReplyDate())
                .modDate(reply.getModDate())
                .build();

        return replyDTO;
    }

}
