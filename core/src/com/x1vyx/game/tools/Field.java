package com.x1vyx.game.tools;

public class Field
{
    public int x, y, w, h;

    public Field(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean contains(int px, int py)
    {
        return (px > x && px < (x + w) && py > y && py < (y + h));
    }
}
