package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.graphics.Color;
import com.kotcrab.vis.ui.util.form.FormInputValidator;
import com.kotcrab.vis.ui.util.form.SimpleFormValidator;
import com.kotcrab.vis.ui.widget.*;
import it.units.sdm.project.board.MapBoard;
import it.units.sdm.project.game.gui.FreedomGame;
import org.jetbrains.annotations.NotNull;

/**
 * A form to be used in a {@code scene2d} user interface,
 * where the {@link it.units.sdm.project.game.Player}s can customize their next {@link it.units.sdm.project.game.gui.FreedomGame}.
 * Allows both {@link it.units.sdm.project.game.Player}s to set their name, surname, and the {@link it.units.sdm.project.board.Board}
 * size for the next {@link it.units.sdm.project.game.gui.FreedomGame}
 */
public class FormLayout extends VisTable {
    private static final int MAX_NAME_LENGTH = 20;
    private static final float TABLE_PADDING = 40f;
    public static final float CELLS_PADDING = 20f;
    @NotNull
    private final VisValidatableTextField whitePlayerUsername;
    @NotNull
    private final VisValidatableTextField blackPlayerUsername;
    @NotNull
    private final VisLabel errorMessage;
    @NotNull
    private final VisSlider boardSizeSlider;
    @NotNull
    private final VisValidatableTextField boardSizeText;
    @NotNull
    private final VisTextButton continueButton;

    /**
     * Creates a new {@link FormLayout} instance. Also sets validation for all input fields.
     * All the form's fields are accessible with getter methods.
     */
    public FormLayout() {
        continueButton = new VisTextButton("Let's play!");
        whitePlayerUsername = new VisValidatableTextField();
        blackPlayerUsername = new VisValidatableTextField();

        whitePlayerUsername.setMaxLength(MAX_NAME_LENGTH + 1);
        blackPlayerUsername.setMaxLength(MAX_NAME_LENGTH + 1);

        errorMessage = new VisLabel();
        errorMessage.setColor(Color.RED);

        boardSizeSlider = new VisSlider(FreedomGame.MIN_BOARD_SIZE, FreedomGame.MAX_BOARD_SIZE, 1, false);
        boardSizeSlider.setValue(FreedomGame.MIN_BOARD_SIZE);
        boardSizeText = new VisValidatableTextField(String.valueOf(FreedomGame.MIN_BOARD_SIZE));
        boardSizeText.setReadOnly(true);

        paddingSetUp();
        fillTable();
        setupValidation();
    }

    private void fillTable() {
        add(new VisLabel("White player username: "));
        add(whitePlayerUsername).expand().fill();
        row();
        add(new VisLabel("Black player username: "));
        add(blackPlayerUsername).expand().fill();
        row();
        add(errorMessage).expand().fill().colspan(2);
        row();
        add(new VisLabel("Choose board size: ")).fill();
        add(boardSizeText).expand().fill();
        row();
        add(boardSizeSlider).fill().expand().colspan(2);
        row();
        add(continueButton).fill().expand().colspan(2);
    }

    private void paddingSetUp() {
        pad(TABLE_PADDING);
        defaults().pad(CELLS_PADDING);
    }

    private void setupValidation() {
        SimpleFormValidator validator = new SimpleFormValidator(continueButton, errorMessage);
        validator.setSuccessMessage("All good!");
        validator.notEmpty(whitePlayerUsername, "White player's name can't be empty");
        validator.custom(whitePlayerUsername, new FormInputValidator("White player's name length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
        validator.notEmpty(blackPlayerUsername, "Black player's name can't be empty");
        validator.custom(blackPlayerUsername, new FormInputValidator("Black player's name length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
    }

    public @NotNull VisValidatableTextField getWhitePlayerUsername() {
        return whitePlayerUsername;
    }

    public @NotNull VisValidatableTextField getBlackPlayerUsername() {
        return blackPlayerUsername;
    }

    public @NotNull VisLabel getErrorMessage() {
        return errorMessage;
    }

    public @NotNull VisSlider getBoardSizeSlider() {
        return boardSizeSlider;
    }

    public @NotNull VisTextField getBoardSizeText() {
        return boardSizeText;
    }

    public @NotNull VisTextButton getContinueButton() {
        return continueButton;
    }
}
