package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Parkiraliste;

import java.util.List;

public interface ParkiralisteService {
    List<Parkiraliste> listAll();

    List<Parkiraliste> listByTvrtkaID(Long tvrtkaID);

    Parkiraliste createParkiraliste(Parkiraliste parkiraliste);

    Boolean deleteParkiraliste(Long parkiralisteID, Long tvrtkaID);

    Parkiraliste fetchParkiraliste(Long parkiralisteID);

}
