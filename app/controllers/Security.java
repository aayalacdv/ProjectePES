package controllers;

import models.*; 


public class Security extends Secure.Security {
    static boolean authenticate(String name, String password) {
        return User.exists(name, password) != null;
    }
    

    static void onDisconnected(){
        Application.index(); 
    }

    static void onAuthenticated(){
        Admin.index();
    }
    

    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return User.find("byName",connected()).<User>first().isAdmin;
        }
        return false;
    }

}
