package hr.fer.opp.projekt.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
public class DefaultController {
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");

        return mv;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return "got username: " + username + " and password " + password;
    }
}
