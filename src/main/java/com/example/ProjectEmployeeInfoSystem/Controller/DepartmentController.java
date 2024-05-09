package com.example.ProjectEmployeeInfoSystem.Controller;


import com.example.ProjectEmployeeInfoSystem.Entity.Department;
import com.example.ProjectEmployeeInfoSystem.Entity.Employee;
import com.example.ProjectEmployeeInfoSystem.Repository.DepartmentRepo;
import com.example.ProjectEmployeeInfoSystem.Repository.EmployeeRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    //Adding a new Department details
    public void addDepartment(Department department){
        if(department != null){
            departmentRepo.save(department);
        }
        
    }

    // Assign a HOD(Employee Id) in Department
    public void setDepartmentHod(int empId,int deptId){
        Department department = departmentRepo.findById(deptId).orElseThrow(() -> new IllegalArgumentException("Department not found"));
        Employee hod = employeeRepo.findById(empId).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        department.setHod(hod);
        departmentRepo.save(department);
    }

    //Get One Department details by Id
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Department getDepartmentById(@PathVariable int id){
        return departmentRepo.findById(id).get();
    }

    //Get all Department details
    @GetMapping("/all")
    public List<Department> getAllDepartments(){
        List<Department> departments = departmentRepo.findAll();
        return departments;
    }
}


