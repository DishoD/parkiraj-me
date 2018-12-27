package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.domain.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    int countByOib(String oib);
    Optional<Korisnik> findByEmail(String email);
    Optional<Korisnik> findByOib(String oib);
}
