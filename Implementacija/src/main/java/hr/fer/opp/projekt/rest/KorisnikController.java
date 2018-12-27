package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;


    @GetMapping("")
    public List<Korisnik> listKorisnike() {
        return korisnikService.listAll();
    }

    @GetMapping("/{id}")
    public Korisnik getKorisnik(@PathVariable("id") Long id) {
        return korisnikService.fetchKorisnik(id);
    }

    @PostMapping("")
    public Korisnik createKorisnik(@RequestBody Korisnik korisnik) {
        return korisnikService.createKorisnik(korisnik);
    }
}
