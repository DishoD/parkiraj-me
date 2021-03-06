package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Automobil;
import hr.fer.opp.projekt.service.AutomobilService;
import hr.fer.opp.projekt.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automobili")
public class AutomobilController {

    @Autowired
    private AutomobilService automobilService;

    @Autowired
    private KorisnikService korisnikService;


    @GetMapping("")
    @Secured(Roles.USER)
    public List<Automobil> listAutomobil(@AuthenticationPrincipal User u) {
        return automobilService.listByUserID(korisnikService.fetchKorisnik(u.getUsername()).getId());
    }

    @GetMapping("/{korisnikID}")
    @Secured({Roles.USER, Roles.ADMIN})
    public List<Automobil> getAutomobiliZaKorisnika(@PathVariable("korisnikID") Long korisnikID, @AuthenticationPrincipal User u) {
        if (u.getAuthorities().containsAll(Roles.userAuthority)) {
            Assert.isTrue(korisnikID.equals(korisnikService.fetchKorisnik(u.getUsername()).getId()), "Nemate pravo na ove automobile.");
        }
        return automobilService.listByUserID(korisnikID);
    }

    @PostMapping("")
    @Secured(Roles.USER)
    public Automobil createAutomobil(@RequestBody Automobil automobil,
                                     @AuthenticationPrincipal User u)  {
        automobil.setKorisnikID(korisnikService.fetchKorisnik(u.getUsername()).getId());
        return automobilService.createAutomobil(automobil);
    }

    @DeleteMapping("/{registracija}")
    @Secured(Roles.USER)
    public Boolean deleteAutomobil(@PathVariable("registracija") String registracija, @AuthenticationPrincipal User u) {
        Assert.isTrue(u.getUsername().equals(korisnikService.fetchKorisnik(u.getUsername()).getEmail()), "Nemate pravo brisati ovaj automobil");
        return automobilService.deleteAutomobil(registracija);
    }
}
