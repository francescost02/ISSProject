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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.ISSProject.game.controller.ScreenController;

/*
    Le classi concrete UI si limitano a definire la grafica, i listener dei bottoni
    chiamano il metodo update che chiama i metodi state per cambiare stato,
    in base allo stato chiama setScreen di quello stato. Tipo setScreen(userManager,getState)
 */

public class UnregisteredUI extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    //private UserManager userManager;
    private final ScreenController controller;

    //costruttore
    public UnregisteredUI(ScreenController controller) {
        this.controller = controller;

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        createUI();
    }


    public void createUI(){
        stage.clear();
        //Layout principale
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        //Aggiunta sfondo
        table.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("images/test_img.jpg"))));

        Label messageLabel = new Label("Benvenuto!", skin);
        Label subMessageLabel = new Label("Accedi o registrati per iniziare a giocare.", skin);
        TextButton loginButton = new TextButton("Accedi", skin);
        TextButton registerButton = new TextButton("Registrati", skin);

        table.center();
        table.add(messageLabel).padBottom(15).row();
        table.add(subMessageLabel).padBottom(20).row();
        table.add(loginButton).width(200).height(50).padBottom(10).row();
        table.add(registerButton).width(200).height(50);

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectRegistrationPath();
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectLoginPath();
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void dispose(){
        stage.dispose();
        skin.dispose();
    }

}
