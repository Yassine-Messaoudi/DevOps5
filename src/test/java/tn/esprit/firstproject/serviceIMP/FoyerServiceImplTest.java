
package tn.esprit.firstproject.serviceIMP;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.firstproject.entity.Bloc;
import tn.esprit.firstproject.entity.Foyer;
import tn.esprit.firstproject.entity.Universite;
import tn.esprit.firstproject.repositories.BlocRepository;
import tn.esprit.firstproject.repositories.FoyerRepository;
import tn.esprit.firstproject.repositories.UniversiteRepository;





class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllFoyers() {
        Foyer foyer = new Foyer();
        when(foyerRepository.findAll()).thenReturn(Collections.singletonList(foyer));

        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        assertThat(foyers).hasSize(1);
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testAddFoyer() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer savedFoyer = foyerService.addFoyer(foyer);

        assertThat(savedFoyer).isNotNull();
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testUpdateFoyer() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer updatedFoyer = foyerService.updateFoyer(foyer);

        assertThat(updatedFoyer).isNotNull();
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRetrieveFoyer() {
        Foyer foyer = new Foyer();
        when(foyerRepository.findById(anyInt())).thenReturn(Optional.of(foyer));

        Foyer retrievedFoyer = foyerService.retrieveFoyer(1);

        assertThat(retrievedFoyer).isNotNull();
        verify(foyerRepository, times(1)).findById(1);
    }

    @Test
    void testRemoveFoyer() {
        doNothing().when(foyerRepository).deleteById(anyInt());

        foyerService.removeFoyer(1);

        verify(foyerRepository, times(1)).deleteById(1);
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() {
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        Bloc bloc = new Bloc();
        Set<Bloc> blocs = Collections.singleton(bloc);
        foyer.setBlocs(blocs);

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(universiteRepository.findById(anyInt())).thenReturn(Optional.of(universite));
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Foyer savedFoyer = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1);

        assertThat(savedFoyer).isNotNull();
        verify(foyerRepository, times(1)).save(foyer);
        verify(universiteRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).save(universite);
        verify(blocRepository, times(1)).save(bloc);
    }
}