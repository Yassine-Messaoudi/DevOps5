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

import tn.esprit.firstproject.entity.Chambre;
import tn.esprit.firstproject.entity.TypeChambre;
import tn.esprit.firstproject.repositories.ChambreRepository;




class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllChambres() {
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre1, chambre2));

        List<Chambre> chambres = chambreService.retrieveAllChambres();

        assertThat(chambres).hasSize(2);
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testAddChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre savedChambre = chambreService.addChambre(chambre);

        assertThat(savedChambre).isNotNull();
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testUpdateChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre updatedChambre = chambreService.updateChambre(chambre);

        assertThat(updatedChambre).isNotNull();
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testRetrieveChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.findById(anyInt())).thenReturn(Optional.of(chambre));

        Chambre retrievedChambre = chambreService.retrieveChambre(1);

        assertThat(retrievedChambre).isNotNull();
        verify(chambreRepository, times(1)).findById(1);
    }

    @Test
    void testGetChambresParNomUniversite() {
        Chambre chambre = new Chambre();
        when(chambreRepository.findByBlocFoyerUniversiteNomUniversiteLike(anyString())).thenReturn(Arrays.asList(chambre));

        List<Chambre> chambres = chambreService.getChambresParNomUniversite("Test University");

        assertThat(chambres).hasSize(1);
        verify(chambreRepository, times(1)).findByBlocFoyerUniversiteNomUniversiteLike("Test University");
    }

    @Test
    void testGetChambresParBlocEtType() {
        Chambre chambre = new Chambre();
        when(chambreRepository.findByTypeCAndBlocIdBloc(any(TypeChambre.class), anyInt())).thenReturn(Arrays.asList(chambre));

        List<Chambre> chambres = chambreService.getChambresParBlocEtType(1, TypeChambre.Simple);

        assertThat(chambres).hasSize(1);
        verify(chambreRepository, times(1)).findByTypeCAndBlocIdBloc(TypeChambre.Simple, 1);
    }

    @Test
    void testGetChambresNonReserveParNomUniversiteEtTypeChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.findByTypeCAndBlocFoyerUniversiteNomUniversiteLike(any(TypeChambre.class), anyString())).thenReturn(Arrays.asList(chambre));

        List<Chambre> chambres = chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre("Test University", TypeChambre.Simple);

        assertThat(chambres).hasSize(1);
        verify(chambreRepository, times(1)).findByTypeCAndBlocFoyerUniversiteNomUniversiteLike(TypeChambre.Simple, "Test University");
    }
}