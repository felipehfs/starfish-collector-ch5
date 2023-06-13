package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Action;

public class SetTextAction extends Action {

    protected  String textToDisplay;

    public SetTextAction(String textToDisplay) {
        this.textToDisplay = textToDisplay;
    }

    public void setTextAction(String value) {
        textToDisplay = value;
    }
    @Override
    public boolean act(float delta) {
        DialogBox db = (DialogBox) target;
        db.setText(textToDisplay);
        return true;
    }
}
