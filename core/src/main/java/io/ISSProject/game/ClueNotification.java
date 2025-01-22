package io.ISSProject.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ClueNotification extends Dialog {
    public ClueNotification(String clueTitle, Skin skin, Stage stage) {
        super("Nuovo Indizio!", skin);
        text("Hai trovato un nuovo indizio: " + clueTitle);
        button("OK");

        setPosition(
            (stage.getWidth() - getWidth()) / 2,
            (stage.getHeight() - getHeight()) / 2
        );

        setupAnimations();
        show(stage);
    }

    private void setupAnimations(){
        getColor().a = 0;
        addAction(Actions.sequence(
            Actions.fadeIn(0.5f),
            Actions.delay(1.5f),
            //Actions.fadeOut(0.5f),
            Actions.removeActor()
        ));
    }
}


