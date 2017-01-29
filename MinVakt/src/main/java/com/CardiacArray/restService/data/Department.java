package com.CardiacArray.restService.data;

public class Department {
    private int departmentId;
    private String departmentName;
    private int departmentPhone;

    public Department(int departmentId, String departmentName, int departmentPhone) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentPhone = departmentPhone;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentPhone() {
        return departmentPhone;
    }

    public void setDepartmentPhone(int departmentPhone) {
        this.departmentPhone = departmentPhone;
    }
}
