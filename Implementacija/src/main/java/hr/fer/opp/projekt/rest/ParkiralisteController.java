package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Parkiraliste;
import hr.fer.opp.projekt.service.ParkiralisteService;
import hr.fer.opp.projekt.service.TvrtkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parkiralista")
public class ParkiralisteController {

    @Autowired
    private ParkiralisteService parkiralisteService;

    @Autowired
    private TvrtkaService tvrtkaService;

    @GetMapping
    @Secured(Roles.ADMIN)
    public List<Parkiraliste> listParkiralista(){
        return parkiralisteService.listAll();
    }

    @GetMapping("/{tvrtkaID}")
    @Secured({Roles.COMPANY, Roles.ADMIN})
    public List<Parkiraliste> getParkiralisteZaTvrtku(@PathVariable("tvrtkaID") Long tvrtkaID, @AuthenticationPrincipal User u){
        if (u.getAuthorities().containsAll(Roles.userAuthority)) {
            Assert.isTrue(tvrtkaID.equals(tvrtkaService.fetchTvrtka(u.getUsername()).getId()));
        }
        return parkiralisteService.listByTvrtkaID(tvrtkaID);
    }

    @PostMapping("")
    @Secured(Roles.COMPANY)
    public Parkiraliste createParkiraliste(@RequestParam("ime") String ime,
                                           @RequestParam("lokacija") Double[] lokacija,
                                           @RequestParam("kapacitet") Integer kapacitet,
                                           @RequestParam("cijena") Integer cijena,
                                           @AuthenticationPrincipal User u){
        Parkiraliste parkiraliste = new Parkiraliste();
        parkiraliste.setCijena(cijena);
        parkiraliste.setIme(ime);
        parkiraliste.setKapacitet(kapacitet);
        parkiraliste.setLokacija(lokacija);
        parkiraliste.setTvrtkaID(tvrtkaService.fetchTvrtka(u.getUsername()).getId());
        return parkiralisteService.createParkiraliste(parkiraliste);
    }

    @DeleteMapping("/{parkiralisteID}")
    @Secured(Roles.COMPANY)
    public Boolean deleteParkiraliste(@PathVariable("parkiralisteID") Long parkiralisteID, @AuthenticationPrincipal User u){
        return parkiralisteService.deleteParkiraliste(parkiralisteID, tvrtkaService.fetchTvrtka(u.getUsername()).getId());
    }
}