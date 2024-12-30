package io.ISSProject.game.model.userManagment;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import io.ISSProject.game.controller.InterfaceManager;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.userManagment.state.LoggedInState;
import io.ISSProject.game.model.userManagment.state.LoggingInState;
import io.ISSProject.game.model.userManagment.state.SignUpState;
import io.ISSProject.game.model.userManagment.state.UnregisteredState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements GameComponent {

    //mediatore tra i vari controller
    private GameMediator mediator;

    private static UserManager instance;
    private String filePath;
    private FileHandle fileHandle;
    private Json json;
    private List<User> users;
    private List<Observer> observers;

    private UserState currentState;
    private List<InterfaceManager> observersUI;

    @Override
    public void setMediator(GameMediator mediator){
        this.mediator = mediator;
    }

    @Override
    public void notify(String event, Object...data){
        if(mediator != null){
            mediator.notify(this, event, data);
        }
    }


    public UserManager(){

        this.filePath = "assets/json/users.json";
        fileHandle = new FileHandle(new File(filePath));

        if(!fileHandle.exists()){
            this.fileHandle.writeString("" , false);
            System.out.println("File json creato");
        }

        this.json = new Json();
        this.users = new ArrayList<>();
        this.observers = new ArrayList<>();

        observersUI = new ArrayList<>();
        currentState = new UnregisteredState(this);
    }

    public void selectRegistrationPath(){
        setState(new SignUpState(this, mediator));
    }

    public void selectLoginPath(){
        setState(new LoggingInState(this, mediator));
    }

    public void handleInput(String input){
        currentState.handleInput(input);
    }

    public UserState getState(){
        return currentState;
    }

    public void returnToUnregistered(){
        setState(new UnregisteredState(this));
    }

    public void setState(UserState newState){
        this.currentState = newState;
        notifyObserversUI();
    }

    public void addObserverUI(InterfaceManager observer){
        observersUI.add(observer);
    }

    public void removeObserverUI(InterfaceManager observer){
        observersUI.remove(observer);
    }

    public void notifyObserversUI(){
        for(InterfaceManager observer : observersUI){
            observer.update(this);
        }
    }

    // Osservatori per notificare file di log francesco
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Rimuovi un osservatore
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyUserAdded(User user){
        for(Observer obs : this.observers){
            obs.onUserAdded(user);
        }
    }

    public void notifyUserNotAdded(User user){
        for(Observer obs : observers){
            obs.onUserNotAdded(user);
        }
    }

    public void notifyUserRemoved(User user){
        for(Observer obs : observers){
            obs.onUserRemoved(user);
        }
    }

    public void notifyUserUpdated(String oldUsername , User user){
        for(Observer obs : this.observers){
            obs.onUserUpdated(oldUsername , user);
        }
    }

    public void notifyUserLogged(User user){
        for(Observer obs : observers){
            obs.onUserLogged(user);
        }
    }

    public boolean saveUsers(){

        if(this.fileHandle.exists()){
            //formattazione corretta per file json
            json.setOutputType(JsonWriter.OutputType.json);
            String jsonString = json.toJson(users);
            this.fileHandle.writeString(jsonString , false);
            return true;
        }
        return false;
    }


    public boolean registerNewUser(User newUser){
        this.readUsers();

        if(this.users.contains(newUser)){
            this.notifyUserNotAdded(newUser);
            return false;
        }else{
            this.users.add(newUser);
            this.saveUsers();
            this.notifyUserAdded(newUser);
            //eseguiamo automaticamente il login dopo la registrazione
            loginUser(newUser.getUsername());
            return true;
        }
    }

    public boolean loginUser(String username){
        User user = this.findUserByName(username);
        if(user == null){
            System.out.println("L'utente non e' registrato nel sistema");
            return false;
        }
        this.notifyUserLogged(user);
        setState(new LoggedInState(user, mediator));
        this.notify("USER_LOGGED_IN", user);
        System.out.println("accesso effettuato con successo");
        return true;
    }

    public void showAllUsers(){
        this.readUsers();
        for(User user : this.users){
            System.out.println(user);
        }
    }

    public void readUsers(){

            if(fileHandle.exists()){
                String jsStr = fileHandle.readString();
                users = json.fromJson(ArrayList.class , User.class , jsStr);
            }
        if (users == null) {
            System.out.println("Attenzione: `users` è ancora null. Inizializzazione forzata.");
            users = new ArrayList<>();
                if (users == null) {
                    System.out.println("Attenzione: `users` è ancora null. Inizializzazione forzata.");
                    users = new ArrayList<>();
                }
        }
    }

    public boolean deleteUser(String username){

        this.readUsers();
        for(User usr : this.users){
            if(usr.getUsername().equals(username)){
                this.users.remove(usr);
                this.saveUsers();
                this.notifyUserRemoved(usr);
                return true;
            }
        }
        return false;
    }

    /*Temporaneamente può essere modificato solamente il lo username*/
    public boolean updateUserData(User user , String newUsername){
        this.readUsers();

        for(User usr : this.users){
            if(usr.equals(user)){
                int index = this.users.indexOf(usr);
                this.users.get(index).setUsername(newUsername);
                this.saveUsers();
                this.notifyUserUpdated(user.getUsername() , usr);
                return true;
            }
        }

        return false;
    }

    public User findUserByName(String username){

        for(User usr : this.users){
            if(usr.getUsername().equals(username)){
                return usr;
            }
        }
        return null;
    }

    public static UserManager getInstance(){

        if(instance == null){
            instance = new UserManager();
            return instance;
        }
        return instance;
    }
}
