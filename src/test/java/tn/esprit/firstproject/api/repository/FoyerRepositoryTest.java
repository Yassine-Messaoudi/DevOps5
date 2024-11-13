package tn.esprit.firstproject.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.firstproject.entity.Foyer;
import tn.esprit.firstproject.repositories.FoyerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class FoyerRepositoryTest {
    @Autowired
    private FoyerRepository foyerRepository;


    @Test
    public void FoyerRepository_Save_ReturnSavedFoyer() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer A")
                .capaciteFoyer(100)
                .build();

        // Act
        Foyer savedFoyer = foyerRepository.save(foyer);

        // Assert
        assertThat(savedFoyer).isNotNull();
        assertThat(savedFoyer.getIdFoyer()).isGreaterThan(0);
        assertThat(savedFoyer.getNomFoyer()).isEqualTo("Foyer A");
    }


    @Test
    public void FoyerRepository_Update_ReturnUpdatedFoyer() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .nomFoyer("Initial Foyer")
                .capaciteFoyer(200)
                .build();
        Foyer savedFoyer = foyerRepository.save(foyer);

        // Update details
        savedFoyer.setNomFoyer("Updated Foyer");
        savedFoyer.setCapaciteFoyer(250);

        // Act
        Foyer updatedFoyer = foyerRepository.save(savedFoyer);

        // Assert
        assertThat(updatedFoyer).isNotNull();
        assertThat(updatedFoyer.getIdFoyer()).isEqualTo(savedFoyer.getIdFoyer());
        assertThat(updatedFoyer.getNomFoyer()).isEqualTo("Updated Foyer");
        assertThat(updatedFoyer.getCapaciteFoyer()).isEqualTo(250);
    }

    @Test
    public void FoyerRepository_Delete_RemovesFoyer() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer to Delete")
                .capaciteFoyer(80)
                .build();
        Foyer savedFoyer = foyerRepository.save(foyer);

        // Act
        foyerRepository.deleteById(savedFoyer.getIdFoyer());
        boolean foyerExists = foyerRepository.findById(savedFoyer.getIdFoyer()).isPresent();

        // Assert
        assertThat(foyerExists).isFalse();
    }

    @Test
    public void FoyerRepository_FindById_ReturnFoyer() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer B")
                .capaciteFoyer(150)
                .build();
        Foyer savedFoyer = foyerRepository.save(foyer);

        // Act
        Foyer foundFoyer = foyerRepository.findById(savedFoyer.getIdFoyer()).orElse(null);

        // Assert
        assertThat(foundFoyer).isNotNull();
        assertThat(foundFoyer.getIdFoyer()).isEqualTo(savedFoyer.getIdFoyer());
        assertThat(foundFoyer.getNomFoyer()).isEqualTo("Foyer B");
    }
    @Test
    public void FoyerRepository_FindAll_ReturnAllFoyers() {
        // Arrange
        Foyer foyer1 = Foyer.builder()
                .nomFoyer("Foyer 1")
                .capaciteFoyer(120)
                .build();

        Foyer foyer2 = Foyer.builder()
                .nomFoyer("Foyer 2")
                .capaciteFoyer(140)
                .build();

        foyerRepository.save(foyer1);
        foyerRepository.save(foyer2);

        // Act
        List<Foyer> foyers = foyerRepository.findAll();

        // Assert
        assertThat(foyers).isNotNull();
        assertThat(foyers.size()).isGreaterThanOrEqualTo(2);
        assertThat(foyers).extracting(Foyer::getNomFoyer).contains("Foyer 1", "Foyer 2");
    }
}



