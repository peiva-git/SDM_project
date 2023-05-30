package it.units.sdm.project.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import it.units.sdm.project.enums.GameStatus;
import org.jetbrains.annotations.NotNull;

public class FreedomGameDialog extends Dialog {

    @NotNull
    private final FreedomGame game;

    public FreedomGameDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        padBottom(40f);
    }
    @Override
    protected void result(Object object) {
        if(!(object instanceof GameStatus)) {
            return;
        }
        GameStatus gameStatus = (GameStatus) object;
        game.setGameStatus(gameStatus);
    }

}
