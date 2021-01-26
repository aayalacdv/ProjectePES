package controllers;


import play.*;
import play.data.validation.Required;
import play.mvc.*;
 
import java.util.*;
 
import models.*;
 
@With(Secure.class)
public class Admin extends Controller {
    public static String username;
    public static String coursename;


    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byName", Security.connected()).first();
            username = user.name;
            List<Course> courses = user.courses;
            renderArgs.put("user", username);
            renderArgs.put("courses", courses);
        }
    }
 
    public static void index() {
        render();
    }


    public static void modPage(String course){

        Course crs = Course.find("byNameAndUser.name", course, username).first();
        render(crs);

    }


    public static void updateCourseName (String name, @Required String curso){
        Course crs = Course.find("byNameAndUser.name", name, username).first();
        if(validation.hasErrors()){
            render("Admin/modPage.html",crs);
        }
        crs.updateName(curso);
        flash.success("Nombre cambiado con éxito");
        modPage(crs.name);
    }

    public static void updateExam( String name,String oldname, String newname, double score){
        Course crs = Course.find("byNameAndUser.name", name, username).first();

        for (int i = 0; i < crs.exams.size(); i++) {
                Exam exm = crs.exams.get(i);
                if (exm.name.equals(oldname)) {
                    if(isNullOrEmpty(newname)){
                        crs.exams.get(i).upDateParameters(oldname, score);
                    }
                    else{
                        crs.exams.get(i).upDateParameters(newname, score);
                    }
                }
        }

        modPage(crs.name);
    }


    public static void addExam(String name, @Required String examen, int percentage, double score ){
        Course crs = Course.find("byNameAndUser.name", name, username).first();
        if(validation.hasErrors()){
            render("Admin/modPage.html",crs);
        }
        Exam exam = new Exam(examen,crs,percentage);
        if (score != 0 ) exam.score = score;
        crs.addExam(exam);
        flash.success("Examen añadido con éxito");
        modPage(crs.name);
    }

    public static void addCourse(@Required String name){
        if(validation.hasErrors()){
            render("Admin/index.html");
        }
        User user = User.find("byName",username).first();
        Course course = new Course(name, user);
        user.addCourse(course);
        flash.success("Operación Completada!");
        index();

    }

    public static void deleteCourse( String name){
        Course crs = Course.find("byNameAndUser.name", name, username).first();
        User user = User.find("byName",username).first();
        user.deleteCourse(crs);
        index();

    }

    public static void deleteExam(String name,String oldname){
        Course crs = Course.find("byNameAndUser.name", name, username).first();

        for (int i = 0; i < crs.exams.size(); i++) {
            Exam exm = crs.exams.get(i);
            if (exm.name.equals(oldname)) {
                crs.deleteExam(exm);
                crs.exams.get(i).delete();


            }
        }

        modPage(crs.name);

    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
















}