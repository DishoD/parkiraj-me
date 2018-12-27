package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {

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
        Korisnik korisnik = korisnikService.fetchKorisnik(id);
        if (korisnik.getEmail().equals(u.getUsername()) || "admin".equals(u.getUsername())) {
            return korisnik;
        } else {
            throw new RequestDeniedException("You do not have permission to view this user.");
        }
    }

    @PostMapping("")
    public Korisnik createKorisnik(@RequestBody Korisnik korisnik) {
        return korisnikService.createKorisnik(korisnik);
    }
}
