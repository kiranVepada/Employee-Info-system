package com.example.ProjectEmployeeInfoSystem.Controller;


import com.example.ProjectEmployeeInfoSystem.Entity.Employee;
import com.example.ProjectEmployeeInfoSystem.Entity.Job;
import com.example.ProjectEmployeeInfoSystem.Entity.JobHistory;
import com.example.ProjectEmployeeInfoSystem.Repository.EmployeeRepo;
import com.example.ProjectEmployeeInfoSystem.Repository.JobHistoryRepo;
import com.example.ProjectEmployeeInfoSystem.Repository.JobRepo;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;














@RestController
@RequestMapping("/job-history")
public class JobHistoryController {
    @Autowired
    JobHistoryRepo jobHistoryRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    JobRepo jobRepo;

    //Adding a new Job History details
    @PostMapping("/add")
    public void addJobHistory(@RequestParam("job") int job,@RequestParam("emp") int emp ,@RequestBody JobHistory jobHistory){
        Job jobId = jobRepo.findById(job).get();
        Employee empId = employeeRepo.findById(emp).get();
        JobHistory history = new JobHistory();
        history.setJob(jobId);
        history.setEmployee(empId);
        history.setStartDate(jobHistory.getStartDate());
        history.setEndDate(jobHistory.getEndDate());

        jobHistoryRepo.save(history);
    }

    //Get all Job History details
    @GetMapping("/all")
    public List<JobHistory> getAllJobHistories(){
        List<JobHistory> histories = jobHistoryRepo.findAll();

        return histories;
    }

    public int getExperience(Employee emp){
        List<JobHistory> history = jobHistoryRepo.findAllByEmployee(emp);
        int years = 0;

        for (JobHistory h : history) {
            LocalDate endDate = (h.getEndDate()==null)?LocalDate.now():h.getEndDate();
            LocalDate startDate = h.getStartDate();
            if (startDate != null){
                Period period = Period.between(startDate, endDate);
                years += period.getYears();
            }
        }

        return years;
    }

    //Get One Job History by Employee
    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<JobHistory> getJobHistoryByEmployee(@PathVariable int id){
        Employee emp = employeeRepo.findById(id).get();
        List<JobHistory> history = jobHistoryRepo.findAllByEmployee(emp);
        
        return history;
    }

}

