package tn.esprit.firstproject.api.repository;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.firstproject.entity.Bloc;
import tn.esprit.firstproject.entity.Chambre;
import tn.esprit.firstproject.repositories.BlocRepository;
import tn.esprit.firstproject.repositories.ChambreRepository;
import tn.esprit.firstproject.serviceIMP.BlocServiceImpl;

import java.util.List;
import java.util.Optional;

public class BlocServiceTestImpl {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    private Bloc bloc;
    private Chambre chambre;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bloc = Bloc.builder().idBloc(1).nomBloc("Bloc A").capaciteBloc(100).build();
        chambre = Chambre.builder().numeroChambre(101L).bloc(bloc).build();
    }

    @Test
    public void testRetrieveBlocs() {
        // Arrange
        when(blocRepository.findAll()).thenReturn(List.of(bloc));

        // Act
        List<Bloc> blocs = blocService.retrieveBlocs();

        // Assert
        assertNotNull(blocs);
        assertEquals(1, blocs.size());
        assertEquals("Bloc A", blocs.get(0).getNomBloc());
    }

    @Test
    public void testUpdateBloc() {
        // Arrange
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc updatedBloc = blocService.updateBloc(bloc);

        // Assert
        assertNotNull(updatedBloc);
        assertEquals("Bloc A", updatedBloc.getNomBloc());
    }

    @Test
    public void testAddBloc() {
        // Arrange
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc addedBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(addedBloc);
        assertEquals("Bloc A", addedBloc.getNomBloc());
    }

    @Test
    public void testRetrieveBloc() {
        // Arrange
        when(blocRepository.findById(1)).thenReturn(Optional.of(bloc));

        // Act
        Bloc retrievedBloc = blocService.retrieveBloc(1);

        // Assert
        assertNotNull(retrievedBloc);
        assertEquals("Bloc A", retrievedBloc.getNomBloc());
    }

    @Test
    public void testRemoveBloc() {
        // Arrange
        doNothing().when(blocRepository).deleteById(1);

        // Act
        blocService.removeBloc(1);

        // Assert
        verify(blocRepository, times(1)).deleteById(1);
    }

    @Test
    public void testAffecterChambresABloc() {
        // Arrange
        when(chambreRepository.findAllByNumeroChambreIn(anyList())).thenReturn(List.of(chambre));
        when(blocRepository.findById(1)).thenReturn(Optional.of(bloc));

        // Act
        Bloc affectedBloc = blocService.affecterChambresABloc(List.of(101L), 1);

        // Assert
        assertNotNull(affectedBloc);
        assertEquals(bloc, affectedBloc);
        verify(chambreRepository, times(1)).save(any(Chambre.class));
    }
}
