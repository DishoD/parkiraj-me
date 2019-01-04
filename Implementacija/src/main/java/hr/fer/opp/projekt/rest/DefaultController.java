package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.dao.AdministratorRepository;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.TvrtkaService;
import hr.fer.opp.projekt.service.Util;
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

    @Autowired
    private TvrtkaService tvrtkaService;

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");

        return mv;
    }

    @PostMapping("/login")
    public LoginResponseEntity login(@AuthenticationPrincipal User u) {
        LoginResponseEntity entity = new LoginResponseEntity();
        entity.setUsername(u.getUsername());
        if (u.getAuthorities().containsAll(Roles.userAuthority)) {
            entity.setTip(0);
            entity.setIme(korisnikService.fetchKorisnik(u.getUsername()).getIme());
        } else if (u.getAuthorities().containsAll(Roles.companyAuthority)) {
            entity.setTip(1);
            entity.setIme(tvrtkaService.fetchTvrtka(u.getUsername()).getIme());
        } else if (u.getAuthorities().containsAll(Roles.adminAuthority)){
            entity.setTip(2);
            entity.setIme("Administrator");
        }
        return entity;
    }

    private static class LoginResponseEntity {
//        private Long id;
        private String username;
        private int tip;
        private String ime;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getTip() {
            return tip;
        }

        public void setTip(int tip) {
            this.tip = tip;
        }

        public String getIme() {
            return ime;
        }

        public void setIme(String ime) {
            this.ime = ime;
        }
    }
}
