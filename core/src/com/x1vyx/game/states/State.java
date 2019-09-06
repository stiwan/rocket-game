package com.x1vyx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.RocketGame;

import java.util.ArrayList;

public abstract class State
{
    OrthographicCamera cam;
    GameStateManager gsm;

    // Tools
    public static long timer = 0; // max 960 (16s)
    static int resolutionFactor = Gdx.graphics.getWidth() / 120; // 4 for Desktop (480x960) OR 12 for Android (1440x2880)
    static int direction;
    static protected ArrayList<GameObject> objects; // stuff that should be updated, rendered and disposed

    // Input
    int[] touchX = new int[2];
    int[] touchY = new int[2];

    State(GameStateManager gsm)
    {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, RocketGame.WIDTH, RocketGame.HEIGHT);

    }

    public abstract void updateInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

    public abstract void disposeForSwitch();
}
