package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class DefaultController {

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");

        return mv;
    }

    @PostMapping("/login")
    public Korisnik login(@RequestParam String username) {
        return korisnikService.fetchKorisnik(username);
    }
}
