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
import java.util.stream.Collectors;

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
    public Parkiraliste createParkiraliste(@RequestBody Parkiraliste parkiraliste,
                                           @AuthenticationPrincipal User u){
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
        List<Parkiraliste> parkiralista = parkiralisteService.listAll();
        List<SlobodnaMjesta> slobodnaMjesta = new ArrayList<>();
        Date now = new Date();
        for (Parkiraliste p : parkiralista) {
            SlobodnaMjesta mjesta = new SlobodnaMjesta();
            long brojZauzetih = p.getRezervacije().stream().filter((r) -> r.getVrijemePocetka().before(now) && r.getVrijemeKraja().after(now)).count();
            mjesta.setBrojSlobodnihMjesta(p.getKapacitet() - brojZauzetih);
            mjesta.setParkiralisteID(p.getId());
            mjesta.setKapacitet(p.getKapacitet());
            slobodnaMjesta.add(mjesta);
        }
        return slobodnaMjesta;
    }

    private static class SlobodnaMjesta {
        private Long parkiralisteID;
        private long brojSlobodnihMjesta;
        private long kapacitet;

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

        public long getKapacitet() {
            return kapacitet;
        }

        public void setKapacitet(long kapacitet) {
            this.kapacitet = kapacitet;
        }
    }
}