package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.domain.Automobil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutomobilRepository extends JpaRepository<Automobil, Long> {
    Optional<Automobil> findByRegistracijskaOznaka(String registracijskaOznaka);
    List<Automobil> findAllByKorisnikID(Long korisnikID);
}
