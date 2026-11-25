package mn.edu.must.sqat;

/**
 * Оюутны нийлбэр онооноос үсгэн дүн тооцох, хичээлийн оноонуудын нийлбэрийг гаргах класс.
 */
public class GradeCalculator {

    static final double MAX_ATTENDANCE = 10;
    static final double MAX_LAB = 40;
    static final double MAX_QUIZ = 10;
    static final double MAX_EXAM = 30;

    /**
     * 90+ -> A, 80-89 -> B, 70-79 -> C, 60-69 -> D, 60-аас доош -> F.
     * score 0-100 хязгаараас гарвал IllegalArgumentException шиднэ.
     */
    public String letterGrade(double score) {
        if (Double.isNaN(score) || score < 0 || score > 100) {
            throw new IllegalArgumentException("Оноо 0-100 хооронд байх ёстой: " + score);
        }
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    /**
     * Ирц(10), лаб+бие даалт(40), сорил1(10), сорил2(10), шалгалт(30)-ийн нийлбэр.
     * Сөрөг эсвэл дээд хязгаараас хэтэрсэн утгад IllegalArgumentException шиднэ.
     */
    public double totalScore(double att, double lab, double quiz1, double quiz2, double exam) {
        validate("Ирц", att, MAX_ATTENDANCE);
        validate("Лаб", lab, MAX_LAB);
        validate("Сорил 1", quiz1, MAX_QUIZ);
        validate("Сорил 2", quiz2, MAX_QUIZ);
        validate("Шалгалт", exam, MAX_EXAM);
        return att + lab + quiz1 + quiz2 + exam;
    }

    private static void validate(String name, double value, double max) {
        if (Double.isNaN(value) || value < 0 || value > max) {
            throw new IllegalArgumentException(name + " нь 0-" + max + " хооронд байх ёстой: " + value);
        }
    }
}
