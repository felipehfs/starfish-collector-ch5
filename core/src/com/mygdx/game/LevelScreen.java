package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelScreen extends BaseScreen{

    private Turtle turtle;
    private boolean win;
    private Label starfishLabel;
    private DialogBox dialogBox;

    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("assets/water-border.jpg");
        ocean.setSize(1200, 900);
        BaseActor.setWorldBounds(ocean);

        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(100, 450, mainStage);
        new Starfish(200, 250, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);


        new Shark(600, 400, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        win = false;

        starfishLabel = new Label("Starfish left:", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);
        uiStage.addActor(starfishLabel);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("assets/undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        uiStage.addActor(restartButton);

        restartButton.addListener((Event e) -> {
           if (!(e instanceof InputEvent) || !(((InputEvent) e).getType().equals(InputEvent.Type.touchDown))) {
               return false;
           }
           StarfishCollectorCh3.setActiveScreen(new LevelScreen());
           return false;
        });

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();

        Sign sign1 = new Sign(20, 400, mainStage);
        sign1.setText("West Starfish Bay");

        Sign sign2 = new Sign(600, 300, mainStage);
        sign2.setText("East Starfish Bay");

        dialogBox = new DialogBox(0, 0, uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.row();
        uiTable.add(dialogBox).colspan(3);
    }

    @Override
    public void update(float deltaTime) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, "com.mygdx.game.Rock"))
            turtle.preventOverlap(rockActor);

        for (BaseActor starfishActor: BaseActor.getList(mainStage, "com.mygdx.game.Starfish")) {
            Starfish starfish = (Starfish) starfishActor;

            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();

                Whirlpool whirlpool = new Whirlpool(0, 0, mainStage);
                whirlpool.centerAtActor(starfish);
                whirlpool.setOpacity(0.25f);

            }
        }

        for (BaseActor actor:  BaseActor.getList(mainStage, "com.mygdx.game.Shark")) {
            Shark shark = (Shark) actor;
            if (turtle.overlaps(shark)) {
                turtle.remove();
                BaseActor gameOverMessage = new BaseActor(0, 0, uiStage);
                gameOverMessage.loadTexture("assets/game-over.png");
                gameOverMessage.centerAtPosition(300, 300);
                gameOverMessage.setOpacity(0);
                gameOverMessage.addAction(Actions.delay(1));
                gameOverMessage.addAction(Actions.after(Actions.fadeIn(1)));
            }
        }

        if (BaseActor.count(mainStage, "com.mygdx.game.Starfish") == 0  && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("assets/you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));

            BaseActor continueMessage = new BaseActor(0, 0, uiStage);
            continueMessage.loadTexture("assets/message-continue.png");
            continueMessage.centerAtPosition(400, 100);
            continueMessage.setOpacity(0);
            continueMessage.addAction(Actions.delay(1));
            continueMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.C) && win) {
            StarfishCollectorCh3.setActiveScreen(new Level2Screen());
        }

        starfishLabel.setText("Starfish left: " + BaseActor.count(mainStage, "com.mygdx.game.Starfish"));


        for(BaseActor signActor: BaseActor.getList(mainStage, "com.mygdx.game.Sign")) {
            Sign sign = (Sign) signActor;

            turtle.preventOverlap(sign);
            boolean nearby = turtle.isWithinDistance(4, sign);
            if (nearby && !sign.isViewing()) {
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if (sign.isViewing() && !nearby) {
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }
    }
}
