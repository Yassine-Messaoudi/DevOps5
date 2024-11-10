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

import tn.esprit.firstproject.entity.Bloc;
import tn.esprit.firstproject.entity.Chambre;
import tn.esprit.firstproject.repositories.BlocRepository;
import tn.esprit.firstproject.repositories.ChambreRepository;




class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveBlocs() {
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertThat(blocs).hasSize(2);
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc updatedBloc = blocService.updateBloc(bloc);

        assertThat(updatedBloc).isEqualTo(bloc);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc addedBloc = blocService.addBloc(bloc);

        assertThat(addedBloc).isEqualTo(bloc);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.findById(anyInt())).thenReturn(Optional.of(bloc));

        Bloc retrievedBloc = blocService.retrieveBloc(1);

        assertThat(retrievedBloc).isEqualTo(bloc);
        verify(blocRepository, times(1)).findById(1);
    }

    @Test
    void testRemoveBloc() {
        doNothing().when(blocRepository).deleteById(anyInt());

        blocService.removeBloc(1);

        verify(blocRepository, times(1)).deleteById(1);
    }

    @Test
    void testAffecterChambresABloc() {
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        Bloc bloc = new Bloc();
        when(chambreRepository.findAllByNumeroChambreIn(anyList())).thenReturn(Arrays.asList(chambre1, chambre2));
        when(blocRepository.findById(anyInt())).thenReturn(Optional.of(bloc));
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre1);

        Bloc affectedBloc = blocService.affecterChambresABloc(Arrays.asList(1L, 2L), 1);

        assertThat(affectedBloc).isEqualTo(bloc);
        verify(chambreRepository, times(1)).findAllByNumeroChambreIn(Arrays.asList(1L, 2L));
        verify(blocRepository, times(1)).findById(1);
        verify(chambreRepository, times(2)).save(any(Chambre.class));
    }

}