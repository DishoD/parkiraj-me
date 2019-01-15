package hr.fer.opp.projekt.service;


import hr.fer.opp.projekt.domain.Tvrtka;

import java.util.List;

public interface TvrtkaService {
    List<Tvrtka> listAll();
    Tvrtka createTvrtka(Tvrtka tvrtka);
    Tvrtka fetchTvrtka(Long id);
    Tvrtka fetchTvrtka(String email);
    boolean containsTvrtka(String email);
    Boolean deleteTvrtka(String email);
}
