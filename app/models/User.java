package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model{
  
    @Required 
    public String name; 

    @Required
    @Password
    public String password; 

    public boolean isAdmin = false;


    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    public List<Course> courses;

    public String toString(){
        return name;
    }

    public User(String name, String password) {

        this.name = name;
        this.password = password;
        this.courses = new ArrayList<Course>(); 
    }


    // Mirar si existe el usuario
    public static User exists(String name, String password) {
        return User.find("byNameAndPassword", name, password).first();
    }

    // Borrar el usuario
    public static int deleteUser(User user) {
        user.delete();
        return 0;
  }

    //Agregar usuario nuevo
    public static void addUser(String name, String password){
         User usr = new User( name, password).save(); 
 
 }
    public User addCourse ( Course course ){
        this.courses.add(course); 
        this.save(); 
        return this; 
    }

    public User deleteCourse(Course course){

        for (Course crs : this.courses){
            if(crs == course){ crs.delete();}
        }
        return this;
    }
}
