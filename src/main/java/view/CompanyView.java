package view;

import controller.EntityController;
import model.Company;

import java.util.List;

public class CompanyView extends EntityView<Company> {
    private static final int PRINT_ALL_KEY = 1;
    private static final int PRINT_COMPANY_KEY = 2;
    private static final int CREATE_KEY = 3;
    private static final int UPDATE_KEY = 4;
    private static final int DELETE_KEY = 5;
    private static final int DELETE_ALL_KEY = 6;
    private static final int BACK_TO_MAIN_MENU_KEY = 7;

    private boolean backToMainMenu = false;

    public CompanyView(MainView mainView, EntityController<Company> controller, TerminalHelper terminalHelper) {
        super(mainView, controller, terminalHelper);
    }

    @Override
    protected void printMenu() {
        System.out.printf("Press %d to print all companies\n", PRINT_ALL_KEY);
        System.out.printf("Press %d to print company by id\n", PRINT_COMPANY_KEY);
        System.out.printf("Press %d to create new company\n", CREATE_KEY);
        System.out.printf("Press %d to update company by id\n", UPDATE_KEY);
        System.out.printf("Press %d to delete company by id\n", DELETE_KEY);
        System.out.printf("Press %d to delete all companies\n", DELETE_ALL_KEY);
        System.out.printf("Press %d to back to main menu\n", BACK_TO_MAIN_MENU_KEY);
    }

    @Override
    protected void selectMenuAction() {
        int enteredInteger = terminalHelper.readIntFromInput();
        switch (enteredInteger) {
            case PRINT_ALL_KEY:
                printAll();
                break;
            case PRINT_COMPANY_KEY:
                printCompany();
                break;
            case CREATE_KEY:
                createCompany();
                break;
            case UPDATE_KEY:
                updateCompany();
                break;
            case DELETE_KEY:
                deleteCompany();
                break;
            case DELETE_ALL_KEY:
                deleteAllCompanies();
                break;
            case BACK_TO_MAIN_MENU_KEY:
                backToMainMenu = true;
                break;
        }
        if (!backToMainMenu) {
            printMenu();
            selectMenuAction();
        }
        backToMainMenu = false;
    }

    @Override
    protected void printAll() {
        List<Company> companies = controller.readAll();
        if (companies != null) {
            companies.forEach(System.out::println);
        }
    }

    private void printCompany() {
        int enteredInteger = terminalHelper.readIntFromInput("Enter id to print company details");
        Company company = controller.read(enteredInteger);
        System.out.println(company);
    }

    private void createCompany() {
        Company company = new Company();
        company.setName(terminalHelper.readStringFromInput("Enter company name"));
        
    }

    private void updateCompany() {

    }

    private void deleteCompany() {
        int enteredInteger = terminalHelper.readIntFromInput("Enter id to delete company");
        controller.delete(enteredInteger);
    }

    private void deleteAllCompanies() {
        String answer = terminalHelper.readStringFromInput
                ("Are you sure you want to delete all companies? y/n");
        switch (answer) {
            case "y":
                if (controller.deleteAll()) {
                    System.out.println("All companies successfully deleted.");
                }
                break;
            case "n":
                break;
            default:
                deleteAllCompanies();
                break;
        }
    }
}
