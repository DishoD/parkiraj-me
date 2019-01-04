package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Tvrtka;
import hr.fer.opp.projekt.service.exceptions.RequestDeniedException;
import hr.fer.opp.projekt.service.TvrtkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tvrtke")
public class TvrtkaController {

    @Autowired
    private TvrtkaService tvrtkaService;

    @GetMapping("")
    @Secured(Roles.ADMIN)
    public List<Tvrtka> listTvrtke(){
        return tvrtkaService.listAll();
    }

    @GetMapping("/{id}")
    @Secured({Roles.USER, Roles.ADMIN})
    public Tvrtka getTvrtka(@PathVariable("id") Long id, @AuthenticationPrincipal User u){
        Tvrtka tvrtka = tvrtkaService.fetchTvrtka(id);
        if (tvrtka.getEmail().equals(u.getUsername()) || "admin".equals(u.getUsername())) {
            return tvrtka;
        } else {
            throw new RequestDeniedException("You do not have permission to view this user.");
        }

    }

    @PostMapping("")
    public Tvrtka createTvrtka(@RequestBody Tvrtka tvrtka){
        return tvrtkaService.createTvrtka(tvrtka);
    }

    @DeleteMapping("{email}")
    @Secured(Roles.ADMIN)
    public void deleteTvrtka(@PathVariable("email") String email) {
        tvrtkaService.deleteTvrtka(email);
    }
}
