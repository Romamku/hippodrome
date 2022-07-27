import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {
    @Test
    public void nullHorsesException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    public void emptyListException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));

        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    public void getHorses() {
        List<Horse> horsesList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horsesList.add(new Horse("" + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horsesList);

        assertEquals(horsesList, hippodrome.getHorses());
    }

    @Test
    public void move() {
        List<Horse> horsesList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horsesList.add(mock(Horse.class));
        }
        new Hippodrome(horsesList).move();

        for (Horse horse : horsesList) {
            verify(horse, times(1)).move();
        }
    }
    @Test
    public void getWinner() {
        Horse horse1 = new Horse("1", 1, 1);
        Horse horse2 = new Horse("1", 1, 2);
        Horse horse3 = new Horse("1", 1, 3);
        Horse horse4 = new Horse("1", 1, 3.1);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse4, hippodrome.getWinner());
    }

}