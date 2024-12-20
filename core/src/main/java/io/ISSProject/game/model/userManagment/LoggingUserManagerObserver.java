package io.ISSProject.game.model.userManagment;

import com.badlogic.gdx.files.FileHandle;

public class LoggingUserManagerObserver implements Observer{

    private String logFilePath = "assets/userLogFile.txt";
    private FileHandle logFile;
    private static int rowCounter = 0;

    public LoggingUserManagerObserver(){
        logFile = new FileHandle(logFilePath);
        if(!logFile.exists()){
            logFile.writeString("USERMANAGER LOG FILE: \n" , true);
            System.out.println("File creato con successo");
        }
    }



    @Override
    public void onUserAdded(User user) {
        this.logFile.writeString(this.rowCounter + "-" + "L'utente, con username: " + user.getUsername() + " è stato registrato.\n" , true);
        LoggingUserManagerObserver.rowCounter++;
    }

    @Override
    public void onUserNotAdded(User user) {
        this.logFile.writeString(this.rowCounter + "-" + "L'utente, con username: " + user.getUsername() + " non è stato registrato poichè già presente nel sistema.\n",true);
        LoggingUserManagerObserver.rowCounter++;
    }

    @Override
    public void onUserUpdated(String oldUsername , User user) {
        this.logFile.writeString(this.rowCounter + "-" + "L'utente, con username: " + oldUsername + " è stato modificato con: " + user.getUsername() + "\n" , true);
        LoggingUserManagerObserver.rowCounter++;
    }

    @Override
    public void onUserRemoved(User user) {
        this.logFile.writeString(this.rowCounter + "-" + "L'utente, con username: " + user.getUsername() + " è stato rimosso.\n" , true);
        LoggingUserManagerObserver.rowCounter++;
    }

    @Override
    public void onUserLogged(User user) {
        this.logFile.writeString(this.rowCounter + "-" + "L'utente, con username: " + user.getUsername() + " ha effettuato il login con successo.\n" , true);
        LoggingUserManagerObserver.rowCounter++;
    }

    public static void main(String[] args) {
        LoggingUserManagerObserver usrMngObs = new LoggingUserManagerObserver();
        //UserManager userManager = UserManager.getInstance();
        usrMngObs.onUserAdded(new User("ciccio"));
        usrMngObs.onUserLogged(new User("pippo"));
        usrMngObs.onUserUpdated("mario" , new User("marco"));

    }
}
