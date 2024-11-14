package tn.esprit.firstproject.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;



public class EtudiantTest {

    @Test
    public void testEtudiantBuilder() {
        Date dateNaissance = new Date();
        Etudiant etudiant = Etudiant.builder()
                .idEtudiant(1)
                .nomEt("John")
                .PrenomEt("Doe")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(dateNaissance)
                .build();

        assertThat(etudiant.getIdEtudiant()).isEqualTo(1);
        assertThat(etudiant.getNomEt()).isEqualTo("John");
        assertThat(etudiant.getPrenomEt()).isEqualTo("Doe");
        assertThat(etudiant.getCin()).isEqualTo(12345678L);
        assertThat(etudiant.getEcole()).isEqualTo("ESPRIT");
        assertThat(etudiant.getDateNaissance()).isEqualTo(dateNaissance);
    }

    @Test
    public void testEtudiantSettersAndGetters() {
        Etudiant etudiant = new Etudiant();
        Date dateNaissance = new Date();

        etudiant.setIdEtudiant(1);
        etudiant.setNomEt("John");
        etudiant.setPrenomEt("Doe");
        etudiant.setCin(12345678L);
        etudiant.setEcole("ESPRIT");
        etudiant.setDateNaissance(dateNaissance);

        assertThat(etudiant.getIdEtudiant()).isEqualTo(1);
        assertThat(etudiant.getNomEt()).isEqualTo("John");
        assertThat(etudiant.getPrenomEt()).isEqualTo("Doe");
        assertThat(etudiant.getCin()).isEqualTo(12345678L);
        assertThat(etudiant.getEcole()).isEqualTo("ESPRIT");
        assertThat(etudiant.getDateNaissance()).isEqualTo(dateNaissance);
    }

    @Test
    public void testEtudiantNoArgsConstructor() {
        Etudiant etudiant = new Etudiant();
        assertThat(etudiant).isNotNull();
    }

    @Test
    public void testEtudiantAllArgsConstructor() {
        Date dateNaissance = new Date();
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 12345678L, "ESPRIT", dateNaissance, new ArrayList<>());

        assertThat(etudiant.getIdEtudiant()).isEqualTo(1);
        assertThat(etudiant.getNomEt()).isEqualTo("John");
        assertThat(etudiant.getPrenomEt()).isEqualTo("Doe");
        assertThat(etudiant.getCin()).isEqualTo(12345678L);
        assertThat(etudiant.getEcole()).isEqualTo("ESPRIT");
        assertThat(etudiant.getDateNaissance()).isEqualTo(dateNaissance);
    }
}