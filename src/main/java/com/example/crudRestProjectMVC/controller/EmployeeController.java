package com.example.crudRestProjectMVC.controller;

import com.example.crudRestProjectMVC.entity.Employee;
import com.example.crudRestProjectMVC.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeService empService;

    @Autowired
    public EmployeeController(EmployeeService theEmployeeService){
        empService = theEmployeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model theModel){
        // get all employees from the db
        List<Employee> retList = empService.findAllService();

        // add them to the spring model, so that they can be added to the thymeleaf view(html form) that is going to be returned
        theModel.addAttribute("listOfEmployees", retList);

        //return the view page
        return "list-employees";

    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        // create a model attribute(ie Employee object) to bind form data, ie we basically create an employee object and add it to the model. The data entered in the html form will then be linked to this added employee object.
        Employee theEmployee = new Employee();
        theModel.addAttribute("tempEmployee", theEmployee);

        // return the html form that takes info of the employee to be added
        return "employee-add-form";
    }

    @GetMapping("/showFormForUpdate")
    public String ShowFormForUpdate(@RequestParam("employeeId") int theId, Model theModel){
        // get the employee from the database using empService
        Employee theEmployee = empService.getByIdService(theId);

        //add this retrieved employee to model for prepopulating the form that is going to be shown
        theModel.addAttribute("obtEmployee", theEmployee);
        // note that I can add this obtained theEmployee to the model with the name tempEmployee as well, the model being used here is different from the model that was being used in the above showFormForUpdate method, new models are created locally in all of these functions, it isnt like our entire application is using one global common model.

        //return the employee-update-form
        return "employee-update-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("tempEmployee") Employee emp){  // whatever info was stored in tempStudent object(which was in the model), will now be stored in this emp object.
        // save the obtained emp objctc into the database using saveService of the ServiceImpl class
        empService.saveService(emp);

        //return the entire list of all employees. This returned list should now have this newly added employee
        //we dont directly return the "list-employees" list, instead we redirect the call to /employs/list. Anything can be done, but using this Post/Redirect/Get pattern is generally preffered.
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String deleteAnEmployee(@RequestParam("employeeId") int theId){
        empService.deleteService(theId);

        //after deletion return the entire updated list of employees
        return "redirect:/employees/list";

    }
}
