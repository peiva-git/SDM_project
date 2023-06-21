import com.beust.jcommander.ParameterException;
import it.units.sdm.project.NameSizeValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameSizeValidationTests {
    private final NameSizeValidator validator = new NameSizeValidator();

    @ParameterizedTest
    @ValueSource(strings = {"Marco", "Matteo", "nyhImZaOHnAiexeM4vnXNc5qD815Ni"})
    void testNameSizeValidationWithCorrectValues(String inputValue) {
        assertDoesNotThrow(() -> validator.validate("White player name", inputValue));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"nyhImZaOHnAiexeM4vnXNc5qD815Nit"})
    void testNameSizeValidationWithIncorrectValues(String inputValue) {
        assertThrows(ParameterException.class, () -> validator.validate("White player name", inputValue));
    }
}
