package net.company.orders.controller;

import net.company.orders.model.User;
import net.company.orders.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private UserDetailsServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "index";
    }

    @GetMapping("/registration")
    public String getRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "auth/register";
    }
    @PostMapping("/registration")
    public String registerUserAccount(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userService.saveUser(user))
        {
            UserDetails securityUser = userService.loadUserByUsername(user.getLogin());
            Authentication auth =
                    new UsernamePasswordAuthenticationToken(user, null, securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";
        }
        else
            return "error";


    }
    @GetMapping("/editUser")
    public String editUserForm(Model model){
        User currUser = userService.getCurrUser();
        model.addAttribute("user", currUser);
        return "auth/editUser";
    }
    @PostMapping("/editUser")
    public String editUser(User user, Model model){
        User currUser = userService.getCurrUser();
        user.setId(currUser.getId());
        if(user.getPassword() == "")
        {
            user.setPassword(currUser.getPassword());
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.saveUser(user);
        model.addAttribute("info", "Пользователь успешно изменен");
        return "index";
    }
}
