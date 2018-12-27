package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Automobil;
import hr.fer.opp.projekt.service.AutomobilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class AutomobilController {

    @Autowired
    private AutomobilService automobilService;


    @GetMapping("")
    public List<Automobil> listAutomobil() { return automobilService.listAll(); }

    @GetMapping("")
    @Secured("ROLE_USER")
    public Automobil createAutomobil(@RequestBody Automobil automobil) { return automobilService.createAutomobil(automobil); }



}
