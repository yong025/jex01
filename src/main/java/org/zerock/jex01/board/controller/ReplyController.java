package org.zerock.jex01.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.jex01.board.dto.ReplyDTO;
import org.zerock.jex01.board.service.ReplyService;

import java.util.List;

@Log4j2
@RestController //return값이 모두 json 처리가 된다. -> content-type이 json으로 반환됨
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("")
    public String[] doA() {

        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }

        return new String[] {"AAA", "BBB", "CCCC"};

    }


    @PostMapping("")
    //@RequestBody -> json으로 들어오는 데이터를 DTO로 변환해주는 어노테이션
    public int add(@RequestBody ReplyDTO replyDTO) {

        log.info("====================================");
        log.info(replyDTO);

        return replyService.addReply(replyDTO);
    }

    @DeleteMapping("/{rno}") //특정 rno를 부르면 DeleteMapping이 되도록 uri를 설계
    public String remove(@PathVariable(name="rno") Long rno) { //PathVariable은 경로에 줬던 값을 파라미터로 바로 줄 수 있음.
        log.info("remove reply............");

        log.info("rno: " + rno);

        replyService.remove(rno);

        return "success";
    }

    @PutMapping("/{rno}")
    public String modify(@PathVariable(name="rno") Long rno,
                         @RequestBody ReplyDTO replyDTO) {
        log.info("modify reply................");

        log.info("rno : " + rno );
        log.info("replyDTO : " + replyDTO);
        log.info("=====================================");

        replyService.modify(replyDTO);

        return "success";
    }

    @GetMapping("/list/{bno}")
    public List<ReplyDTO> getBoardReplies(@PathVariable(name = "bno") Long bno) {
        //서비스 계층 호출
        return replyService.getRepliesWithBno(bno);
    }

}
