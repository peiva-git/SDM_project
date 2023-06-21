package game.gui;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.game.gui.FreedomGame;
import it.units.sdm.project.game.gui.screens.FormLayout;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utility.FreedomHeadlessApplicationUtils.initHeadlessApplication;

class FormValidationTests {
    private static HeadlessApplication application;
    private static FormLayout form;

    @BeforeAll
    static void initApplication() {
        application = initHeadlessApplication("freedom.atlas");
        form = new FormLayout();
    }

    @BeforeEach
    void resetForm() {
        form.getWhitePlayerUsername().clearText();
        form.getBlackPlayerUsername().clearText();
        form.getBoardSizeSlider().setValue(FreedomGame.MIN_BOARD_SIZE);
        form.getBoardSizeText().setText(String.valueOf(FreedomGame.MIN_BOARD_SIZE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Walter", "Jeffrey", "QvSF9h2oYQsb16EExhGU"})
    void testPlayerUsernamesValidationWithCorrectValues(String username) {
        form.getWhitePlayerUsername().setText(username);
        form.getBlackPlayerUsername().setText(username);
        form.getWhitePlayerUsername().validateInput();
        form.getBlackPlayerUsername().validateInput();
        assertTrue(form.getWhitePlayerUsername().isInputValid());
        assertTrue(form.getBlackPlayerUsername().isInputValid());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"QvSF9h2oYQsb16EExhGUe"})
    void testPlayerUsernamesValidationWithIncorrectValues(String username) {
        form.getWhitePlayerUsername().setText(username);
        form.getBlackPlayerUsername().setText(username);
        form.getWhitePlayerUsername().validateInput();
        form.getBlackPlayerUsername().validateInput();
        assertFalse(form.getWhitePlayerUsername().isInputValid());
        assertFalse(form.getBlackPlayerUsername().isInputValid());
    }

    @AfterAll
    static void cleanup() {
        VisUI.dispose();
        application.exit();
    }
}
