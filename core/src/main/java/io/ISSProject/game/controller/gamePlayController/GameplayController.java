package io.ISSProject.game.controller.gamePlayController;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.view.ClueNotification;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Diary.DetectiveDiary;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.settingsMenuModel.AudioManager;
import io.ISSProject.game.view.DiaryUI;
import io.ISSProject.game.view.GameplayView.*;

import static java.lang.Thread.sleep;


public class GameplayController implements GameComponent {
    private static GameplayController instance;
    private final GameContext gameContext;
    private AbstractSceneView gameView;
    private final DetectiveDiary diary;
    private GameMediator mediator;
    private AudioManager audioManager;


    public GameplayController() {
        this.gameContext = GameContext.getInstance();
        this.diary = DetectiveDiary.getInstance();
    }


    // Metodo per impostare la view dopo la creazione del controller
    public void setScreen(AbstractSceneView gameView) {
        this.gameView = gameView;
        this.audioManager = AudioManager.getInstance();
        // Assicuriamoci che il pulsante pausa abbia un listener
        addPauseListener();
        addNextListener();
        addDiaryListener();
    }

    public void updateViewForScene(Scene scene) {
        switch (scene.getName()) {
            case "Intro" -> this.gameView = new IntroView(GameplayController.this);
            case "Brother's Bedroom" -> this.gameView = new BrotherBedroomView(GameplayController.this);
            case "Brother's Living Room" ->
                this.gameView = new BrotherLivingRoomView(GameplayController.this); // Cambia view
            case "Ferramenta" -> this.gameView = new StoreView(GameplayController.this); // Cambia view per Ferramenta
            case "Warehouse" -> this.gameView = new WarehouseView(GameplayController.this);
            case "Call" -> this.gameView = new CallView(GameplayController.this);
            case "Before Abandoned Shelter" -> this.gameView = new BeforeAbandonedShelterView(GameplayController.this);
            case "Abandoned Shelter" -> this.gameView = new AbandonedShelterView(GameplayController.this);
            case "Trap Door" -> this.gameView = new TrapDoorView(GameplayController.this);
            case "Secret Room 1" -> this.gameView = new SecretRoom1View(GameplayController.this);
            case "Ex Boss' Hiddenout 1" -> this.gameView = new ExBossHiddenoutView1(GameplayController.this);
            case "Studio" -> this.gameView = new StudioView(GameplayController.this);
            case "Ex Boss' Hiddenout 2" -> this.gameView = new ExBossHiddenoutView2(GameplayController.this);
            /*case "Buttons" -> this.gameView = new ButtonsView(GameplayController.this);

             */
            case "Secret Room 2" -> this.gameView = new SecretRoom2View(GameplayController.this);
            case "Before Boss' Hiddenout" -> this.gameView = new BeforeBossHiddenoutView();
            case "Final" -> this.gameView = new FinalView(GameplayController.this);
            case "Victory View"-> this.gameView = new VictoryView(GameplayController.this, mediator);
            case "Defeat View"-> this.gameView = new DefeatView(GameplayController.this, mediator);
        }

        addPauseListener();
        addNextListener();
        addDiaryListener();
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
                if(mediator != null && gameContext.getCurrentScene().isCompleted()) {
                    mediator.notify(GameplayController.this, "GO_TO_NEXT_SCENE");
                } else {
                    audioManager.playClickSound2();
                    mediator.notify(GameplayController.this, "SCENE_NOT_COMPLETED");
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

        // Imposta il mediator se l'oggetto è un PuzzleObject
        if (object instanceof PuzzleObject puzzleObj) {
            audioManager.playClickSound();
            puzzleObj.setMediator(mediator);
            checkSceneCompletion();
        }

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
                    audioManager.playClickSound();
                    System.out.println (gameContext.getCurrentScene());
                    /*
                    if (gameContext.getCurrentScene().getName().equals("Final")) {
                        System.out.println(gameContext.getCurrentScene());
                        //mediator.notify(GameplayController.this, "FINAL_CHOICE");
                    }
                     */
                    mediator.notify(GameplayController.this, "CLUE_FOUND", clue);
                    checkSceneCompletion();
                }
                else if (gameContext.getCurrentScene().getName().equals("Final")){
                    audioManager.playClickSound();
                    ClueNotification.showChoice(
                        gameView.getSkin(),
                        gameView.getStage(),
                        GameplayController.this
                    );
                }
                else
                    audioManager.playClickSound2();
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

    private boolean checkPlayerChoice() {
        // Verifica l'ultima scelta memorizzata nel contesto di gioco
        return gameContext.getCurrentScene().getName().equals("Final") &&
            gameContext.getPlayerFinalChoice().equals("Boss");
    }

    public static void resetInstance() {
        if (instance != null) {
            instance.gameView = null;
            instance.audioManager = AudioManager.getInstance(); // Ricarica l'istanza
            instance.audioManager.reloadSounds();
        }
        instance = null;
        System.out.println("GameplayController: Istanza resettata.");
    }


}
