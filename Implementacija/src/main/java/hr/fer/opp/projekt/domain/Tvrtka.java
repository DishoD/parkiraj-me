package hr.fer.opp.projekt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "tvrtka")
public class Tvrtka {

    @Id
    @GeneratedValue
    @Column(name = "tvrtka_ID")
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 11)
    private String oib;

    @NotNull
    private String ime;

    @NotNull
    private String adresaSjedista;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String passwordHash;

    @OneToMany(mappedBy = "tvrtka")
    private Set<Parkiraliste> parkiralista;


    public Long getId() {
        return id;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getAdresaSjedista() {
        return adresaSjedista;
    }

    public void setAdresaSjedista(String adresaSjedista) {
        this.adresaSjedista = adresaSjedista;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Parkiraliste> getParkiralista() {
        return parkiralista;
    }

    public void setParkiralista(Set<Parkiraliste> parkiralista) {
        this.parkiralista = parkiralista;
    }
}