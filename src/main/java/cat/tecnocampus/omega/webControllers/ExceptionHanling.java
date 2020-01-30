package cat.tecnocampus.omega.webControllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ExceptionHanling {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHanling.class);

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleUsernameDoesNotExist(Model model, HttpServletRequest request, Exception e) {
        String url = request.getRequestURL().toString();
        logger.error("Request: " + url + " raised " + e);
        model.addAttribute("id", url.substring(url.lastIndexOf("/") + 1));
        return "error/incorrectLogin";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMistMatch(Model model, HttpServletRequest request, Exception e) {
        String url = request.getRequestURL().toString();
        logger.error("Request: " + url + " raised " + e.getMessage());

        int beginIdx = e.getMessage().indexOf("required type");
        int endIdx = e.getMessage().indexOf(";", beginIdx);

        model.addAttribute("arg", url.substring(url.lastIndexOf("/") + 1));
        model.addAttribute("type", e.getMessage().substring(beginIdx + "required type".length(), endIdx));

        return "error/MethodArgumentTypeMismatch";
    }
}
