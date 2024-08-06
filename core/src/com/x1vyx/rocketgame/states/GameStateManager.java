package com.x1vyx.rocketgame.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.tools.RocketGame;

import java.util.Stack;

public class GameStateManager
{
    private Stack<State> states;

    public GameStateManager()
    {
        states = new Stack<State>();
    }

    public void push(State state)
    {
        states.push(state);
    }

    public void pop()
    {
        states.pop();
    }

    /* Change state */
    void set(State state)
    {
        states.push(state);
    }

    public void update(float dt)
    {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb)
    {
        states.peek().render(sb);
    }

    public void dispose()
    {
        states.pop().dispose();
        if(states.empty() && (RocketGame.DEV_MODE))
            System.out.println("~STATE STACKS EMPTY~");
        if (RocketGame.DEV_MODE)
            System.out.println("STATE DISPOSED");
    }

}
