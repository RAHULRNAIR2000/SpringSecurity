package telusko.SpringSecurtiyLearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import telusko.SpringSecurtiyLearning.model.Student;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> student  = new ArrayList<>(List.of(
            new Student(1,"navin",60),
            new Student(2,"Rahul",64)
    ));

//    @GetMapping("/")
//    public void greet(){
//        System.out.println("Hellow world");
//    }

    @GetMapping("/home")
    public List<Student> getStudent(){
       return student;
    }
}
