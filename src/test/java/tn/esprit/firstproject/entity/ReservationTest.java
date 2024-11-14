package tn.esprit.firstproject.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        reservation = new Reservation();
    }

    @Test
    public void testReservationDefaultValues() {
        assertThat(reservation.getIdReservationn()).isNull();
        assertThat(reservation.getAnneeUniversitaire()).isNull();
        assertThat(reservation.isEstValide()).isFalse();
        assertThat(reservation.getEtudiants()).isEmpty();
    }

    @Test
    public void testReservationSettersAndGetters() {
        Long id = 1L;
        LocalDate date = LocalDate.of(2023, 1, 1);
        boolean estValide = true;
        List<Etudiant> etudiants = new ArrayList<>();

        reservation.setIdReservationn(id);
        reservation.setAnneeUniversitaire(date);
        reservation.setEstValide(estValide);
        reservation.setEtudiants(etudiants);

        assertThat(reservation.getIdReservationn()).isEqualTo(id);
        assertThat(reservation.getAnneeUniversitaire()).isEqualTo(date);
        assertThat(reservation.isEstValide()).isEqualTo(estValide);
        assertThat(reservation.getEtudiants()).isEqualTo(etudiants);
    }

    @Test
    public void testReservationBuilder() {
        Long id = 1L;
        LocalDate date = LocalDate.of(2023, 1, 1);
        boolean estValide = true;
        List<Etudiant> etudiants = new ArrayList<>();

        Reservation reservation = Reservation.builder()
                .idReservationn(id)
                .anneeUniversitaire(date)
                .estValide(estValide)
                .etudiants(etudiants)
                .build();

        assertThat(reservation.getIdReservationn()).isEqualTo(id);
        assertThat(reservation.getAnneeUniversitaire()).isEqualTo(date);
        assertThat(reservation.isEstValide()).isEqualTo(estValide);
        assertThat(reservation.getEtudiants()).isEqualTo(etudiants);
    }
}