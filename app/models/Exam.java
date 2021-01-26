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
       this.save();
       
   }

   public Exam addScore(double score){
       if (score != 0){
       this.score = score;
       this.course.updateParammeters(this); 
       this.save(); 
       return this;
       }
       else { return this; }
   }



   public Exam upDateParameters(String newname, double newscore){
       this.course.deleteExam(this);
       this.name = newname;
       this.addScore(newscore);
       return this;
   }
}
