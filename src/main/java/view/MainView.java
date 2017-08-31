package view;

import controller.AppController;
import model.*;

import java.io.IOException;

public class MainView extends View {

    private static final int COMPANY_KEY = 1;
    private static final int CUSTOMER_KEY = 2;
    private static final int PROJECT_KEY = 3;
    private static final int DEVELOPER_KEY = 4;
    private static final int SKILL_KEY = 5;
    private static final int EXIT_KEY = 0;

    private AppController controller;

    private View companyView;
    private View customerView;
    private View projectView;
    private View developerView;
    private View skillView;

    public MainView() {
        super(new TerminalHelper(System.in));
        controller = new AppController();
        companyView = new CompanyView(terminalHelper, controller.getEntityController(Company.class));
        customerView = new CustomerView(terminalHelper, controller.getEntityController(Customer.class));
        projectView = new ProjectView(terminalHelper, controller.getEntityController(Project.class));
        developerView = new DeveloperView(terminalHelper, controller.getEntityController(Developer.class));
        skillView = new SkillView(terminalHelper, controller.getEntityController(Skill.class));
    }

    @Override
    public void printMenu() {
        printMenuItem(COMPANY_KEY, "company");
        printMenuItem(CUSTOMER_KEY, "customer");
        printMenuItem(PROJECT_KEY, "project");
        printMenuItem(DEVELOPER_KEY, "developer");
        printMenuItem(SKILL_KEY, "skill");
        System.out.printf("Press %d to exit.\n", EXIT_KEY);
    }

    private void printMenuItem(int actionKey, String menuName) {
        System.out.printf("Press %d to show %s menu.\n", actionKey, menuName);
    }

    @Override
    protected void selectMenuAction() {
        int enteredInteger = terminalHelper.readIntFromInput();
        switch (enteredInteger) {
            case COMPANY_KEY:
                companyView.execute();
                break;
            case CUSTOMER_KEY:
                customerView.execute();
                break;
            case PROJECT_KEY:
                projectView.execute();
                break;
            case DEVELOPER_KEY:
                developerView.execute();
                break;
            case SKILL_KEY:
                skillView.execute();
                break;
            case EXIT_KEY:
                exitApp();
                break;
            default:
                printNoActionMsg(enteredInteger);
                printMenu();
                selectMenuAction();
                break;
        }
        printMenu();
        selectMenuAction();
    }

    private void exitApp() {
        try {
            terminalHelper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.closeApp();
    }

    private void printNoActionMsg(int enteredInteger) {
        System.out.printf("There is no action for %d. Please press correct action key.\n", enteredInteger);
    }
}
