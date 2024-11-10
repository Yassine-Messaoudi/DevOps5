package tn.esprit.firstproject.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class ChambreTest {

    @Test
    public void testChambreBuilder() {
        Chambre chambre = Chambre.builder()
                .idChambre(1)
                .numeroChambre(101)
                .typeC(TypeChambre.Simple)
                .build();

        assertThat(chambre.getIdChambre()).isEqualTo(1);
        assertThat(chambre.getNumeroChambre()).isEqualTo(101);
        assertThat(chambre.getTypeC()).isEqualTo(TypeChambre.Simple);
    }

    @Test
    public void testChambreNoArgsConstructor() {
        Chambre chambre = new Chambre();
        assertThat(chambre).isNotNull();
    }

    @Test
    public void testChambreAllArgsConstructor() {
        Set<Reservation> reservations = new HashSet<>();
        Bloc bloc = new Bloc();
        Chambre chambre = new Chambre(1, 101, TypeChambre.Simple, reservations, bloc);

        assertThat(chambre.getIdChambre()).isEqualTo(1);
        assertThat(chambre.getNumeroChambre()).isEqualTo(101);
        assertThat(chambre.getTypeC()).isEqualTo(TypeChambre.Simple);
        assertThat(chambre.getReservations()).isEqualTo(reservations);
        assertThat(chambre.getBloc()).isEqualTo(bloc);
    }

    @Test
    public void testChambreSetters() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1);
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.Simple);

        assertThat(chambre.getIdChambre()).isEqualTo(1);
        assertThat(chambre.getNumeroChambre()).isEqualTo(101);
        assertThat(chambre.getTypeC()).isEqualTo(TypeChambre.Simple);
    }

    @Test
    public void testChambreToString() {
        Chambre chambre = Chambre.builder()
                .idChambre(1)
                .numeroChambre(101)
                .typeC(TypeChambre.Simple)
                .build();

        String chambreString = chambre.toString();
        assertThat(chambreString).doesNotContain("reservations");
        assertThat(chambreString).doesNotContain("bloc");
    }
}