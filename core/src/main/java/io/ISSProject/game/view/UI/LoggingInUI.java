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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

        stage = new Stage(new ScreenViewport());
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
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        //Pulsante per tornare indietro
        TextButton backButton = new TextButton("Indietro", skin);
        backButton.getLabel().setFontScale(1.5f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goBackToUnregistered();
            }
        });

        Table contentTable = new Table();
        Container<Table> container = new Container<>(contentTable);
        container.setBackground(createColorBackground(new Color(0.3f, 0.3f, 0.3f, 0.9f)));
        container.pad(40);

        Label titleLabel = new Label("LOGIN", skin);
        titleLabel.setFontScale(2.0f);

        Label usernameLabel = new Label("Inserisci il tuo username:", skin);
        usernameLabel.setFontScale(1.5f);

        // Campo di input per l'username
        usernameField = new TextField("", skin); // Crea il TextField

        // Modifica lo stile per centrare il testo e ingrandire il font
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(usernameField.getStyle());

        // Imposta un nuovo font con dimensioni maggiori
        textFieldStyle.font.getData().setScale(1.5f); // Scala il font a dimensioni maggiori

        // Centra il testo
        usernameField.setAlignment(Align.center);

        // Applica il nuovo stile
        usernameField.setStyle(textFieldStyle);

        TextButton confirmButton = new TextButton("Conferma Username", skin);
        confirmButton.getLabel().setFontScale(1.5f);

        //Layout della tabella principale
        mainTable.add(backButton).pad(20).top().left().row();
        mainTable.add(container).center().pad(50);

        // Layout del contenuto
        contentTable.add(titleLabel).pad(30).row();
        contentTable.add(usernameLabel).pad(20).row();
        contentTable.add(usernameField).width(400).height(60).pad(20).row();
        contentTable.add(confirmButton).width(300).height(70).pad(30).row();

        // Listener per il pulsante di conferma username
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                if(username.trim().isEmpty()){
                    showError("Non sei ancora registrato, registrati per accedere");
                }

                controller.submitRegistration(username);
                //System.out.println("Username confermato: " + username);
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

    public Stage getStage(){
        return stage;
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
