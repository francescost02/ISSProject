package io.ISSProject.game.controller.mediator;

public interface GameComponent {
    void setMediator(GameMediator mediator);
    void notify(String event,Object...data); //varargs, il metodo può accettare 0 o più parametri Object
}
