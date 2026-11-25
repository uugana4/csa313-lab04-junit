# Лаб 4: Нэгжийн тестийн эхлэл - JUnit 5 (F.CSA313)

**Оюутан:** А.Ууганбаяр
**Оюутны код:** B242270014

## Орчин

```text
openjdk version "17.0.21.1" 2026-11-17
OpenJDK Runtime Environment Homebrew (build 17.0.21.1+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.21.1+0, mixed mode, sharing)

Apache Maven 3.10.2 (61b8bc355f4c4325463c22aa9afe4e7811a290f8)
Maven home: /opt/homebrew/Cellar/maven/3.10.2/libexec
Java version: 17.0.21.1, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk@17/17.0.21.1/libexec/openjdk.jdk/Contents/Home
Default locale: en_MN, platform encoding: UTF-8, time zone: Asia/Ulaanbaatar
OS name: "mac os x", version: "26.5.2", arch: "aarch64", family: "mac"
```

## Төслийн бүтэц

| Файл | Зорилго |
|---|---|
| `pom.xml` | Maven тохиргоо: `maven.compiler.release` 17, junit-jupiter 5.10.2, surefire 3.2.5 |
| `src/main/java/mn/edu/must/sqat/GradeCalculator.java` | Тестлэгдэх класс: `letterGrade`, `totalScore` |
| `src/test/java/mn/edu/must/sqat/GradeCalculatorTest.java` | JUnit 5 тестүүд |
| `results/mvn-test.txt` | Эх кодтой үеийн `mvn test` гаралт |
| `results/mvn-test-mutant.txt` | Мутацитай үеийн `mvn test` гаралт |
| `results/env.txt` | `java -version`, `mvn -version` |

## Тестлэгдсэн дүрмүүд

- `letterGrade(score)`: 90+ A, 80-89 B, 70-79 C, 60-69 D, 60-аас доош F. 0-100-аас гарсан эсвэл NaN оноонд `IllegalArgumentException`.
- `totalScore(ирц, лаб, сорил1, сорил2, шалгалт)`: дээд хязгаарууд 10 / 40 / 10 / 10 / 30. Сөрөг эсвэл дээд хязгаараас хэтэрсэн утгад `IllegalArgumentException`.

## Тестийн үр дүн

Тестийн **метод 12** (үүний 5 нь `@ParameterizedTest`). Surefire параметрийн мөр бүрийг тусад нь тоолдог тул `results/mvn-test.txt`-д **Tests run: 42, Failures: 0, Errors: 0, Skipped: 0**, BUILD SUCCESS гарсан.

Хязгаарын утгууд: 0, 59.99, 60, 69.99, 70, 79.99, 80, 89.99, 90, 100. Буруу оролт: -1, -0.01, 100.01, 101, 1000, NaN. Тест бүр Arrange-Act-Assert бүтэцтэй, `@DisplayName`-тэй.

## Мутацийн туршилт

`letterGrade` доторх `score >= 90`-ийг зориуд `score > 90` болгоод `mvn test` ажиллуулсан (`results/mvn-test-mutant.txt`).

| | Tests run | Failures | Үр дүн |
|---|---|---|---|
| Эх код | 42 | 0 | BUILD SUCCESS |
| Мутант (`score > 90`) | 42 | 2 | BUILD FAILURE, exit code 1 |

Унасан тестүүд: `ninetyIsExactlyA` ба `letterGradeBoundaries[1]` (оноо 90). Хоёулаа `expected: <A> but was: <B>` алдаа өгсөн. Мутацийг буцаасны дараа тест дахин 42/42 ногоон болсон. 100 оноотой тест мутант дээр унаагүй, учир нь 100 > 90 үнэн. Мутацийг яг 90 дээрх тестүүд барьсан тул хязгаарын тест чухал болохыг харсан.

## Ажиллуулах

```bash
mkdir -p results
mvn test 2>&1 | tee results/mvn-test.txt
```

## Дүгнэлт

Энэ лабораар Maven төсөл дээр JUnit 5-ийн тестийг гараар бичиж, `mvn test`-ээр ажиллуулахыг сурлаа. Дүнгийн хязгаарт алдаа хамгийн их гардаг тул 90, 89.99, 80, 79.99 гэх мэт утгуудыг параметржүүлсэн тестээр шалгасан. `totalScore`-ийн сөрөг болон дээд хязгаараас хэтэрсэн утгыг `assertThrows`-оор шалгасан. Мутацийн туршилтаар `>=`-ийг `>` болгоход 2 тест унасан нь тест хязгаарыг үнэхээр шалгаж байгааг харуулсан. Хэрэв би зөвхөн 95, 85 гэх мэт ердийн оноогоор тестлэсэн бол энэ алдааг олохгүй байх байсан. Surefire параметрийн мөр бүрийг тусад нь тоолдог тул "Tests run: 42" нь 12 методоос бүрдэнэ гэдгийг ялгаж бичив.

## AI ашигласан байдал

Төслийн эхний ноорог (pom.xml, GradeCalculator, тестийн бүтэц) болон командуудыг Claude-ээр бэлтгүүлсэн. `mvn test`, мутацийн туршилтыг би өөрийн компьютер дээр ажиллуулсан бөгөөд дээрх тоонууд нь `results/` доторх файлуудаас авсан бодит үр дүн.
