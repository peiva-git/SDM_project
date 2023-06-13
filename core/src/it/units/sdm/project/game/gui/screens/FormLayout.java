package it.units.sdm.project.game.gui.screens;

import com.badlogic.gdx.graphics.Color;
import com.kotcrab.vis.ui.util.form.FormInputValidator;
import com.kotcrab.vis.ui.util.form.SimpleFormValidator;
import com.kotcrab.vis.ui.widget.*;
import it.units.sdm.project.board.MapBoard;
import org.jetbrains.annotations.NotNull;


public class FormLayout extends VisTable {
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MIN_BOARD_SIZE = MapBoard.MIN_BOARD_SIZE;
    @NotNull
    private final VisValidatableTextField whitePlayerName;
    @NotNull
    private final VisValidatableTextField whitePlayerSurname;
    @NotNull
    private final VisValidatableTextField blackPlayerName;
    @NotNull
    private final VisValidatableTextField blackPlayerSurname;
    @NotNull
    private final VisLabel errorMessage;
    @NotNull
    private final VisSlider boardSize;
    @NotNull
    private final VisTextField boardSizeText;
    @NotNull
    private final VisTextButton continueButton;

    public FormLayout() {
        continueButton = new VisTextButton("Let's play!");
        whitePlayerName = new VisValidatableTextField();
        whitePlayerSurname = new VisValidatableTextField();
        blackPlayerName = new VisValidatableTextField();
        blackPlayerSurname = new VisValidatableTextField();

        whitePlayerName.setMaxLength(MAX_NAME_LENGTH + 1);
        whitePlayerSurname.setMaxLength(MAX_NAME_LENGTH + 1);
        blackPlayerName.setMaxLength(MAX_NAME_LENGTH + 1);
        blackPlayerSurname.setMaxLength(MAX_NAME_LENGTH + 1);

        errorMessage = new VisLabel();
        errorMessage.setColor(Color.RED);

        boardSize = new VisSlider(MIN_BOARD_SIZE, MapBoard.MAX_BOARD_SIZE, 1, false);
        boardSizeText = new VisTextField(String.valueOf(MIN_BOARD_SIZE));
        boardSizeText.setReadOnly(true);
        boardSizeText.setDisabled(true);

        defaults().padLeft(10);
        defaults().padRight(10);
        defaults().padBottom(5);
        defaults().padTop(5);
        columnDefaults(0).left();
        add(new VisLabel("First player's name: "));
        add(whitePlayerName).expand().fill();
        row();
        add(new VisLabel("First player's surname: "));
        add(whitePlayerSurname).expand().fill();
        row();
        add(new VisLabel("Second player's name: "));
        add(blackPlayerName).expand().fill();
        row();
        add(new VisLabel("Second player's surname: "));
        add(blackPlayerSurname).expand().fill();
        row();
        add(errorMessage).expand().fill().colspan(2);
        row();
        add(new VisLabel("Choose board size: "));
        add(boardSizeText).expand().fill();
        row();
        add(boardSize).fill().expand().colspan(2);
        row();
        add(continueButton).fill().expand().colspan(2).padBottom(3);

        SimpleFormValidator validator = new SimpleFormValidator(continueButton, errorMessage);
        validator.setSuccessMessage("All good!");
        validator.notEmpty(whitePlayerName, "First player's name can't be empty");
        validator.custom(whitePlayerName, new FormInputValidator("First player's name length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
        validator.notEmpty(whitePlayerSurname, "First player's surname can't be empty");
        validator.custom(whitePlayerSurname, new FormInputValidator("First player's surname length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
        validator.notEmpty(blackPlayerName, "Second player's name can't be empty");
        validator.custom(blackPlayerName, new FormInputValidator("Second player's name length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
        validator.notEmpty(blackPlayerSurname, "Second player's surname can't be empty");
        validator.custom(blackPlayerSurname, new FormInputValidator("Second player's surname length can't exceed " + MAX_NAME_LENGTH) {
            @Override
            protected boolean validate(String input) {
                return input.length() <= MAX_NAME_LENGTH;
            }
        });
    }

    public @NotNull VisValidatableTextField getWhitePlayerName() {
        return whitePlayerName;
    }

    public @NotNull VisValidatableTextField getWhitePlayerSurname() {
        return whitePlayerSurname;
    }

    public @NotNull VisValidatableTextField getBlackPlayerName() {
        return blackPlayerName;
    }

    public @NotNull VisValidatableTextField getBlackPlayerSurname() {
        return blackPlayerSurname;
    }

    public @NotNull VisLabel getErrorMessage() {
        return errorMessage;
    }

    public @NotNull VisSlider getBoardSize() {
        return boardSize;
    }

    public @NotNull VisTextField getBoardSizeText() {
        return boardSizeText;
    }

    public @NotNull VisTextButton getContinueButton() {
        return continueButton;
    }
}
