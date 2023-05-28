package it.units.sdm.project.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import org.jetbrains.annotations.NotNull;

public class DialogBuilder {
    private static final float BUTTON_SIZE = 80f;
    @NotNull
    private final Dialog dialog;
    @NotNull
    private final Skin skin;
    @NotNull
    private final TextButton positiveButton;
    @NotNull
    private final TextButton negativeButton;

    public DialogBuilder(@NotNull String title, @NotNull Skin skin) {
        this.skin = skin;
        positiveButton = new TextButton("", skin);
        negativeButton = new TextButton("", skin);
        positiveButton.setVisible(false);
        negativeButton.setVisible(false);
        Table buttonTable = new Table();
        buttonTable.add(positiveButton).size(BUTTON_SIZE, BUTTON_SIZE);
        buttonTable.add(negativeButton).size(BUTTON_SIZE, BUTTON_SIZE);
        dialog = new Dialog(title, skin);
        dialog.getButtonTable().add(buttonTable).center().padBottom(80f);
    }

    @NotNull
    public DialogBuilder setModal(boolean isModal) {
        dialog.setModal(isModal);
        return this;
    }

    @NotNull
    public DialogBuilder setResizable(boolean isResizable) {
        dialog.setResizable(isResizable);
        return this;
    }

    @NotNull
    public DialogBuilder setMovable(boolean isMovable) {
        dialog.setMovable(isMovable);
        return this;
    }

    public DialogBuilder setTitle(@NotNull String title) {
        Label titleLabel = new Label(title, skin);
        titleLabel.setAlignment(Align.center);
        dialog.getContentTable().add(titleLabel).padTop(40f);
        return this;
    }

    @NotNull
    public DialogBuilder setPositiveButtonLabel(@NotNull String label) {
        positiveButton.setLabel(new Label(label, skin));
        positiveButton.setVisible(true);
        return this;
    }

    @NotNull
    public DialogBuilder setNegativeButtonLabel(@NotNull String label) {
        negativeButton.setLabel(new Label(label, skin));
        negativeButton.setVisible(true);
        return this;
    }

    @NotNull
    public DialogBuilder addPositiveButtonListener(@NotNull ClickListener listener) {
        positiveButton.addListener(listener);
        return this;
    }

    @NotNull
    public DialogBuilder addNegativeButtonListener(@NotNull ClickListener listener) {
        negativeButton.addListener(listener);
        return this;
    }

    @NotNull
    public Dialog build() {
        return dialog;
    }


}
