package io.ISSProject.game.view.saveMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SaveGameView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private Table saveTable;
    private TextButton backButton;
    private TextButton loadButton;
    private TextButton deleteButton;
    private List<String> fileList; // Lista dei file di salvataggio
    private ScrollPane fileScrollPane;
    private String username; // Campo per il nome utente

    public SaveGameView(String username) {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        this.username = username; // Imposta il nome utente corrente
        setupUI();
        populateFileList(username); // Popola la lista dei file per l'utente
    }

    /**
     * Restituisce lo stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Restituisce la tabella dei salvataggi.
     */
    public Table getSaveTable() {
        return saveTable;
    }

    /**
     * Restituisce il pulsante "Indietro".
     */
    public TextButton getBackButton() {
        return backButton;
    }

    /**
     * Restituisce il pulsante "Carica".
     */
    public TextButton getLoadButton() {
        return loadButton;
    }

    /**
     * Restituisce il pulsante "Elimina".
     */
    public TextButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * Restituisce il file selezionato.
     */
    public String getSelectedFile() {
        return fileList.getSelected(); // Restituisce il file selezionato
    }

    /**
     * Restituisce la lista dei file.
     */
    public List<String> getFileList() {
        return fileList;
    }

    /**
     * Restituisce il nome utente corrente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Popola la lista dei file di salvataggio per l'utente specifico.
     */
    public void populateFileList(String username) {
        FileHandle userDirectory = Gdx.files.local("saves/" + username);
        if (!userDirectory.exists()) {
            userDirectory.mkdirs(); // Crea la directory se non esiste
        }

        FileHandle[] files = userDirectory.list(); // Elenco dei file nella directory dell'utente
        Array<String> fileNames = new Array<>();

        // Itera sui file e aggiunge solo quelli con estensione .json
        for (FileHandle file : files) {
            if (file.extension().equals("json") && file.nameWithoutExtension() != null && !file.nameWithoutExtension().isEmpty()) {
                fileNames.add(file.name());
            }
        }

        // Se non ci sono file validi, svuota la lista
        if (fileNames.size == 0) {
            fileList.setItems(new Array<>()); // Svuota la lista
        } else {
            fileList.setItems(fileNames); // Imposta i file trovati
        }
    }

    /**
     * Configura l'interfaccia utente.
     */
    private void setupUI() {
        // Creazione del tavolo principale e aggiunta alla scena
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Creazione della tabella per i pulsanti di salvataggio
        saveTable = new Table(skin);

        // Creazione dei pulsanti
        backButton = new TextButton("Indietro", skin);
        loadButton = new TextButton("Carica", skin);
        deleteButton = new TextButton("Elimina", skin);

        fileList = new List<>(skin);
        fileScrollPane = new ScrollPane(fileList, skin);
        fileScrollPane.setScrollingDisabled(true, false); // Permette lo scroll verticale

        Label fileLabel = new Label("File di salvataggio:", skin);
        fileLabel.setFontScale(1.5f); // Aumenta la dimensione del testo
        saveTable.add(fileLabel).pad(10).row();
        saveTable.add(fileScrollPane).width(300).height(200).pad(10).row();

        // Impostazione dello sfondo
        rootTable.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("images/background-menu-tmp.jpg"))));

        // Aggiunta dei pulsanti e della tabella alla UI
        Label titleLabel = new Label("Salvataggi Disponibili", skin);
        titleLabel.setFontScale(2.0f); // Aumenta la dimensione del testo del titolo
        rootTable.add(titleLabel).pad(20).row();
        rootTable.add(saveTable).expand().fill().pad(20).row();

        // Creazione di una riga con i pulsanti
        Table buttonRow = new Table();
        buttonRow.add(loadButton).width(200).height(60).pad(10);
        buttonRow.add(deleteButton).width(200).height(60).pad(10);

        rootTable.add(buttonRow).padTop(40).padBottom(10).row(); // Posiziona i pulsanti più in alto
        rootTable.add(backButton).width(150).height(50).padTop(10);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15F, 0.15F, 0.2F, 1.0F);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
