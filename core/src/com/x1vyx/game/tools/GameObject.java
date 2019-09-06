package com.x1vyx.game.tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject
{
    protected Sprite sprite;
    protected Vector2 position;
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

    public Vector2 getPosition()
    {
        return position;
    }
}
