package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {

    private static class CurrentUser {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("")
    @Secured(Roles.ADMIN)
    public List<Korisnik> listKorisnike() {
        return korisnikService.listAll();
    }

    @GetMapping("/{id}")
    @Secured({Roles.USER, Roles.ADMIN})
    public Korisnik getKorisnik(@PathVariable("id") Long id, @AuthenticationPrincipal User u) {
        if (u.getAuthorities().containsAll(Roles.userAuthority)) {
            Assert.isTrue(id.equals(korisnikService.fetchKorisnik(u.getUsername()).getId()), "Nemate pravo na ovog korisnika.");
        }
        return korisnikService.fetchKorisnik(id);
    }

    @PostMapping("")
    public Korisnik createKorisnik(@RequestBody Korisnik korisnik) {
        return korisnikService.createKorisnik(korisnik);
    }

    @DeleteMapping("/{email}")
    @Secured(Roles.ADMIN)
    public Boolean deleteKorisnik(@PathVariable String email) {
        return korisnikService.deleteKorisnik(korisnikService.fetchKorisnik(email));
    }

    @GetMapping("/current")
    public CurrentUser getCurrent(@AuthenticationPrincipal User u) {
        CurrentUser user = new CurrentUser();
        if (u != null) {
            user.setEmail(u.getUsername());
        }
        return user;
    }
}
