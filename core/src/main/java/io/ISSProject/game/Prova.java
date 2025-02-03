private void setupInteractiveObjects() {
    Table interactiveLayer = new Table();
    Stack gameStack = new Stack();
    gameStack.add(new Image(backgroundTexture));
    gameStack.add(interactiveLayer);

    // Definizione degli oggetti interattivi
    List<InteractiveObject> objects = Arrays.asList(
        // Oggetti normali
        new InteractiveObject(
            "Una lampada vintage...",
            "Una lampada vintage...non credo mi possa aiutare nella risoluzione di questo caso."
        ),
        new InteractiveObject(
            "Un quadro di un famoso artista contemporaneo",
            "Un bellissimo quadro, ma a meno che mio fratello l'abbia rubato non credo mi servirà per risolvere il caso..."
        ),
        // Indizi
        new Clue(
            "Una busta...",
            "Una busta! Lasciata così sul divano...sembra contenere documenti importanti, vediamo un po'...\n\"*Qualcosa scritto nel documento*\""
        ),
        new Clue(
            "Un libro sulla magia...",
            "Un antico libro sulla magia...chissà se esiste qualche incantesimo per avere una giornata normale ogni tanto."
        )
    );

    // Coordinate e dimensioni degli oggetti
    Map<String, float[]> objectProperties = new HashMap<>();
    objectProperties.put("Una lampada vintage...", new float[]{599f, 127f, 40f, 260f});
    objectProperties.put("Un quadro di un famoso artista contemporaneo", new float[]{338f, 365f, 130f, 160f});
    objectProperties.put("Una busta...", new float[]{458f, 213f, 40f, 40f});
    objectProperties.put("Un libro sulla magia...", new float[]{110f, 331f, 10f, 50f});

    // Creazione e posizionamento degli attori
    for (InteractiveObject object : objects) {
        Actor actor = controller.createInteractiveArea(object);
        float[] props = objectProperties.get(object.getTooltipText());

        if (props != null) {
            actor.setPosition(
                props[0] / 800f * stage.getViewport().getWorldWidth(),
                props[1] / 600f * stage.getViewport().getWorldHeight() * 0.7f
            );
            actor.setSize(
                props[2] / 800f * stage.getViewport().getWorldWidth(),
                props[3] / 600f * stage.getViewport().getWorldHeight() * 0.7f
            );

            interactiveLayer.addActor(actor);

            // Aggiungi l'oggetto alla scena corrente se è un indizio
            if (object instanceof Clue) {
                GameContext.getInstance().getCurrentScene().addInteractiveObject(object);
            }
        }
    }

    gameArea.add(gameStack).expand().fill();
}
