package controller;

import model.Student;
import model.StudentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.IStudentSevice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentSevice studentSevice;
    @Autowired
    private Environment environment;
    @GetMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("list",studentSevice.findAll());
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("create","s",new StudentForm());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView createNewStudent(@ModelAttribute StudentForm s) throws IOException{
        ModelAndView modelAndView = new ModelAndView("create");
        MultipartFile[] files = s.getAvatar();
        List<String> avatar = new ArrayList<>();
        String folder = environment.getProperty("file_upload").toString();
        System.out.println(folder);
        for (MultipartFile file: files){
            avatar.add(file.getOriginalFilename());
            FileCopyUtils.copy(file.getBytes(),new File(folder+ file.getOriginalFilename()));
        }
        Student s1 = new Student(s.getName(),s.getAddress(),avatar);
        studentSevice.save(s1);
        modelAndView.addObject("s",new StudentForm());
        modelAndView.addObject("mess","Thanh công");
        return modelAndView;
    }
}
