package view;

import controller.AppController;
import model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainView extends View {

    private static final int COMPANY_KEY = 1;
    private static final int CUSTOMER_KEY = 2;
    private static final int PROJECT_KEY = 3;
    private static final int DEVELOPER_KEY = 4;
    private static final int SKILL_KEY = 5;
    private static final int EXIT_KEY = 0;

    private AppController controller;
    private Map<Class, EntityView> viewMap;

    public MainView() {
        super(new TerminalHelper(System.in));
        controller = new AppController();
        viewMap = new HashMap<>();
        viewMap.put(Company.class,
                new CompanyView(this, controller.getEntityController(Company.class), terminalHelper));
        viewMap.put(Customer.class,
                new CustomerView(this, controller.getEntityController(Customer.class), terminalHelper));
        viewMap.put(Project.class,
                new ProjectView(this, controller.getEntityController(Project.class), terminalHelper));
        viewMap.put(Developer.class,
                new DeveloperView(this, controller.getEntityController(Developer.class), terminalHelper));
        viewMap.put(Skill.class,
                new SkillView(this, controller.getEntityController(Skill.class), terminalHelper));
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

    void printAll(Class clazz) {
        viewMap.get(clazz).printAll();
    }

    private void printMenuItem(int actionKey, String menuName) {
        System.out.printf("Press %d to show %s menu.\n", actionKey, menuName);
    }

    @Override
    protected void selectMenuAction() {
        int enteredInteger = terminalHelper.readIntFromInput();
        switch (enteredInteger) {
            case COMPANY_KEY:
                viewMap.get(Company.class).execute();
                break;
            case CUSTOMER_KEY:
                viewMap.get(Customer.class).execute();
                break;
            case PROJECT_KEY:
                viewMap.get(Project.class).execute();
                break;
            case DEVELOPER_KEY:
                viewMap.get(Developer.class).execute();
                break;
            case SKILL_KEY:
                viewMap.get(Skill.class).execute();
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
