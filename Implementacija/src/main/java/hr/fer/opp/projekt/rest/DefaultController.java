package hr.fer.opp.projekt.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DefaultController {
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @GetMapping("/login")
    public ModelAndView loginPage() {
        System.out.println("mapping is used");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");

        return mv;
    }

    @GetMapping("")
    public String homePage() {
        System.out.println("is also used");
        return "success";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        System.out.println("got username: " + username + " and password " + password);
        return "got username: " + username + " and password " + password;
    }
}
