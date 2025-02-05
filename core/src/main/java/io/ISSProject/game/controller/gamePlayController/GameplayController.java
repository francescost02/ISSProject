package io.ISSProject.game.controller.gamePlayController;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Diary.DetectiveDiary;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.view.DiaryUI;
import io.ISSProject.game.view.GameplayView.AbstractSceneView;
import io.ISSProject.game.view.GameplayView.BrotherLivingRoomView;
import io.ISSProject.game.view.GameplayView.StoreView;

import static java.lang.Thread.sleep;


public class GameplayController implements GameComponent {
    private static GameplayController instance;
    private final GameContext gameContext;
    //private final BrotherLivingRoomView gameView;
    private AbstractSceneView gameView;
    //private PauseView overlayView;
    private final DetectiveDiary diary;
    private GameMediator mediator;
    //private Viewport sharedViewport = new ScreenViewport();

    public GameplayController() {
        this.gameContext = GameContext.getInstance();
        //this.gameView = new BrotherLivingRoomView(GameplayController.this);
       // this.gameView = gameView;
        this.diary = DetectiveDiary.getInstance();
    }


    // Metodo per impostare la view dopo la creazione del controller
    public void setScreen(AbstractSceneView gameView) {
        this.gameView = gameView;

        // Assicuriamoci che il pulsante pausa abbia un listener
        addPauseListener();
        addNextListener();
        addDiaryListener();
    }

    public void updateViewForScene(Scene scene) {
        // Crea una nuova view specifica per la scena corrente
        if (scene.getName().equals("Brother's Living Room")) {
            this.gameView = new BrotherLivingRoomView(GameplayController.this); // Cambia view
        } else if (scene.getName().equals("Ferramenta")) {
            this.gameView = new StoreView(GameplayController.this); // Cambia view per Ferramenta
        } else {
            //continua
        }

        addPauseListener();  // aggiungere il listener anche dopo il cambio
        addNextListener();
    }

    public static synchronized GameplayController getInstance() {
        if (instance == null) {
            instance = new GameplayController();
            System.out.println("GameContext: Istanza creata.");
        }
        return instance;
    }

    private void addPauseListener() {
        gameView.getPauseButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Pulsante Pause cliccato");
                if (mediator != null) {
                    mediator.notify(GameplayController.this, "OPEN_PAUSE_MENU");
                }
            }
        });
    }

    private void addNextListener() {
        gameView.getNextButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Pulsante Next cliccato");
                if (mediator != null && gameContext.getCurrentScene().isCompleted() ) {
                    mediator.notify(GameplayController.this, "GO_TO_NEXT_SCENE");
                }
            }
        });
    }

    private void addDiaryListener() {
        gameView.getDiaryButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Pulsante Diario cliccato");
                openDiary();
            }
        });
    }

    public void openDiary() {
        DiaryUI diaryWindow = gameView.getDiaryWindow();
        diaryWindow.setVisible(true);
        float x = (gameView.getStage().getWidth() - gameView.getDiaryWindow().getWidth()) / 2;
        float y = (gameView.getStage().getHeight() - gameView.getDiaryWindow().getHeight()) / 2;
        gameView.getDiaryWindow().setPosition(x, y);

    }


    public Actor createInteractiveArea(InteractiveObject object) {
        Actor actor = new Actor();
        actor.setTouchable(Touchable.enabled);

        // Aggiungi tooltip
        TextTooltip tooltip = new TextTooltip(object.getTooltipText(), gameView.getSkin());
        tooltip.setInstant(true);
        actor.addListener(tooltip);

        // Aggiungi listener per il click
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Area cliccata: " + object.getTooltipText());
                gameView.getDialogWindow().updateText(object.getDialogText());

                // Interazione specifica
                object.interact();

                // Se l'oggetto è un indizio, aggiorna la scena
                if (object instanceof Clue clue) {
                    //clue.setFound(true); // Segna l'indizio come trovato
                    //System.out.println ("Found: " + clue.isFound());
                    mediator.notify(GameplayController.this, "CLUE_FOUND", clue);
                    checkSceneCompletion();

                }
            }
        });
        return actor;
    }


    // Metodo per controllare il completamento della scena
    public void checkSceneCompletion() {
        Scene currentScene = gameContext.getCurrentScene();
        if (currentScene != null && currentScene.isCompleted()) {
            System.out.println("Scena completata!");
            mediator.notify(this, "SCENE_COMPLETED", currentScene);
            //mediator.notify(this, "GO_TO_NEXT_SCENE");

        } else {
            System.out.println("La scena non è ancora completata.");
        }
    }

    public AbstractSceneView getScreen() {
        return gameView;
    } //metodo per ottenere la view corrente


    @Override
    public void setMediator(GameMediator mediator) {
        this.mediator = mediator;
    }

    public GameMediator getMediator() {
        return mediator;
    }

    @Override
    public void notify(String event, Object... data) {
        if (mediator != null) {
            mediator.notify(this, event, data);
        }
    }
}
