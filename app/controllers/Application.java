package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;



public class Application extends Controller {

    public static void index() {
        render();
    }


    public static void signup (){
        render();
    }

    public static void addUser(String username, String password){
        User usr = User.find("byName", username).first();
        if (usr == null ) {
            User.addUser(username,password);
            index();
        }
        else {
           signup();
        }
    }


    public static void deleteUser(String user){
        User usr = User.find("byName", user).first();
        usr.delete();
        index();
    }











}