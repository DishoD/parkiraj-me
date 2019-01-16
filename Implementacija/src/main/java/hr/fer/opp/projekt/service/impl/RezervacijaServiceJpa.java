package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.AutomobilRepository;
import hr.fer.opp.projekt.dao.KorisnikRepository;
import hr.fer.opp.projekt.dao.ParkiralisteRepository;
import hr.fer.opp.projekt.dao.RezervacijaRepository;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.domain.Parkiraliste;
import hr.fer.opp.projekt.domain.Rezervacija;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuJednokratnuDTO;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuPonavljajucuDTO;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuTrajnuDTO;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.ParkiralisteService;
import hr.fer.opp.projekt.service.RezervacijaService;
import hr.fer.opp.projekt.service.AutomobilService;
import hr.fer.opp.projekt.service.exceptions.EntityMissingException;
import hr.fer.opp.projekt.service.exceptions.RequestDeniedException;
import net.bytebuddy.description.field.FieldDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RezervacijaServiceJpa implements RezervacijaService {

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ParkiralisteRepository parkiralisteRepository;

    @Autowired
    private ParkiralisteService parkiralisteService;

    @Override
    public List<Rezervacija> listAll() {
        return rezervacijaRepository.findAll();
    }

    @Override
    public Rezervacija fetch(Long rezervacijaId) {
        return rezervacijaRepository.findById(rezervacijaId).orElseThrow(
                () -> new EntityMissingException(Rezervacija.class, rezervacijaId)
        );
    }

    @Override
    public Rezervacija createRezervacijaJednokratna(DodajRezervacijuJednokratnuDTO dto, Long korisnikID) {
        Long parkingID = dto.getParkingID();
        Date vrijemePocetka = new Date(dto.getVrijemePocetka());
        Long trajanje = dto.getTrajanje();

        Double hours = milisecondsToHours(vrijemePocetka.getTime() - System.currentTimeMillis());
        Assert.isTrue(hours > 6, "Jednokratno možete rezervirati minimalno 6 sati unaprijed.");

        Assert.notNull(parkingID, "Potrebno je predati ID parkiralista.");
        Assert.notNull(vrijemePocetka, "Potrebno je predati vrijeme početka.");
        Assert.notNull(trajanje, "Potrebno je predati trajanje rezervacije.");

        Date vrijemeKraja = new Date(dto.getVrijemePocetka() + hoursToMilliseconds(trajanje));

        Rezervacija rezervacija = new Rezervacija(korisnikID, vrijemePocetka, vrijemeKraja, parkingID, false);

        Korisnik korisnik = korisnikService.fetchKorisnik(korisnikID);
        korisnik.getRezervacije().add(rezervacija);

        Parkiraliste parkiraliste = parkiralisteService.fetchParkiraliste(parkingID);
        parkiraliste.getRezervacije().add(rezervacija);

        rezervacijaRepository.save(rezervacija);
        korisnikRepository.save(korisnik);
        parkiralisteRepository.save(parkiraliste);
        return rezervacija;
    }

    @Override
    public List<Rezervacija> createRezervacijaPonavljajuca(DodajRezervacijuPonavljajucuDTO dto, Long korisnikID) {
        Long parkingID = dto.getParkingID();
        Integer[] dani = dto.getDani();
        String[] vrijemePocetka = dto.getVrijemePocetka().split(":");
        int satiPocetka = Integer.valueOf(vrijemePocetka[0]);
        int minutePocetka = Integer.valueOf(vrijemePocetka[1]);
        Long trajanje = dto.getTrajanje();

        List<Rezervacija> rezervacije = new ArrayList<>();
        Korisnik korisnik = korisnikService.fetchKorisnik(korisnikID);
        Parkiraliste parkiraliste = parkiralisteService.fetchParkiraliste(parkingID);

        Date danas = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(danas);
        int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);

        //za svaki zadani dan
        for (int i = 0; i < dani.length; i++) {

            //odredivanje pocetnog dana
            int daysFromToday = 0;
            if (dani[i] < dayOfTheWeek) {
                daysFromToday = dayOfTheWeek - dani[i] - 1;
            } else if (dani[i] > dayOfTheWeek) {
                daysFromToday = dani[i] - dayOfTheWeek;
            } else {
                //idu sati pocetka -1 jer je vrijeme aplikacije namjesteno na UTC
                if (danas.before(createDate(danas, satiPocetka - 1, minutePocetka))) {
                    daysFromToday = 0;
                } else {
                    daysFromToday = 7;
                }
            }

            //za sva 4 pojavljanja u buducnosti
            for (int j = 0; j < 4; j++) {
                Date newDate = convertToDate(LocalDate.now().plusDays(daysFromToday + 7 * j));

                Date startTime = createDate(newDate, satiPocetka, minutePocetka);
                Date endTime = createDate(newDate, satiPocetka + trajanje.intValue(), minutePocetka);

                Rezervacija rezervacija = new Rezervacija(korisnikID, startTime, endTime, parkingID, false);

                rezervacije.add(rezervacija);
                rezervacijaRepository.save(rezervacija);

                korisnik.getRezervacije().add(rezervacija);
                korisnikRepository.save(korisnik);

                parkiraliste.getRezervacije().add(rezervacija);
                parkiralisteRepository.save(parkiraliste);
            }
        }
        return rezervacije;
    }

    @Override
    public Rezervacija createRezervacijaTrajna(DodajRezervacijuTrajnuDTO dto, Long korisnikID) {
        Long parkingID = dto.getParkingID();
        Assert.notNull(parkingID, "Potrebno je predati ID parkirališta.");

        List<Rezervacija> trajne = korisnikService.fetchKorisnik(korisnikID).getRezervacije()
                .stream()
                .filter((r) -> r.getTrajna())
                .filter((r) -> r.getParkiralisteID().equals(parkingID))
                .collect(Collectors.toList());

        if (trajne != null && trajne.size() != 0)
            throw new RequestDeniedException("Već imate trajnu rezervaciju na ovom parkiralištu.");

        Date vrijemePocetka = new Date();
        Date vrijemeKraja = new Date(vrijemePocetka.getTime() + hoursToMilliseconds((long) 30 * 24));

        Korisnik korisnik = korisnikService.fetchKorisnik(korisnikID);
        Parkiraliste parkiraliste = parkiralisteService.fetchParkiraliste(parkingID);

        Rezervacija rezervacija = new Rezervacija(korisnikID, vrijemePocetka, vrijemeKraja, parkingID, true);
        rezervacijaRepository.save(rezervacija);

        korisnik.getRezervacije().add(rezervacija);
        korisnikRepository.save(korisnik);

        parkiraliste.getRezervacije().add(rezervacija);
        parkiralisteRepository.save(parkiraliste);

        return rezervacija;
    }

    @Override
    public Rezervacija deleteRezervacija(Long id) {
        Rezervacija rezervacija = fetch(id);
        Korisnik korisnik = korisnikService.fetchKorisnik(rezervacija.getKorisnikID());
        Parkiraliste parkiraliste = parkiralisteService.fetchParkiraliste(rezervacija.getParkiralisteID());

        korisnik.getRezervacije().remove(rezervacija);
        parkiraliste.getRezervacije().remove(rezervacija);

        korisnikRepository.save(korisnik);
        parkiralisteRepository.save(parkiraliste);
        rezervacijaRepository.delete(rezervacija);
        return rezervacija;
    }

    private Long hoursToMilliseconds(Long hours) {
        return hours * 60 * 60 * 1000;
    }

    private Double milisecondsToHours(Long miliseconds) {
        return miliseconds * 1.0 / (1000 * 60 * 60);
    }

    private Date convertToDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    /**
     * Kreira Date objekt iz danog Date objekta koji bi trebao ima zeljeni datum i 2 Integera koji predstavljaju zeljene sate i minute.
     *
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    private Date createDate(Date date, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
        c.set(Calendar.MINUTE, Integer.valueOf(minute));
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}