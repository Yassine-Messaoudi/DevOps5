package tn.esprit.firstproject.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class UniversiteTest {

    @Test
    public void testUniversiteBuilder() {
        Universite universite = Universite.builder()
                .idUniversite(1)
                .nomUniversite("ESPRIT")
                .adresse("Tunis")
                .build();

        assertThat(universite.getIdUniversite()).isEqualTo(1);
        assertThat(universite.getNomUniversite()).isEqualTo("ESPRIT");
        assertThat(universite.getAdresse()).isEqualTo("Tunis");
    }

    @Test
    public void testUniversiteSettersAndGetters() {
        Universite universite = new Universite();
        universite.setIdUniversite(2);
        universite.setNomUniversite("INSAT");
        universite.setAdresse("Ariana");

        assertThat(universite.getIdUniversite()).isEqualTo(2);
        assertThat(universite.getNomUniversite()).isEqualTo("INSAT");
        assertThat(universite.getAdresse()).isEqualTo("Ariana");
    }

    @Test
    public void testUniversiteToString() {
        Universite universite = Universite.builder()
                .idUniversite(3)
                .nomUniversite("IHEC")
                .adresse("Carthage")
                .build();

        String expectedToString = "Universite(idUniversite=3, nomUniversite=IHEC, adresse=Carthage)";
        assertThat(universite.toString()).isEqualTo(expectedToString);
    }

    @Test
    public void testUniversiteNoArgsConstructor() {
        Universite universite = new Universite();
        assertThat(universite).isNotNull();
    }

    @Test
    public void testUniversiteAllArgsConstructor() {
        Universite universite = new Universite(4, "ENIT", "Tunis", null);
        assertThat(universite.getIdUniversite()).isEqualTo(4);
        assertThat(universite.getNomUniversite()).isEqualTo("ENIT");
        assertThat(universite.getAdresse()).isEqualTo("Tunis");
    }
}