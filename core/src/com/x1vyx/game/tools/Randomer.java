package com.x1vyx.game.tools;

import java.util.Random;

public class Randomer   // TODO: test
{
    private static final int MARGIN = 5;

    private static Random rnd = new Random();

    private static int getInt(int min, int max)
    {
        return (int) (Math.random() * (max - min) + min);
    }

    public static int getInt(int max)
    {
        return (int) (Math.random() * max);
    }

    public static float getFloat()
    {
        return (float) Math.random();
    }

    public static int getY(int rarity)
    {
        if(rarity > 0)
            return getInt(RocketGame.HEIGHT) + RocketGame.HEIGHT * rarity;
        else
            return getInt(-RocketGame.HEIGHT) + RocketGame.HEIGHT * rarity;
    }

    public static int getX()
    {
        return getInt(MARGIN, RocketGame.WIDTH - MARGIN);
    }

    public static boolean getBoolean() { return rnd.nextBoolean(); }
}
