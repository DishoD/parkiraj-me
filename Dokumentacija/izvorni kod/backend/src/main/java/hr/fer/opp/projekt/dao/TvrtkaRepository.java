package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.domain.Tvrtka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TvrtkaRepository extends JpaRepository<Tvrtka, Long> {
    int countByOib(String oib);
    Optional<Tvrtka> findByEmail(String email);
    Optional<Tvrtka> findByOib(String oib);
}
