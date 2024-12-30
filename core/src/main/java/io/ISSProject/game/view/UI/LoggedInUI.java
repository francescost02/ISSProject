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
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.userManagment.User;

public class LoggedInUI extends ScreenAdapter implements GameComponent{
    private final Stage stage;
    private final Skin skin;
    private final ScreenController controller;
    private TextButton proceedButton;
    private GameMediator mediator;

    @Override
    public void setMediator(GameMediator mediator) {
        if (mediator == null){
            throw new IllegalArgumentException("Mediato non può essere nullo");
        }
        this.mediator = mediator; // Imposta il mediatore, se necessario
    }

    @Override
    public void notify(String event, Object... data) {
        if (mediator!=null){
            mediator.notify(this, event, data);
        } else {
            System.err.println("Errore: Mediato non inizializzato");
        }
    }

    // Costruttore
    public LoggedInUI(ScreenController controller,User user, GameMediator mediator) {
        setMediator(mediator);
        this.controller = controller;

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    // Metodo per creare la UI
    public void createUI() {
        stage.clear();
        Table table = new Table();
        table.setFillParent(true);

        Container<Table> container = new Container<>();
        container.setActor(table);
        container.setSize(600, 400);
        container.setPosition((Gdx.graphics.getWidth() - container.getWidth()) / 2, (Gdx.graphics.getHeight() - container.getHeight()) / 2);
        container.setBackground(createColorBackground(new Color(Color.GRAY)));
        container.fillX();
        container.fillY();

        proceedButton = new TextButton("Vai al Menu", skin);
        proceedButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mediator.notify( LoggedInUI.this,"USER_LOGGED_IN");
            }
                                  });

        container.setActor(table);
        stage.addActor(container);

        // Elementi base
        Label welcomeLabel = new Label("Benvenuto!", skin);
        Label infoLabel = new Label("Sei autenticato. Qui potrai accedere alle funzionalità disponibili.", skin);

        table.add(proceedButton).padTop(20).row();
        table.add(welcomeLabel).padBottom(15).row();
        table.add(infoLabel).padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

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
}
