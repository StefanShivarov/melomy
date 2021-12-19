package softuni.javaweb.melomy.web.exceptions;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GlobalExceptionHandler implements ErrorController {

    private static final String NOT_FOUND = "We could not find what you're looking for!";
    private static final String DEFAULT_MESSAGE = "Ooops. Something went wrong";

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        String errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();
        if(errorMessage.equals("")){
            errorMessage = DEFAULT_MESSAGE;
        }

        int status = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        model.addAttribute("status", status);
        if (status == HttpStatus.NOT_FOUND.value()) {

            model.addAttribute("errorMessage", NOT_FOUND);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "error";
    }
}
