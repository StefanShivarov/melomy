package softuni.javaweb.melomy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/sign-up")
    public String signUp(Model model){
        return "sign-up";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
