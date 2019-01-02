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
    @Secured(Roles.ADMIN)
    public List<Automobil> listAutomobil() {
        return automobilService.listAll();
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
    public Automobil createAutomobil(@RequestBody Automobil automobil) {
        return automobilService.createAutomobil(automobil);
    }



}
