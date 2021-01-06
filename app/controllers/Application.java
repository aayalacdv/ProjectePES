package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;



public class Application extends Controller {

    public static void index() {
        User axel = User.find("byName", "Axel").first(); 
        List<Course> courses = axel.courses; 
        render(axel,courses); 
    }

}