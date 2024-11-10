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

import tn.esprit.firstproject.entity.Foyer;
import tn.esprit.firstproject.entity.Universite;
import tn.esprit.firstproject.repositories.BlocRepository;
import tn.esprit.firstproject.repositories.FoyerRepository;
import tn.esprit.firstproject.repositories.UniversiteRepository;






class UniversiteServiceImplTest {

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    FoyerRepository foyerRepository;

    @Mock
    BlocRepository blocRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllUniversities() {
        Universite universite1 = new Universite();
        Universite universite2 = new Universite();
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite1, universite2));

        List<Universite> result = universiteService.retrieveAllUniversities();

        assertThat(result).hasSize(2);
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversity() {
        Universite universite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.addUniversity(universite);

        assertThat(result).isNotNull();
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testUpdateUniversity() {
        Universite universite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.updateUniversity(universite);

        assertThat(result).isNotNull();
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversity() {
        Universite universite = new Universite();
        when(universiteRepository.findById(anyInt())).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversity(1);

        assertThat(result).isNotNull();
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testAffecterFoyerAUniversite() {
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        when(foyerRepository.findById(anyInt())).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversiteLike(anyString())).thenReturn(universite);
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.affecterFoyerAUniversite(1, "Test University");

        assertThat(result).isNotNull();
        assertThat(result.getFoyer()).isEqualTo(foyer);
        verify(foyerRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).findByNomUniversiteLike("Test University");
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testDesaffecterFoyerAUnivrsite() {
        Universite universite = new Universite();
        universite.setFoyer(new Foyer());
        when(universiteRepository.findById(anyInt())).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.desaffecterFoyerAUnivrsite(1);

        assertThat(result).isNotNull();
        assertThat(result.getFoyer()).isNull();
        verify(universiteRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).save(universite);
    }
}