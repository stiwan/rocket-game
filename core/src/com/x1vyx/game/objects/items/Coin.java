package com.x1vyx.game.objects.items;

import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.states.PlayState;
import com.x1vyx.game.tools.Randomer;
import com.x1vyx.game.tools.Sprite;

public class Coin
{
    public float x, y;
    public int frame, w, h;
    private final int MAX_FRAMES = 6;

    Coin(Sprite sprite) // "package-private"
    {
        w = sprite.w;
        h = sprite.h;
        genNewPos();
    }

    public void update(float dt)
    {
        y -= Ship.getV();
        if (y <= 0)
            genNewPos();

        if (PlayState.timer % 4 == 0)
            frame++;
        if (frame >= MAX_FRAMES)
            frame = 0;
    }

    public void genNewPos()
    {
        x = Randomer.getX();
        y = Randomer.getY(1);
    }

}
