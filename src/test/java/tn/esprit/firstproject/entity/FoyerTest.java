package tn.esprit.firstproject.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class FoyerTest {

    @Test
    public void testFoyerBuilder() {
        Foyer foyer = Foyer.builder()
                .idFoyer(1)
                .nomFoyer("Foyer A")
                .capaciteFoyer(100L)
                .build();

        assertThat(foyer.getIdFoyer()).isEqualTo(1);
        assertThat(foyer.getNomFoyer()).isEqualTo("Foyer A");
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(100L);
    }

    @Test
    public void testFoyerSettersAndGetters() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(2);
        foyer.setNomFoyer("Foyer B");
        foyer.setCapaciteFoyer(200L);

        assertThat(foyer.getIdFoyer()).isEqualTo(2);
        assertThat(foyer.getNomFoyer()).isEqualTo("Foyer B");
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(200L);
    }

    @Test
    public void testFoyerToString() {
        Foyer foyer = Foyer.builder()
                .idFoyer(3)
                .nomFoyer("Foyer C")
                .capaciteFoyer(300L)
                .build();

        String expectedToString = "Foyer(idFoyer=3, nomFoyer=Foyer C, capaciteFoyer=300)";
        assertThat(foyer.toString()).isEqualTo(expectedToString);
    }

}