package model;

import java.math.BigDecimal;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private BigDecimal cost;
    private List<Integer> companiesIds;
    private List<Integer> developersIds;
    private List<Integer> customersIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<Integer> getCompaniesIds() {
        return companiesIds;
    }

    public void setCompaniesIds(List<Integer> companiesIds) {
        this.companiesIds = companiesIds;
    }

    public List<Integer> getDevelopersIds() {
        return developersIds;
    }

    public void setDevelopersIds(List<Integer> developersIds) {
        this.developersIds = developersIds;
    }

    public List<Integer> getCustomersIds() {
        return customersIds;
    }

    public void setCustomersIds(List<Integer> customersIds) {
        this.customersIds = customersIds;
    }
}
