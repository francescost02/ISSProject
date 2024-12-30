package io.ISSProject.game.view.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.model.userManagment.UserManager;

public class LoggingInUI extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private UserManager userManager;
    private TextField usernameField; // Campo per inserire l'username
    private ScreenController controller;

    public LoggingInUI(ScreenController controller) {
        this.controller = controller;

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public void createUI() {
        // Rimuovi attori esistenti per ricreare l'interfaccia
        stage.clear();

        // Crea la tabella per la UI
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton backButton = new TextButton("<", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goBackToUnregistered();
            }
        });

        table.add(backButton).pad(10).top().left().row();

        Container<Table> container = new Container<>();
        container.setActor(table);
        //dimensoni fisse o che si adattano alla schermata? Può dare problemi con diverse risoluzioni?
        container.setSize(600, 400);
        container.setPosition((Gdx.graphics.getWidth() - container.getWidth()) / 2, (Gdx.graphics.getHeight() - container.getHeight()) / 2);
        //container.pad(20); avendo dimensioni fisse non influisce per ora
        container.setBackground(createColorBackground(new Color(Color.GRAY)));
        //fillX occupa tutta la larghezza disponibile, ha senso se utilizziamo dimensioni fisse
        container.fillX();
        container.fillY();

        stage.addActor(container);

        Label usernameLabel = new Label("Inserisci il tuo username:", skin);
        usernameField = new TextField("", skin); // Campo di input per l'username
        TextButton confirmButton = new TextButton("Conferma Username", skin);

        // Aggiungi gli elementi alla tabella
        table.add(usernameLabel).padBottom(10).row();
        table.add(usernameField).width(200).padBottom(10).row(); // Aggiungi il campo username
        table.add(confirmButton).padBottom(10).row(); // Aggiungi il pulsante di conferma

        // Listener per il pulsante di conferma username
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                if(username.trim().isEmpty()){
                    showError("Non sei ancora registrato, registrati per accedere");
                }

                controller.submitRegistration(username);
                System.out.println("Username confermato: " + username);


            }
        });
    }

    public void showError(String message){
        controller.showNotification("Errore", message, stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private Drawable createColorBackground(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();
        return drawable;
    }
}
