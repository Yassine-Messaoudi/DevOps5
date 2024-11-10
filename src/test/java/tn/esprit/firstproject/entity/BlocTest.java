package tn.esprit.firstproject.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class BlocTest {

    @Test
    public void testBlocBuilder() {
        Bloc bloc = Bloc.builder()
                .idBloc(1)
                .nomBloc("Bloc A")
                .capaciteBloc(100)
                .build();

        assertThat(bloc.getIdBloc()).isEqualTo(1);
        assertThat(bloc.getNomBloc()).isEqualTo("Bloc A");
        assertThat(bloc.getCapaciteBloc()).isEqualTo(100);
    }

    @Test
    public void testBlocSettersAndGetters() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(2);
        bloc.setNomBloc("Bloc B");
        bloc.setCapaciteBloc(200);

        assertThat(bloc.getIdBloc()).isEqualTo(2);
        assertThat(bloc.getNomBloc()).isEqualTo("Bloc B");
        assertThat(bloc.getCapaciteBloc()).isEqualTo(200);
    }

    @Test
    public void testBlocToString() {
        Bloc bloc = Bloc.builder()
                .idBloc(3)
                .nomBloc("Bloc C")
                .capaciteBloc(300)
                .build();

        String blocString = bloc.toString();
        assertThat(blocString).contains("idBloc=3");
        assertThat(blocString).contains("nomBloc=Bloc C");
        assertThat(blocString).contains("capaciteBloc=300");
    }

    @Test
    public void testBlocNoArgsConstructor() {
        Bloc bloc = new Bloc();
        assertThat(bloc).isNotNull();
    }

    @Test
    public void testBlocAllArgsConstructor() {
        Bloc bloc = new Bloc(4, "Bloc D", 400, null, null);
        assertThat(bloc.getIdBloc()).isEqualTo(4);
        assertThat(bloc.getNomBloc()).isEqualTo("Bloc D");
        assertThat(bloc.getCapaciteBloc()).isEqualTo(400);
    }
}