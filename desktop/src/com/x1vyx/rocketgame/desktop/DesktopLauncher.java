package com.x1vyx.rocketgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.x1vyx.rocketgame.tools.RocketGame;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 120 * 4;
        config.height = 240 * 4;
        config.title = RocketGame.TITLE;
        new LwjglApplication(new RocketGame(), config);
    }
}
