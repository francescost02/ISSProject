
package io.ISSProject.game.model;

import io.ISSProject.game.model.InteractiveObject;

public class SceneObject extends InteractiveObject {
    public SceneObject(String tooltipText, String dialogText) {
        super(tooltipText, dialogText);
    }

    @Override
    public void interact() {
        System.out.println("Hai interagito con un oggetto di scena" );
    }
}
