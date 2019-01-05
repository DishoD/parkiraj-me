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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parkiralista")
public class ParkiralisteController {

    @Autowired
    private ParkiralisteService parkiralisteService;

    @Autowired
    private TvrtkaService tvrtkaService;

    @GetMapping("")
    public List<Parkiraliste> listParkiralista(){
        return parkiralisteService.listAll();
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

    @GetMapping("/slobodna")
    public List<SlobodnaMjesta> getSlobodnaMjesta() {
//        Parkiraliste parkiraliste = parkiralisteService.fetchParkiraliste(parkiralisteID);
        List<Parkiraliste> parkiralista = parkiralisteService.listAll();
        List<SlobodnaMjesta> slobodnaMjesta = new ArrayList<>();
        Date now = new Date();
        for (Parkiraliste p : parkiralista) {
            SlobodnaMjesta mjesta = new SlobodnaMjesta();
            long brojZauzetih = p.getRezervacije().stream().filter((r) -> r.getVrijemePocetka().before(now) && r.getVrijemeKraja().after(now)).count();
            mjesta.setBrojSlobodnihMjesta(p.getKapacitet() - brojZauzetih);
            mjesta.setParkiralisteID(p.getId());
            slobodnaMjesta.add(mjesta);
        }
        return slobodnaMjesta;
    }

    private static class SlobodnaMjesta {
        private Long parkiralisteID;
        private long brojSlobodnihMjesta;

        public Long getParkiralisteID() {
            return parkiralisteID;
        }

        public void setParkiralisteID(Long parkiralisteID) {
            this.parkiralisteID = parkiralisteID;
        }

        public long getBrojSlobodnihMjesta() {
            return brojSlobodnihMjesta;
        }

        public void setBrojSlobodnihMjesta(long brojSlobodnihMjesta) {
            this.brojSlobodnihMjesta = brojSlobodnihMjesta;
        }
    }
}