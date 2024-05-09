package com.example.ProjectEmployeeInfoSystem.Controller;


import com.example.ProjectEmployeeInfoSystem.Entity.Job;
import com.example.ProjectEmployeeInfoSystem.Repository.JobRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    JobRepo jobRepo;

    //adding a new job row
    public void addJob(Job job) {
        if(job!=null){
            jobRepo.save(job);
        }
    }

    //Get a job details by job ID
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Job getJobById(@PathVariable int id) {
        return jobRepo.findById(id).get();
    }

    //Get all job details
    @GetMapping("/all")
    public List<Job> getAllJobs() {
        List<Job> jobs = jobRepo.findAll();

        for (Job job : jobs) {
            System.out.println(job.toString());
        }

        return jobs;
    }
}
