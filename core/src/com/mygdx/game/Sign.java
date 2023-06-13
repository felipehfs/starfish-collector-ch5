package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sign extends BaseActor{
    private boolean viewing;
    private String text;
    public Sign(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("assets/sign.png");
        text = " ";
        viewing = false;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isViewing() {
        return viewing;
    }

    public void setViewing(boolean viewing) {
        this.viewing = viewing;
    }

    public String getText() {
        return text;
    }
}
