package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Rezervacija;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuJednokratnuDTO;
import hr.fer.opp.projekt.service.RezervacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rezervacije")
public class RezervacijaController {

    @Autowired
    private RezervacijaService rezervacijaService;


    @GetMapping("")
    @Secured(Roles.ADMIN)
    public List<Rezervacija> listRezervacija() {
        return rezervacijaService.listAll();
    }

    /*
    jednokratna - {autoID, parkingID, vrijemePocetka, trajanje}
    trajna - {vrijemePocetka}
    ponavljajuca - {nista? vrijeme pocetka je od trenutka kad dode zahtjev}
     */
    @PostMapping("/jednokratne/{id}")
    @Secured(Roles.USER)
    public Rezervacija createRezervacijaJednokratna(
            @RequestBody DodajRezervacijuJednokratnuDTO dto, @PathVariable Long id) {
        return rezervacijaService.createRezervacijaJednokratna(dto, id);
    }

    /*
    @PostMapping("/ponavljajuce/{id}")
    @Secured(Roles.USER)
    public List<Rezervacija> createRezervacijaPonavljajuca(
            @RequestBody DodajRezervacijuPonavljajucuDTO dto, @PathVariable Long id) {
        return rezervacijaService.createRezervacijaPonavljajuca(dto);
    }

    @PostMapping("/trajna")
    @Secured(Roles.USER)
    public List<Rezervacija> createRezervacijaTrajna(
            @RequestBody DodajRezervacijuTrajnu dto, @PathVariable Long id) {
        return rezervacijaService.createRezervacijaTrajna(dto);
    }
    */
}
