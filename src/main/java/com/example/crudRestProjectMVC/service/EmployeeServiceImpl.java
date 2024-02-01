package com.example.crudRestProjectMVC.service;

import com.example.crudRestProjectMVC.dao.EmployeeRepository;
import com.example.crudRestProjectMVC.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository empRepo;

    public EmployeeServiceImpl(EmployeeRepository empInject) {
        empRepo = empInject;
    }

    @Override
    public List<Employee> findAllService() {
        return empRepo.findAll();
    }

    @Override
    public Employee saveService(Employee s) {
        return empRepo.save(s);
    }

    @Override
    public Employee getByIdService(int id) {
        Optional<Employee> result = empRepo.findById(id);
        Employee theEmployee = null;
        if(result.isPresent()){
            theEmployee = result.get();
        }
        return theEmployee;


    }

    @Override
    public void deleteService(int id) {
        empRepo.deleteById(id);
    }


    //these save, findAll, getById, deleteById, functions called using empRepo are provided by spring data JPA, names should be these only.
}

