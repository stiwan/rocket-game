package com.x1vyx.rocketgame.tools;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.states.GameStateManager;
import com.x1vyx.rocketgame.states.MenuState;

public class RocketGame extends ApplicationAdapter
{
    public static boolean isWide;
    private static int pixelFactor = -1;
    private static int width = 120;
    private static int height = -1;
    public static final boolean DEV_MODE = false;

    public static final String TITLE = "Sandbox";

    /* Game */
    private GameStateManager gsm;
    private SpriteBatch batch;

    /* Config */
    public static boolean muted;
    public static boolean keepBoost = true;
    public static boolean boosting;

    @Override
    public void create()
    {
        batch = new SpriteBatch();

        // calculate pixel factor
        RocketGame.setPixelFactor(Gdx.graphics.getWidth() / width);
        RocketGame.setHeight(Gdx.graphics.getHeight() / pixelFactor);

        // Screen is 2:1 or wider
        isWide = ((float) height / (float) width > 1.8f);

        if(DEV_MODE)
            System.out.println("\n\n SCREEN WIDE: " + isWide + "\n\n");

        gsm = new GameStateManager();
        gsm.push(new MenuState(gsm));
        Sprite.initUnitext();
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
        if (RocketGame.DEV_MODE)
            System.out.println("GAME DISPOSED");
    }

    public static int getPixelFactor()
    {
        return pixelFactor;
    }

    public static void setPixelFactor(int pixelFactor)
    {
        RocketGame.pixelFactor = pixelFactor;
    }

    public static int getWidth()
    {
        return width;
    }

    public static void setWidth(int width)
    {
        RocketGame.width = width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static void setHeight(int height)
    {
        RocketGame.height = height;
    }
}
