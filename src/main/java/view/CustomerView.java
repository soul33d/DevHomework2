package view;

import controller.EntityController;
import model.Customer;

public class CustomerView extends View {

    private EntityController<Customer> controller;

    public CustomerView(TerminalHelper terminalHelper) {
        super(terminalHelper);
    }

    public CustomerView(TerminalHelper terminalHelper, EntityController<Customer> controller) {
        super(terminalHelper);
        this.controller = controller;
    }

    @Override
    protected void printMenu() {

    }

    @Override
    protected void selectMenuAction() {

    }
}
