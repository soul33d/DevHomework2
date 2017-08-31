package view;

import controller.EntityController;
import model.Company;

public class CompanyView extends View {
    private EntityController<Company> controller;

    public CompanyView(TerminalHelper terminalHelper, EntityController<Company> controller) {
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
