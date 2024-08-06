package com.x1vyx.rocketgame.tools;

public class QFunction
{
    private float a;
    private float b;
    private int t;

    public boolean isSwitching;

    public QFunction(float b)
    {
        a = -0.07f;
        this.b = b;
    }

    public float getNextV()
    {
        float v = a * t + b;
        if (v < 0)
        {
            isSwitching = false;
            return 0f;
        }
        t++;
        return v;
    }

    public void reset()
    {
        t = 0;
        isSwitching = true;
    }
}
