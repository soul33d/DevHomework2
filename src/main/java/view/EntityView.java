package view;

import controller.EntityController;

public abstract class EntityView<T> extends View {
    protected MainView mainView;
    protected EntityController<T> controller;

    public EntityView(MainView mainView, EntityController<T> controller, TerminalHelper terminalHelper) {
        super(terminalHelper);
        this.mainView = mainView;
        this.controller = controller;
    }
    protected abstract void printAll();
}
