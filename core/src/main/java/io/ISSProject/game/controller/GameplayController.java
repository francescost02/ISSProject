package io.ISSProject.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.menuState.GameContext;
import io.ISSProject.game.model.Diary.DetectiveDiary;
import io.ISSProject.game.view.DiaryUI;
import io.ISSProject.game.view.GameplayView.BrotherLivingRoomView;

public class GameplayController implements GameComponent {
    private GameContext gameContext;
    private final BrotherLivingRoomView gameView;
    private GameMediator mediator;
    private final DetectiveDiary diary;

    public GameplayController(GameContext gameContext){
        this.gameContext = gameContext;
        this.gameView = new BrotherLivingRoomView(this);
        this.diary = DetectiveDiary.getInstance();
    }

    public void toggleDiary(){
        DiaryUI diaryWindow = gameView.getDiaryWindow();
        if (diaryWindow.isVisible()){
            diaryWindow.hide();
        } else {
            diaryWindow.show();
        }
    }
    public  Actor createInteractiveArea(String tooltipText, String dialogText, boolean isClue) {
        Actor actor = new Actor();
        actor.setTouchable(Touchable.enabled);
        actor.setDebug(true);
        // Aggiungi tooltip
        TextTooltip tooltip = new TextTooltip(tooltipText, gameView.getSkin());
        tooltip.setInstant(true);
        actor.addListener(tooltip);

        // Aggiungi listener per il click nel controller
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Area clicked " + tooltipText);
                gameView.getDialogWindow().updateText(dialogText);
                if (isClue){
                    diary.addEntry(tooltipText, dialogText);
                    mediator.notify(GameplayController.this, "CLUE_FOUND", tooltipText);
                }
            }
        });

        return actor;
    }

    public BrotherLivingRoomView getScreen(){
        return gameView;
    }

    @Override
    public void setMediator(GameMediator mediator){
        this.mediator = mediator;

    }

    @Override
    public void notify(String event, Object... data) {
        if (mediator != null) {
            mediator.notify(this, event, data);
        }
    }
}
