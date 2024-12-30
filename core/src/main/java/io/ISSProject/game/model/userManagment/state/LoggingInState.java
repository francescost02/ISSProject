package io.ISSProject.game.model.userManagment.state;

import com.badlogic.gdx.Screen;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.userManagment.User;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.model.userManagment.UserState;
import io.ISSProject.game.view.UI.LoggingInUI;

public class LoggingInState implements UserState {
    private UserManager userManager;
    private ScreenController controller;
    private GameMediator mediator;

    public LoggingInState(UserManager userManager, GameMediator mediator) {
        this.userManager = userManager;
        this.mediator=mediator;
    }

    @Override
    public Screen getUIScreen(ScreenController controller){
        this.controller = controller;
        return new LoggingInUI(controller);
    }

    @Override
    public void handleInput(String username) {
        if (isValidUsername(username)) {
            //User newUser = new User(username); se l'utente è registrato già c'è l'oggetto User
            //Delega la logica di business a UserManager e cambiamo stato solo se non restituisce null
            if (!userManager.loginUser(username)) {
                //User user = this.userManager.findUserByName(username);
                //userManager.setState(new LoggedInState(user, mediator));
                System.out.println("Login fallito");
            } else {
                System.out.println("Login effettuato");
            }
        }
    }

    private boolean isValidUsername(String username){
        return username != null && !username.trim().isEmpty();
    }

}
