package org.zerock.jex01.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.board.service.BoardService;
import org.zerock.jex01.board.service.TimeService;
import org.zerock.jex01.common.dto.PageMaker;
import org.zerock.jex01.common.dto.PageRequestDTO;
import org.zerock.jex01.common.dto.PageResponseDTO;

@Controller
@Log4j2
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {

    private final TimeService timeService;
    private final BoardService boardService;

    @GetMapping("/time")
    public void getTime(Model model) {

        log.info("==============controller getTime==================");

        model.addAttribute("time", timeService.getNow());

    }

    @GetMapping("/register")
    public void registerGet() {
        log.info("==============c        getRegister==================");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        log.info("==============c        postRegister==================");
        log.info("boardDTO : " + boardDTO);

        Long bno = boardService.register(boardDTO);

        log.info("success");
        log.info(bno);
        redirectAttributes.addFlashAttribute("result", bno); //한번만 추출하고 사용 후 바로 삭제됨

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) { //PageRequestDTO는 객체자료형이어서 자동으로 수집되어 화면으로전달됨
        log.info("==============c        getList==================" + pageRequestDTO);

        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.getDTOList(pageRequestDTO);

        model.addAttribute("dtoList", pageResponseDTO.getDtoList());

        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();
        int total = pageResponseDTO.getCount();

        model.addAttribute("pageMaker", new PageMaker(page, size, total));
    }

    @GetMapping(value = {"/read", "/modify", "/read2"}) //집합으로 표현 가능함-> 메서드 리팩토링
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        log.info("=============c        getRead==================" + bno);
        log.info("=============c        getRead==================" + pageRequestDTO);
        model.addAttribute("boardDTO", boardService.read(bno));
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        log.info("=============c        postRemove==================" + bno);

        if (boardService.remove(bno)) {
            log.info("remove success");
            redirectAttributes.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list"; //삭제완료 모달창 필요
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info("===============================");
        log.info("===============================");
        log.info("===============================");
        log.info(boardDTO);
        if(boardDTO.getFiles().size() > 0) {
            boardDTO.getFiles().forEach(dto -> log.info(dto));
        }
        log.info("===============================");
        log.info("===============================");
        log.info("===============================");


        if (boardService.modify(boardDTO)) {
            log.info("success");
            redirectAttributes.addFlashAttribute("result", "modified");
         }

        redirectAttributes.addAttribute("bno", boardDTO.getBno()); //flash가 아닌 계속 bno를 가지고 있게함
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size", pageRequestDTO.getSize());

        if (pageRequestDTO.getType() != null) {
            redirectAttributes.addAttribute("type", pageRequestDTO.getType());
            redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
        }

        return "redirect:/board/read";
    }
}
