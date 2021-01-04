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
    public double finalGrade; 
    public double currentGrade; 
    public double meanToPass; 
    public int percentageLeft;


    @ManyToOne
    public User user; 
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    public List<Exam> exams; 


    public Course (String name, User user){
        this.name = name;
        this.finalGrade = 0; 
        this.currentGrade = 0; 
        this.percentageLeft = 100; 
        
        this.user = user; 
        this.exams = new ArrayList<Exam>(); 
    }


    public Course addExam ( Exam exam){
        this.exams.add(exam); 
        this.save(); 
        return this; 
    }


    public Course updateParammeters (Exam exam){
       if (this.percentageLeft >= exam.percentage ){
            this.currentGrade =  this.currentGrade + exam.score * exam.percentage / 100;
            this.percentageLeft = this.percentageLeft - exam.percentage; 
            if(this.currentGrade < 5 && this.percentageLeft != 0){
                this.meanToPass = (5 - this.currentGrade)*100.0/this.percentageLeft ;
                this.meanToPass = Math.round(this.meanToPass * 100.0)/100.0;  
            }
            else {
                this.meanToPass = 0; 
            }
            if (this.percentageLeft == 0){
                this.finalGrade = this.currentGrade; 
                this.meanToPass = 0; 
            }
       }
       else {
           return this; 
       }
        this.save();
        return this; 
    }


    
}
