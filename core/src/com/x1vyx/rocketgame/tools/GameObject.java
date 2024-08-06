package com.x1vyx.rocketgame.tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject
{
    protected Sprite sprite;
    public int type = 0; // used for switching to play state (some of them are for tips)
    // 1 -> ship
    // 2 -> background
    // 3 -> CG
    // 4 -> Paint
    // 5 -> torpedo

    public abstract boolean update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

    public Sprite getSprite()
    {
        return sprite;
    }
}
