package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Rezervacija;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuJednokratnuDTO;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuPonavljajucuDTO;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuTrajnuDTO;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.RezervacijaService;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rezervacije")
public class RezervacijaController {

    @Autowired
    private RezervacijaService rezervacijaService;

    @Autowired
    private KorisnikService korisnikService;

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
    @PostMapping("/jednokratne")
    @Secured(Roles.USER)
    public Rezervacija createRezervacijaJednokratna(
            @RequestBody DodajRezervacijuJednokratnuDTO dto, @AuthenticationPrincipal User u) {
        return rezervacijaService.createRezervacijaJednokratna(dto, korisnikService.fetchKorisnik(u.getUsername()).getId());
    }

    @PostMapping("/ponavljajuce")
    @Secured(Roles.USER)
    public List<Rezervacija> createRezervacijaPonavljajuca(
            @RequestBody DodajRezervacijuPonavljajucuDTO dto, @AuthenticationPrincipal User u) {
        return rezervacijaService.createRezervacijaPonavljajuca(dto, korisnikService.fetchKorisnik(u.getUsername()).getId());
    }

    @PostMapping("/trajna")
    @Secured(Roles.USER)
    public Rezervacija createRezervacijaTrajna(
            @RequestBody DodajRezervacijuTrajnuDTO dto, @AuthenticationPrincipal User u) {
        return rezervacijaService.createRezervacijaTrajna(dto, korisnikService.fetchKorisnik(u.getUsername()).getId());
    }

}
