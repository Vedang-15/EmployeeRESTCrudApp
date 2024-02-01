package com.example.crudRestProjectMVC.service;

import com.example.crudRestProjectMVC.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveService(Employee s);

    Employee getByIdService(int id);

    List<Employee> findAllService();

    void deleteService(int id);

}
