package it.units.sdm.project.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import org.jetbrains.annotations.NotNull;

public class FreedomGameDialog extends Dialog {

    @NotNull
    private final Label mainLabel;
    @NotNull
    private final TextButton positiveButton;
    @NotNull
    private final TextButton negativeButton;

    public FreedomGameDialog(@NotNull String mainLabelString, @NotNull String positiveButtonLabel, @NotNull String negativeButtonLabel, @NotNull Skin skin) {
        super("", skin);
        this.positiveButton = new TextButton(positiveButtonLabel, skin);
        this.negativeButton = new TextButton(negativeButtonLabel, skin);
        this.mainLabel = new Label(mainLabelString, skin);
        buildDialog();
    }

    private void buildDialog() {
        positiveButton.pad(10);
        negativeButton.pad(10);
        Table buttonTable = new Table();
        buttonTable.add(positiveButton).pad(10);
        buttonTable.add(negativeButton).pad(10);
        getButtonTable().add(buttonTable).center().padBottom(40f);
        mainLabel.setAlignment(Align.center);
        getContentTable().add(mainLabel).padTop(40f);
    }

    public void setMainLabel(@NotNull String mainLabelString) {
        this.mainLabel.setText(mainLabelString);
    }

    public void setPositiveButton(@NotNull String positiveButtonLabel) {
        this.positiveButton.setText(positiveButtonLabel);
    }

    public void setNegativeButton(@NotNull String negativeButtonLabel) {
        this.negativeButton.setText(negativeButtonLabel);
    }

    public void addPositiveButtonListener(@NotNull ClickListener listener) {
        positiveButton.addListener(listener);
    }

    public void addNegativeButtonListener(@NotNull ClickListener listener) {
        negativeButton.addListener(listener);
    }

}
