package tn.esprit.firstproject.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.firstproject.entity.Reservation;
import tn.esprit.firstproject.repositories.ReservationRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void ReservationRepository_SaveAl_ReturnSavedReservation() {
        LocalDate anneeUniversitaire = LocalDate.of(2000, 12, 23);
        boolean estValide = true;

        // Arrange
        Reservation reservation = Reservation.builder()
                .anneeUniversitaire(anneeUniversitaire)
                .estValide(estValide)
                .build();

        // Act
        Reservation savedReservation = reservationRepository.save(reservation);

        // Assert
        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getIdReservationn()).isGreaterThan(0);
    }
}
