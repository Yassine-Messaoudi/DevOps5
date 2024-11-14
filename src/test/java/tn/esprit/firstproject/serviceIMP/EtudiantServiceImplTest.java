package tn.esprit.firstproject.serviceIMP;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.firstproject.entity.Etudiant;
import tn.esprit.firstproject.repositories.EtudiantRepository;




public class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllEtudiants() {
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);

        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertThat(result).isEqualTo(etudiants);
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    public void testAddEtudiants() {
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);

        when(etudiantRepository.saveAll(any(List.class))).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.addEtudiants(etudiants);

        assertThat(result).isEqualTo(etudiants);
        verify(etudiantRepository, times(1)).saveAll(etudiants);
    }

    @Test
    public void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant();

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant result = etudiantService.updateEtudiant(etudiant);

        assertThat(result).isEqualTo(etudiant);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    public void testRetrieveEtudiant() {
        Etudiant etudiant = new Etudiant();
        Integer idEtudiant = 1;

        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(idEtudiant);

        assertThat(result).isEqualTo(etudiant);
        verify(etudiantRepository, times(1)).findById(idEtudiant);
    }

    @Test
    public void testRemoveEtudiant() {
        Integer idEtudiant = 1;

        doNothing().when(etudiantRepository).deleteById(idEtudiant);

        etudiantService.removeEtudiant(idEtudiant);

        verify(etudiantRepository, times(1)).deleteById(idEtudiant);
    }
}