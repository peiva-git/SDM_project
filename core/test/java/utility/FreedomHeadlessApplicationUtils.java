package utility;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kotcrab.vis.ui.VisUI;
import org.jetbrains.annotations.NotNull;

import static org.mockito.Mockito.mock;

public class FreedomHeadlessApplicationUtils {
    /**
     * This method initializes a {@link HeadlessApplication} with {@link VisUI} and the assets specified in
     * the file located at {@code atlasFilePath}.
     * The graphical components are mocked with {@link org.mockito.Mockito} and can't therefore be rendered,
     * but can be otherwise tested upon.
     * When closed, {@link VisUI#dispose()} should be invoked as well
     * to prevent memory leaks.
     * @param atlasFilePath The filepath of the atlas to be added to the {@link VisUI} {@link com.badlogic.gdx.scenes.scene2d.ui.Skin}
     * @return The initialized {@link HeadlessApplication}
     */
    public static @NotNull HeadlessApplication initHeadlessApplication(@NotNull String atlasFilePath) {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication application = new HeadlessApplication(mock(ApplicationListener.class), config);
        Gdx.gl = mock(GL20.class);
        VisUI.load();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        VisUI.getSkin().addRegions(atlas);
        return application;
    }
}
