package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class Scene extends Actor {
    private ArrayList<SceneSegment> segmentsList;
    private int index;

    public Scene() {
        super();
        segmentsList = new ArrayList<SceneSegment>();
        index = -1;
    }


    public void addSegment(SceneSegment segment) {
        segmentsList.add(segment);
    }

    public void clearSegment() {
        segmentsList.clear();
    }

    public void start() {
        index = 0;
        segmentsList.get(index).start();
    }

    @Override
    public void act(float delta) {
        if (isSegmentFinished() && !isLastSegment()) {
            loadNextSegment();
        }
    }

    public boolean isSceneFinished() {
        return isSegmentFinished() && isLastSegment();
    }

    public void loadNextSegment() {
        if (isLastSegment()) {
            return;
        }

        segmentsList.get(index).finish();
        index++;
        segmentsList.get(index).start();
    }

    private boolean isSegmentFinished() {
        return segmentsList.get(index).isFinished();
    }

    private boolean isLastSegment() {
        return index >= segmentsList.size() - 1;
    }


}
