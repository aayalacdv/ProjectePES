package models;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Course extends Model {
    public String name; 
    public int finalGrade; 
    public int currentGrade; 
    public int meanToPass; 


    @ManyToOne
    public User user; 
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    public List<Exam> exams; 


    public Course (String name, User user){
        this.name = name;
        this.currentGrade = 0; 
        this.finalGrade = 0; 
        this.currentGrade = 0; 
        this.user = user; 
        this.exams = new ArrayList<Exam>(); 
    }
}
