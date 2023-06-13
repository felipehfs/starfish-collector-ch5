package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends  BaseScreen{
    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("assets/water.jpg");
        ocean.setSize(800, 600);

        BaseActor title = new BaseActor(0, 0, uiStage);
        title.loadTexture("assets/starfish-collector.png");
        title.moveBy(0, 100);
       /* BaseActor start = new BaseActor(0, 0, mainStage);
        start.loadTexture("assets/message-start.png");
        start.centerAtPosition(400, 300);
        start.moveBy(0, -100);*/


        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
        uiStage.addActor(startButton);


        startButton.addListener(
                (Event e) -> {
                    if (!(e instanceof InputEvent) || !(((InputEvent) e).getType().equals(InputEvent.Type.touchDown))) {
                        return false;
                    }
                    StarfishCollectorCh3.setActiveScreen(new StoryScreen());
                    return false;
                }
        );

        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        quitButton.setPosition(500, 150);
        uiStage.addActor(quitButton);

        quitButton.addListener((Event e) -> {
            if (!(e instanceof InputEvent) || !
                    (((InputEvent) e).getType().equals(InputEvent.Type.touchDown)) ) {
                return false;
            }
            Gdx.app.exit();
            return false;
        });

        uiTable.add(title).colspan(2);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton);

    }

    @Override
    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            StarfishCollectorCh3.setActiveScreen(new StoryScreen());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            StarfishCollectorCh3.setActiveScreen(new StoryScreen());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
