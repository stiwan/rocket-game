package com.x1vyx.rocketgame.tools;

/**
 * Basically a class that contains 4 int values.
 * It can be used as a box for an GameObject or just
 * to transfer a point or a double touch.
 * Use sprite.w, sprite.x etc. instead this if possible
 */
public class Field
{
    public float x, y, w, h;

    public Field(float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Field(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean contains(int px, int py)
    {
        return (px > x && px < (x + w) && py > y && py < (y + h));
    }
}
