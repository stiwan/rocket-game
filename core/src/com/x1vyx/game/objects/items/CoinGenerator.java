package com.x1vyx.game.objects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Sprite;

public class CoinGenerator extends GameObject
{
    private final int COINS_LENGTH = 1;
    private Coin[] coins;

    public CoinGenerator()
    {
        type = 3;
        coins = new Coin[COINS_LENGTH];
        sprite = new Sprite("coin.png", 6, position = new Vector2(50, 50)).setCycleTime(0.5f);
        for (int i = 0; i < COINS_LENGTH; i++)
            coins[i] = new Coin(sprite);
    }

    public void reset()
    {
        for (int i = 0; i < COINS_LENGTH; i++)
            coins[i].genNewPos();
    }

    @Override
    public boolean update(float dt)
    {
        for (int i = 0; i < COINS_LENGTH; i++)
            coins[i].update(dt);
        sprite.update(dt); // position won't be applied
        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        for (int i = 0; i < COINS_LENGTH; i++)
            sprite.render(sb, coins[i].frame, coins[i].x, coins[i].y);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("COINS DISPOSED");
    }

    public void setDemo()
    {
        coins[0].x = 92;
        coins[0].y = 99;
    }

    public Coin[] getCoins()
    {
        return coins;
    }

}
