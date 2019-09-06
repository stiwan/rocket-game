package com.x1vyx.game.objects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.background.Background;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Randomer;
import com.x1vyx.game.tools.Sprite;

public class Paint extends GameObject
{

    public Paint()
    {
        type = 4;
        sprite = new Sprite("paint.png", 10, position = new Vector2(0, 0)).setCycleTime(0.5f);
        genNewPos();
    }

    @Override
    public boolean update(float dt)
    {
        sprite.update(dt);
        position.y -= Ship.getV();
        if (position.y <= 0)
            genNewPos();

        return true;
    }

    public void reset()
    {
        genNewPos();
    }

    private void genNewPos()
    {
        position.x = Randomer.getX();
        position.y = Randomer.getY(6);
    }

    public void ok()
    {
        genNewPos();
    }

    public void setDemo()
    {
        position.x = 38;
        position.y = 78;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.render(sb, 0, true);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("PAINT DISPOSED");
    }
}
