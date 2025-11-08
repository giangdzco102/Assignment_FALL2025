package model;

public class Division extends BaseModel {
    private String name;
    private Employee headDivision; // head_division_id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getHeadDivision() {
        return headDivision;
    }

    public void setHeadDivision(Employee headDivision) {
        this.headDivision = headDivision;
    }
}
