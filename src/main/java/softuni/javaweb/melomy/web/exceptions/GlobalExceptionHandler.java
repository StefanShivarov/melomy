package softuni.javaweb.melomy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(HttpClientErrorException.NotFound.class)
//    public ModelAndView handleNotFound(HttpClientErrorException.NotFound e){
//
//        ModelAndView modelAndView = new ModelAndView("404");
//        modelAndView.setStatus(HttpStatus.NOT_FOUND);
//        return modelAndView;
//    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleObjectNotFoundException(ObjectNotFoundException e){

        ModelAndView modelAndView = new ModelAndView("404");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

//    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
//    public ModelAndView handleForbiddenAccessException(HttpClientErrorException.Forbidden e){
//
//        ModelAndView modelAndView = new ModelAndView("403");
//        modelAndView.setStatus(HttpStatus.FORBIDDEN);
//        return modelAndView;
//    }
//
//    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
//    public ModelAndView handleInternalServerError(HttpServerErrorException.InternalServerError e){
//
//        ModelAndView modelAndView = new ModelAndView("500");
//        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        return modelAndView;
//    }
}
