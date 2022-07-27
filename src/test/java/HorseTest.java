import jdk.jshell.EvalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nullNameMassage() {
        try {
            new Horse(null, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n\n"})
    public void blankNameException(String testName) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(testName, 1, 1));

        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void speedNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("testName", -1, 1));
    }

    @Test
    public void speedNegativeValueMassage() {
        try {
            new Horse("testName", -1, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void distanceNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("testName", 1, -1));
    }

    @Test
    public void distanceNegativeValueMassage() {
        try {
            new Horse("testName", 1, -1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("testName", 1, 1);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);

        assertEquals("testName", nameValue);
    }

    @Test
    public void getNameTwo() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("testName", 1, 1);

        assertEquals("testName", horse.getName());
    }

    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("testName", 1, 1);

        Field name = Horse.class.getDeclaredField("speed");
        name.setAccessible(true);
        Double testSpeed = (Double) name.get(horse);

        assertEquals(1, testSpeed);
    }

    @Test
    public void getSpeedTwo() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("testName", 1, 1);

        assertEquals(1, horse.getSpeed());
    }

    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("testName", 1, 1);

        Field name = Horse.class.getDeclaredField("distance");
        name.setAccessible(true);
        Double testDistance = (Double) name.get(horse);

        assertEquals(1, testDistance);
    }

    @Test
    public void getDistanceTwo() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("testName", 1, 1);

        assertEquals(1, horse.getDistance());
    }

    @Test
    public void zeroDistanceByDefault() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("testName", 1);

        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveUsesGetRandom() {
        try (MockedStatic<Horse> mock = mockStatic(Horse.class);) {
            new Horse("testName", 100, 200).move();

            mock.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    public void move(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("testName", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(283 + 31*random, horse.getDistance());
        }
    }
}
