package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.transaction.TransactionScoped;

// import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    public final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudent(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalAccessError("Email is taken");
        }
        studentRepository.save(student);
       
    }

    public void deleteStudentByID(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("Student with id-" + studentId + " does not exist.");
        }
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
        .orElseThrow(()-> new IllegalStateException("Student with id-" + studentId + " does not exist."));
        
        boolean nameIsValied = (name != null && name.length() > 0);
        boolean nameIsSame = Objects.equals(student.getName(), name);

        if((nameIsValied && !nameIsSame)==true){
            student.setName(name);
        }

        boolean emailIsValied = (email != null && email.length() > 0);
        boolean emailIsSame = Objects.equals(student.getEmail(), email);

        if((!emailIsSame && emailIsValied)==true){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalAccessError("Email is taken");
            }
            student.setEmail(email);
        }
    }
}
