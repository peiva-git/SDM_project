package it.units.sdm.project.board.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;

public class GuiStone extends Stone {

    @NotNull
    private final StaticTiledMapTile staticTiledMapTile;

    public GuiStone(@NotNull Color color, @NotNull StaticTiledMapTile staticTiledMapTile) {
        super(color);
        this.staticTiledMapTile = staticTiledMapTile;
        staticTiledMapTile.getProperties().put("color", color);
    }

    @Override
    public @NotNull Color getColor() {
        return (Color) staticTiledMapTile.getProperties().get("color");
    }

    @NotNull
    public StaticTiledMapTile getStaticTiledMapTile() {
        return staticTiledMapTile;
    }
}
