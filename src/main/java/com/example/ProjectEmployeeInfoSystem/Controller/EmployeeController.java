package com.example.ProjectEmployeeInfoSystem.Controller;


import com.example.ProjectEmployeeInfoSystem.Entity.Department;
import com.example.ProjectEmployeeInfoSystem.Entity.Employee;
import com.example.ProjectEmployeeInfoSystem.Entity.Job;
import com.example.ProjectEmployeeInfoSystem.Repository.DepartmentRepo;
import com.example.ProjectEmployeeInfoSystem.Repository.EmployeeRepo;
import com.example.ProjectEmployeeInfoSystem.Repository.JobRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





























@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    JobHistoryController jobHistoryController;

    //Adding a new Employee details
    public void addEmployee(Employee employee) {
        if(employee != null){
            employeeRepo.save(employee);
        }
        
    }

    //Get a Employee details by Id And this method is only accessible to ADMIN role
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee getEmployeeById(@PathVariable int id) {
        Employee employee = employeeRepo.findById(id).get();
        return employee;
    }

    //Get all Employee details
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();

        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
        return employees;
    }

    //Get all Employee details By Job
    @GetMapping("/job/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getAllEmployeeByJob(@PathVariable int id) {
        Job job = jobRepo.findById(id).get();
        List<Employee> employees = employeeRepo.findAllByJob(job);

        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }

        return employees;
    }

    //Get all Employee details By Department
    @GetMapping("/department/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getAllEmployeeByDept(@PathVariable int id) {
        Department dept = departmentRepo.findById(id).get();
        List<Employee> emp = employeeRepo.findAllByDepartment(dept);

        for (Employee employee : emp) {
            System.out.println(employee.toString());
        }
        return emp;
    }

    //Get all Employee details Where Employee name matches
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getEmployeeByName(@PathVariable String name) {
        List<Employee> emp = employeeRepo.findAllByEmpNameContainingIgnoreCase(name);

        for (Employee employee : emp) {
            System.out.println(employee.toString());
        }

        return emp;
    }

    //Get all Employee There Employee Salary between the given range
    @GetMapping("/salary")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getEmployeeSalaryBetweenRange(@RequestParam("min") float min,
            @RequestParam("max") float max) {
        List<Employee> emp = employeeRepo.findAllBySalaryBetween(min, max);

        for (Employee employee : emp) {
            System.out.println(employee.toString());
        }

        return emp;
    }

    //Get all Employee There Total experience is greater than or equal to given number
    @GetMapping("/experience")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getExperienceEmployee(@RequestParam("years") int years) {
        List<Employee> emp = employeeRepo.findAll();
        List<Employee> empList = new ArrayList<>();

        for (Employee employee : emp) {
            int ex = jobHistoryController.getExperience(employee);
            if (ex >= years) {
                empList.add(employee);
            }
        }

        return empList;
    }

    //Updating the Employee Salary
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateEmployee(@PathVariable int id, @RequestBody Employee emp) {
        Employee employee = employeeRepo.findById(id).get();
        employee.setSalary(emp.getSalary());
        employeeRepo.save(employee);

        System.out.println(employee.toString());
    }

}
