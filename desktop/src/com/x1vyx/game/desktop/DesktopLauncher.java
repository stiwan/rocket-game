package com.x1vyx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.x1vyx.game.tools.RocketGame;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = RocketGame.WIDTH * 4;
        config.height = RocketGame.HEIGHT * 4;
        config.title = RocketGame.TITLE;
        new LwjglApplication(new RocketGame(), config);
    }
}
