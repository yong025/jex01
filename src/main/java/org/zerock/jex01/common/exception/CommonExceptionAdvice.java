package org.zerock.jex01.common.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

//@ControllerAdvice //advice == handler
@Log4j2
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public String exceptAll(Exception ex, Model model) {
        log.error(ex.getMessage());

        model.addAttribute("exception", ex);

        return "error_page";
    }

    //ArithmeticException 수학적 예외

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handler404(NoHandlerFoundException ex) {
        return "custom404";
    }
}
