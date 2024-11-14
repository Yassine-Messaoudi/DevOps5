package tn.esprit.firstproject.serviceIMP;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.firstproject.entity.Bloc;
import tn.esprit.firstproject.entity.Chambre;
import tn.esprit.firstproject.entity.Etudiant;
import tn.esprit.firstproject.entity.Reservation;
import tn.esprit.firstproject.repositories.ChambreRepository;
import tn.esprit.firstproject.repositories.EtudiantRepository;
import tn.esprit.firstproject.repositories.ReservationRepository;





public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllReservation() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.retrieveAllReservation();

        assertThat(reservations).hasSize(2);
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testAddReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = reservationService.addReservation(reservation);

        assertThat(savedReservation).isNotNull();
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(reservation);

        assertThat(updatedReservation).isNotNull();
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testRetrieveReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        Reservation retrievedReservation = reservationService.retrieveReservation(1L);

        assertThat(retrievedReservation).isNotNull();
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    public void testAjouterReservation() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setBloc(new Bloc("A"));
        Etudiant etudiant = new Etudiant();
        etudiant.setCin(12345678L);
        when(chambreRepository.findById(anyInt())).thenReturn(Optional.of(chambre));
        when(etudiantRepository.findByCin(anyLong())).thenReturn(etudiant);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        Reservation reservation = reservationService.ajouterReservation(1, 12345678L);

        assertThat(reservation).isNotNull();
        verify(chambreRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).findByCin(12345678L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testGetReservationParAnneeUniversitaireEtNomUniversite() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        when(reservationRepository.recupererParBlocEtTypeChambre(anyString(), any(LocalDate.class)))
                .thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.getReservationParAnneeUniversitaireEtNomUniversite(LocalDate.now(), "University");

        assertThat(reservations).hasSize(2);
        verify(reservationRepository, times(1)).recupererParBlocEtTypeChambre("University", LocalDate.now());
    }
}