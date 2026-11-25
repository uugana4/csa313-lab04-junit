package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * GradeCalculator-ийн нэгжийн тестүүд. Тест бүр Arrange-Act-Assert (AAA) бүтэцтэй.
 */
class GradeCalculatorTest {

    // ---------- letterGrade: ердийн утгууд ----------

    @ParameterizedTest
    @CsvSource({"95,A", "85,B", "75,C", "65,D", "30,F"})
    @DisplayName("Ердийн оноонууд зөв үсгэн дүн өгнө")
    void typicalGrades(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(score);         // Act
        assertEquals(expected, grade);                  // Assert
    }

    // ---------- letterGrade: хязгаарын утгууд ----------

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(90.0);          // Act
        assertEquals("A", grade);                       // Assert
    }

    @Test
    @DisplayName("89.99 оноо A биш, B дүн байх ёстой")
    void justBelowNinetyIsB() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(89.99);         // Act
        assertEquals("B", grade);                       // Assert
    }

    @ParameterizedTest
    @CsvSource({"90,A", "89.99,B", "80,B", "79.99,C", "70,C", "69.99,D",
                "60,D", "59.99,F", "0,F", "100,A"})
    @DisplayName("Бүх дүнгийн хязгаарын утгууд зөв ангилагдана")
    void letterGradeBoundaries(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String grade = calc.letterGrade(score);         // Act
        assertEquals(expected, grade);                  // Assert
    }

    @Test
    @DisplayName("Хоёр захын утга: 0 -> F, 100 -> A")
    void extremesAreValid() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        String low = calc.letterGrade(0);               // Act
        String high = calc.letterGrade(100);
        assertAll(                                      // Assert
            () -> assertEquals("F", low),
            () -> assertEquals("A", high));
    }

    // ---------- letterGrade: буруу оролт ----------

    @ParameterizedTest
    @ValueSource(doubles = {-1, -0.01, 100.01, 101, 1000})
    @DisplayName("0-100 хязгаараас гарсан оноо IllegalArgumentException шиднэ")
    void letterGradeRejectsOutOfRange(double score) {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
            () -> calc.letterGrade(score));
    }

    @Test
    @DisplayName("NaN оноо IllegalArgumentException шиднэ")
    void letterGradeRejectsNaN() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
            () -> calc.letterGrade(Double.NaN));
    }

    // ---------- totalScore ----------

    @Test
    @DisplayName("Бүх онооны дээд утгын нийлбэр 100 байна")
    void totalScoreOfMaximumsIs100() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        double total = calc.totalScore(10, 40, 10, 10, 30); // Act
        assertEquals(100.0, total, 1e-9);               // Assert
    }

    @ParameterizedTest
    @CsvSource({"0,0,0,0,0,0", "10,40,10,10,30,100", "8,35,7,9,25,84",
                "5,20,5,5,15,50", "9.5,38.5,8.5,9,27,92.5"})
    @DisplayName("Хязгаар доторх оноонуудын нийлбэр зөв гарна")
    void totalScoreValidCombinations(double att, double lab, double q1, double q2,
                                     double exam, double expected) {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        double total = calc.totalScore(att, lab, q1, q2, exam); // Act
        assertEquals(expected, total, 1e-9);            // Assert
    }

    @Test
    @DisplayName("Ирц сөрөг (-5) бол IllegalArgumentException шиднэ")
    void totalScoreRejectsNegativeAttendance() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        IllegalArgumentException ex = assertThrows(     // Act
            IllegalArgumentException.class,
            () -> calc.totalScore(-5, 40, 10, 10, 30));
        assertTrue(ex.getMessage().contains("Ирц"));    // Assert
    }

    @Test
    @DisplayName("Лаб дээд хязгаараас хэтэрвэл (41) IllegalArgumentException шиднэ")
    void totalScoreRejectsLabAboveMax() {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
            () -> calc.totalScore(10, 41, 10, 10, 30));
    }

    @ParameterizedTest
    @CsvSource({"-1,0,0,0,0", "11,0,0,0,0", "0,-1,0,0,0", "0,41,0,0,0",
                "0,0,-1,0,0", "0,0,11,0,0", "0,0,0,-1,0", "0,0,0,11,0",
                "0,0,0,0,-1", "0,0,0,0,31"})
    @DisplayName("Оноо бүрийн сөрөг болон хэтэрсэн утга exception шиднэ")
    void totalScoreRejectsEachInvalidComponent(double att, double lab, double q1,
                                               double q2, double exam) {
        GradeCalculator calc = new GradeCalculator();   // Arrange
        assertThrows(IllegalArgumentException.class,    // Act + Assert
            () -> calc.totalScore(att, lab, q1, q2, exam));
    }
}
