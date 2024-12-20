package io.ISSProject.game.model.userManagment;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class User implements Json.Serializable {
    private String username;

    //Questo costruttore serve per la deserializzazione dal file json
    public User(){

    }

    public User(String username){

        this.username = username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return new String(this.username);
    }



    @Override
    public String toString(){
        return "Username: " + this.getUsername() + "\n";
    }


    @Override
    public void write(Json json) {
        json.writeValue("Username" , this.getUsername());

    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        this.username = jsonValue.getString("Username");
    }


    @Override
    public boolean equals(Object obj) {
        User usr = (User) obj;
        if(!(this.username.toLowerCase().equals(usr.username.toLowerCase()))){
            return false;
        }
        return true;
    }

//
//    public static void main(String[] args) {
//        User usr1 = new User("ciccio");
//        User usr2 = new User("Ciccio");
//
//        System.out.println("usr1 = usr2 ? " + (usr1.equals(usr2) ? "sono uguali" : "sono diversi"));
//    }
}
