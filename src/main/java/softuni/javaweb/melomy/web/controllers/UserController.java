package softuni.javaweb.melomy.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.javaweb.melomy.model.binding.UserLoginBindingModel;
import softuni.javaweb.melomy.model.binding.UserSignUpBindingModel;
import softuni.javaweb.melomy.model.service.UserServiceModel;
import softuni.javaweb.melomy.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpConfirm(@Valid UserSignUpBindingModel userModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !userModel.getPassword().equals(userModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel",
                    bindingResult);

            return "redirect:/users/sign-up";
        }

        UserServiceModel userServiceModel = modelMapper.map(userModel, UserServiceModel.class);
        userService.registerUser(userServiceModel);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(Model model){

        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName, RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("badCredentials", true);
        redirectAttributes.addFlashAttribute("username", userName);

        return "redirect:/users/login";
    }

    @ModelAttribute("userModel")
    public UserSignUpBindingModel userModel(){
        return new UserSignUpBindingModel();
    }



}
