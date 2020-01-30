package cat.tecnocampus.omega.webControllers;


import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLDataException;

@Controller
@ControllerAdvice
public class HandlingExceptionController {
    @Aspect
    @Component
    private class publicLoggerAdvice {
        private final org.slf4j.Logger logger = LoggerFactory.getLogger(publicLoggerAdvice.class);

    }

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(publicLoggerAdvice.class);
//    @ExceptionHandler(Exception.class)
//    @GetMapping("errorAll")
//    public String handleError(HttpServletRequest request, Model model) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        String errorMsg = "Error";
//
//        if (status != null) {
//            Integer statusCode = Integer.valueOf(status.toString());
//
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                errorMsg = "Http Error Code: 400. Bad Request";
//            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
//                errorMsg = "Http Error Code: 401. Unauthorized";
//            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//                errorMsg = "Http Error Code: 403. Unauthorized";
//            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                errorMsg = "Http Error Code: 404. Resource not found";
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                errorMsg = "Http Error Code: 500. Internal Server Error";
//            } else errorMsg = "Error number: " + status.toString();
//        } else
//            errorMsg = "Undefined error";
//
//        model.addAttribute("errorMsg", errorMsg);
//        return "error/exceptionAll";
//
//    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @GetMapping("usernameDoesNotExist")
    public String handleUsernameDoesNotExist(Model model, HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        logger.error("Request: " + url + " raised " + ex);

        model.addAttribute("username", url.substring(url.lastIndexOf("/") + 1));
        return "error/usernameDoesNotExist";
    }

    @ExceptionHandler({SQLDataException.class, DataAccessException.class})
    @GetMapping("databaseException")
    public String databaseError(Model model, HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        logger.error("Request: " + url + " raised " + ex);

        model.addAttribute("where", url.substring(url.lastIndexOf("/") + 1));
        return "error/databaseException";
    }

    @ExceptionHandler(NullPointerException.class)
    @GetMapping("exceptionNullPointer")
    public String ExceptionNullPointer(Model model, HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        logger.error("Request: " + url + " raised " + ex);

        model.addAttribute("where", url.substring(url.lastIndexOf("/") + 1));
        return "error/exceptionNullPointer";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @GetMapping("ilegalArgumentError")
    public String IlegalArgument(Model model, HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        logger.error("Request: " + url + " raised " + ex);

        model.addAttribute("where", url.substring(url.lastIndexOf("/") + 1));
        return "error/ilegalArgumentError";
    }

    @ExceptionHandler(ConversionFailedException.class)
    @GetMapping("conversionException")
    public String conversionFailed(Model model, HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        logger.error("Request: " + url + " raised " + ex);

        model.addAttribute("where", url.substring(url.lastIndexOf("/") + 1));

        return "error/conversionException";
    }


    @ExceptionHandler(MissingPathVariableException.class)
    @GetMapping("MissingVariableException")
    public String MisingVariablePathError(Model model, HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        logger.error("Request: " + url + " raised " + ex);

        model.addAttribute("where", url.substring(url.lastIndexOf("/") + 1));

        return "error/MissingVariableException";
    }
}


