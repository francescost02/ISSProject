package io.ISSProject.game.model.saveModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ISSProject.game.model.Scene;

import java.io.*;

public class FileManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveToFile(GameStateMemento memento, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(memento, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameStateMemento loadFromFile(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, GameStateMemento.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
