package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.domain.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {
    Optional<Rezervacija> findById(Long id);
    List<Rezervacija> findAllByParkiralisteID(Long parkiralisteID);
}
