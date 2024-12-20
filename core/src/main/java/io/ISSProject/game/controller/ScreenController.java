package io.ISSProject.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.view.MainGame;

public class ScreenController implements InterfaceManager {
    private final UserManager userManager;
    private final MainGame game;


    public ScreenController(MainGame game) {
        this.userManager = UserManager.getInstance();
        this.game = game;
        this.userManager.addObserverUI(this);
    }

    //metodi per la selezione dell'azione da fare dalla schermata iniziale
    public void selectRegistrationPath(){
        userManager.selectRegistrationPath();
    }

    public void selectLoginPath(){
        userManager.selectLoginPath();
    }

    public void submitRegistration(String username){
        userManager.handleInput(username);
    }


    @Override
    public void update(UserManager userManager){
        Screen screen = userManager.getState().getUIScreen(this);
        game.setScreen(screen);
    }

    public void goBackToUnregistered(){
        userManager.returnToUnregistered();
    }

    public void showNotification(String title, String message, Stage stage){
        Dialog dialog = new Dialog(title, new Skin(Gdx.files.internal("UI/uiskin.json")));
        dialog.text(message);
        dialog.button("OK"); // Aggiunge un pulsante OK
        dialog.setModal(true); // Impedisce l'interazione con elementi sottostanti
        dialog.setMovable(false); // Impedisce lo spostamento della finestra
        dialog.setPosition(
            (stage.getWidth() - dialog.getWidth()) / 2,
            (stage.getHeight() - dialog.getHeight()) / 2
        );
        dialog.show(stage);
    }

}
