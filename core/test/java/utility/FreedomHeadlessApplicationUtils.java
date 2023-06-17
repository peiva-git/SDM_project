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
     * the {@code freedom.atlas} file. WHen closed, {@link VisUI#dispose()} should be invoked as well to dispose of
     * to prevent memory leaks
     * @return The initialized {@link HeadlessApplication}
     */
    public static @NotNull HeadlessApplication initHeadlessApplication() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication application = new HeadlessApplication(mock(ApplicationListener.class), config);
        Gdx.gl = mock(GL20.class);
        VisUI.load();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("freedom.atlas"));
        VisUI.getSkin().addRegions(atlas);
        return application;
    }
}
