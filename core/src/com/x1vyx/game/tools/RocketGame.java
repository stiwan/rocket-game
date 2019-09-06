package com.x1vyx.game.tools;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.game.states.GameStateManager;
import com.x1vyx.game.states.MenuState;

public class RocketGame extends ApplicationAdapter
{
    /* Defaults */
    public static final int WIDTH = 120;
    public static final int HEIGHT = 240;
    public static final String TITLE = "Sandbox";

    /* Game */
    private GameStateManager gsm;
    private SpriteBatch batch;

    /* Config */
    public static boolean muted;
    public static boolean keepBoost = true;

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        Sprite.initUnitext();
        gsm = new GameStateManager();
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render()
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
        batch.end();
    }

    @Override
    public void dispose()
    {
        gsm.dispose();
        batch.dispose();
        Sprite.disposeFont();
        System.out.println("GAME DISPOSED");
    }
}
