package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Automobil;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.AutomobilService;
import hr.fer.opp.projekt.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("")
    @Secured(Roles.USER)
    public Automobil createAutomobil(@RequestBody Automobil automobil) {
        return automobilService.createAutomobil(automobil);
    }



}
