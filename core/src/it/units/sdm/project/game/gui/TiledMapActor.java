package it.units.sdm.project.game.gui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.jetbrains.annotations.NotNull;

class TiledMapActor extends Actor {

    @NotNull
    private final TiledMapTileLayer tiledLayer;
    @NotNull
    private final TiledMapTileLayer.Cell cell;

    public TiledMapActor(@NotNull TiledMapTileLayer tiledLayer, TiledMapTileLayer.@NotNull Cell cell) {
        this.tiledLayer = tiledLayer;
        this.cell = cell;
    }

    public @NotNull TiledMapTileLayer getTiledLayer() {
        return tiledLayer;
    }

    public TiledMapTileLayer.@NotNull Cell getCell() {
        return cell;
    }
}
