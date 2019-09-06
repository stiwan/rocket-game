package com.x1vyx.game.tools;

public class QFunction
{
    // Default params
    private float a = -0.1f; // Stretch factor
    private float v0 = 7f; //10f;

    // Seconds: 2
    // v = 0 after 2s and s = 240

    private float v;
    private int t;
    public boolean isSwitching;

    public QFunction(/*float a)*/float v0)
    {
        /*this.a = a;*/
        this.v0 = v0;
    }

    public QFunction(){}

    public float getNextV()
    {
        if (v > 0) // -v
        {
            t++;
            return (v = a * t + v0);
        } else
        {
            isSwitching = false;
            return 0f;
        }
    }

    public void startAnimation()
    {
        t = 0;
        v = v0;
        isSwitching = true;
    }
}
