package io.ISSProject.game.model;

import io.ISSProject.game.model.InteractiveObject;

public class SceneObject extends InteractiveObject {
    public SceneObject(String tooltipText, String dialogText, float x, float y, float width, float height) {
        super(tooltipText, dialogText);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void interact() {
        System.out.println("Hai interagito con un oggetto di scena" );
    }
}
