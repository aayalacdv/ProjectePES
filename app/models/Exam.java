package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Exam extends Model{
   public String name;
   public double score; 
   public int percentage;  


   @ManyToOne
   public Course course;  


   public Exam(String name, Course course, int percentage ){
       this.name = name; 
       this.course = course; 
       this.percentage = percentage; 
       this.score = 0;  
       
   }

   public Exam addScore(double score){
       this.score = score;
       this.course.updateParammeters(this); 
       this.save(); 
       return this; 
   }
}
