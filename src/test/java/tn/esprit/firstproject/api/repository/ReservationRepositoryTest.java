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
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void ReservationRepository_Save_ReturnSavedReservation() {
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

    @Test

    public void ReservationRepository_SaveAll_ReturnSavedReservations() {
        // Arrange
        LocalDate anneeUniversitaire1 = LocalDate.of(2001, 9, 1);
        LocalDate anneeUniversitaire2 = LocalDate.of(2002, 9, 1);

        Reservation reservation1 = Reservation.builder()
                .anneeUniversitaire(anneeUniversitaire1)
                .estValide(true)
                .build();

        Reservation reservation2 = Reservation.builder()
                .anneeUniversitaire(anneeUniversitaire2)
                .estValide(false)
                .build();

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        // Act
        List<Reservation> savedReservations = reservationRepository.saveAll(reservations);

        // Assert
        assertThat(savedReservations).isNotNull();
        assertThat(savedReservations.size()).isEqualTo(2);
        assertThat(savedReservations.get(0).getIdReservationn()).isGreaterThan(0);
        assertThat(savedReservations.get(1).getIdReservationn()).isGreaterThan(0);
    }


    @Test
    public void ReservationRepository_Update_ReturnUpdatedReservation() {
        // Arrange
        LocalDate initialAnneeUniversitaire = LocalDate.of(2000, 12, 23);
        Reservation reservation = Reservation.builder()
                .anneeUniversitaire(initialAnneeUniversitaire)
                .estValide(true)
                .build();

        // Save initial reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Update reservation details
        LocalDate updatedAnneeUniversitaire = LocalDate.of(2001, 12, 23);
        savedReservation.setAnneeUniversitaire(updatedAnneeUniversitaire);
        savedReservation.setEstValide(false);

        // Act
        Reservation updatedReservation = reservationRepository.save(savedReservation);

        // Assert
        assertThat(updatedReservation).isNotNull();
        assertThat(updatedReservation.getIdReservationn()).isEqualTo(savedReservation.getIdReservationn());
        assertThat(updatedReservation.getAnneeUniversitaire()).isEqualTo(updatedAnneeUniversitaire);
        assertThat(updatedReservation.isEstValide()).isFalse();
    }

    @Test
    public void ReservationRepository_RetrieveReservation_ReturnsReservation() {
        // Arrange
        LocalDate anneeUniversitaire = LocalDate.of(2000, 12, 23);
        Reservation reservation = Reservation.builder()
                .anneeUniversitaire(anneeUniversitaire)
                .estValide(true)
                .build();

        // Save the reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Act
        Reservation retrievedReservation = reservationRepository.findById(savedReservation.getIdReservationn()).orElse(null);

        // Assert
        assertThat(retrievedReservation).isNotNull();
        assertThat(retrievedReservation.getIdReservationn()).isEqualTo(savedReservation.getIdReservationn());
        assertThat(retrievedReservation.getAnneeUniversitaire()).isEqualTo(anneeUniversitaire);
        assertThat(retrievedReservation.isEstValide()).isTrue();
    }



    @Test
    public void testGetReservationParAnneeUniversitaireEtNomUniversite() {
        // Arrange
        String nomUniversite = "Esprit University";
        LocalDate anneeUniversitaire = LocalDate.of(2023, 9, 1);

        Reservation reservation1 = Reservation.builder()
                .anneeUniversitaire(anneeUniversitaire)
                .estValide(true)
                .build();

        Reservation reservation2 = Reservation.builder()
                .anneeUniversitaire(anneeUniversitaire)
                .estValide(false)
                .build();

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        // Act
        List<Reservation> foundReservations = reservationRepository.recupererParBlocEtTypeChambre(nomUniversite, anneeUniversitaire);

        // Assert
        assertThat(foundReservations).isNotNull();
        assertThat(foundReservations.size()).isGreaterThanOrEqualTo(2);
        foundReservations.forEach(reservation -> {
            assertThat(reservation.getAnneeUniversitaire().getYear()).isEqualTo(anneeUniversitaire.getYear());
            // Add any additional assertions based on your requirements
        });
    }

    @Test
    public void testAjouterReservation() {
        // Créer une nouvelle réservation
        Reservation reservation = new Reservation();

        // Définir l'année universitaire comme un LocalDate (exemple : 1er septembre 2023)
        reservation.setAnneeUniversitaire(LocalDate.of(2023, 9, 1));
        reservation.setEstValide(true);

        // Ajouter la réservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Vérifier que l’ID a été généré et que la réservation est enregistrée
        assertThat(savedReservation.getIdReservationn()).isNotNull();
        assertThat(reservationRepository.findById(savedReservation.getIdReservationn())).isPresent();

        // Vérifier que la date a été correctement enregistrée
        assertThat(savedReservation.getAnneeUniversitaire()).isEqualTo(reservation.getAnneeUniversitaire());
    }
}
