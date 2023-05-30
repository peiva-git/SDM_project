package it.units.sdm.project.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import it.units.sdm.project.enums.GameStatus;
import org.jetbrains.annotations.NotNull;

public class GameStatusChangeDialog extends Dialog {

    @NotNull
    private final FreedomGame game;

    public GameStatusChangeDialog(@NotNull FreedomGame game, @NotNull Skin skin) {
        super("", skin);
        this.game = game;
        padBottom(40f);
    }
    @Override
    protected void result(Object object) {
        super.result(object);
        if(!(object instanceof GameStatus)) {
            return;
        }
        GameStatus gameStatus = (GameStatus) object;
        game.setGameStatus(gameStatus);
    }
}
