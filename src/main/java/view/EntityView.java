package view;

import controller.EntityController;

public abstract class EntityView<T> extends View {
    protected EntityController<T> controller;

    public EntityView(EntityController<T> controller, TerminalHelper terminalHelper) {
        super(terminalHelper);
        this.controller = controller;
    }
    protected abstract void printAll();
}
