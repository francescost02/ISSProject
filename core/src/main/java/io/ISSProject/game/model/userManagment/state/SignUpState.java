package io.ISSProject.game.model.userManagment.state;

import com.badlogic.gdx.Screen;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.userManagment.User;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.model.userManagment.UserState;
import io.ISSProject.game.view.UI.SignUpUI;

/*
    In fase di registrazione, nella schermata possiamo inserire l'username
    e registrare un nuovo utente, spunta un messaggio di regisrazione con successo
    cliccando un pulsante si passa alla schermata di inizio gioco.
 */

public class SignUpState implements UserState {
    private UserManager userManager;
    private GameMediator mediator;

    public SignUpState(UserManager userManager, GameMediator mediator){
        this.userManager = userManager;
        this.mediator=mediator;
    }
    @Override
    public void handleInput(String input){
        if( isValidUsername(input) ){
            User newUser = new User(input);
            if (userManager.registerNewUser(newUser)){
                userManager.setState(new LoggedInState(newUser,mediator));
            }
        }
    }

    @Override
    public Screen getUIScreen(ScreenController controller){
        return new SignUpUI(controller);
    }

    private boolean isValidUsername(String username){
        return username != null && !username.trim().isEmpty();
    }
}

