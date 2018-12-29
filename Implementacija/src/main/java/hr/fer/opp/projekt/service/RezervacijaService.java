package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Rezervacija;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuJednokratnuDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RezervacijaService {

    List<Rezervacija> listAll();

    Rezervacija fetch(Long rezervacijaID);

    Rezervacija createRezervacijaJednokratna(DodajRezervacijuJednokratnuDTO rezervacijaDTO, Long korisnikID);

    Rezervacija deleteRezervacija(Long rezervacijaID);

}
