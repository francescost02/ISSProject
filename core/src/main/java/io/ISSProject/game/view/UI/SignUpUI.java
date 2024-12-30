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

public class SignUpUI extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private UserManager userManager;
    private final ScreenController controller;
    private Label notificationLabel;

    public SignUpUI(ScreenController controller) {
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

    public void createUI(){
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

        //table.add(backButton).pad(10).top().left().row();

        Table contentTable = new Table();
        Container<Table> container = new Container<>(contentTable);
        container.setBackground(createColorBackground(new Color(0.3f, 0.3f, 0.3f, 0.9f)));
        /*
        container.setActor(table);
        // Dimensioni fisse o che si adattano alla schermata? Può dare problemi con diverse risoluzioni?
        container.setSize(600, 400);
        container.setPosition((Gdx.graphics.getWidth() - container.getWidth()) / 2, (Gdx.graphics.getHeight() - container.getHeight()) / 2);
        container.setBackground(createColorBackground(new Color(Color.GRAY)));




        container.fillX();
        container.fillY();

        stage.addActor(container);

         */

        Label titleLabel = new Label("REGISTRAZIONE", skin);
        titleLabel.setFontScale(2.0f);

        Label usernameLabel = new Label("Inserisci il tuo username:", skin);
        usernameLabel.setFontScale(1.5f);

        TextField usernameField = new TextField("", skin); // Campo di input per l'username
        usernameField.setScale(1.5f);

        TextButton registerButton = new TextButton("Registrati", skin); // Pulsante di registrazione
        registerButton.getLabel().setFontScale(1.5f);

        //Layout della tabella principale
        mainTable.add(backButton).pad(20).top().left().expandX().row();
        mainTable.add(container).expand().fill().pad(50);

        // Layout del contenuto
        contentTable.add(titleLabel).pad(30).row();
        contentTable.add(usernameLabel).pad(20).row();
        contentTable.add(usernameField).width(400).height(60).pad(20).row();
        contentTable.add(registerButton).width(300).height(70).pad(30).row();

/*
        table.add(usernameLabel).padBottom(10).row(); // Aggiungi l'etichetta per l'username
        table.add(usernameField).width(200).padBottom(10).row(); // Aggiungi il campo username
        table.add(registerButton).padBottom(10).row(); // Aggiungi il pulsante di registrazione

 */

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                if(username.trim().isEmpty()){
                    showError("Username non valido, riprova");
                }
                controller.submitRegistration(username);
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

    private Drawable createColorBackground(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();
        return drawable;
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}
