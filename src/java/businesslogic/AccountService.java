/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;

/**
 *
 * @author awarsyle
 */
public class AccountService {
    
    public User checkLogin(String username, String password, String path) {
        User user;
        
        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);
            
            if (user.getPassword().equals(password)) {
                // successful login
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO,
                                "A user logged in: {0}", username);
                String email = user.getEmail();
                try {
                    // WebMailService.sendMail(email, "NotesKeepr Login", "Big brother is watching you!  Hi " + user.getFirstname(), false);
                    
                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", ((new java.util.Date()).toString()));
                    
                    try {
                        WebMailService.sendMail(email, "NotesKeepr Login", path + "/emailtemplates/login.html", contents);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return user;
            }
            
        } catch (NotesDBException ex) {
        }
        
        return null;
    }
    
    public boolean resetPassword(String email, String path, String url){
        
        UserService us = new UserService();
        
        User user;
        String uuid = UUID.randomUUID().toString();
        String link = url + "?uuid=" + uuid;
        UserDB userDB = new UserDB();
        
        try {
            user = userDB.getUserEmail(email);
            if(user == null){
                return false;
            }
            //setpassword into the database through entity class method
            user.setResetPasswordUUID(uuid);
            //update user password calling userService via userDB to presist
            us.update(user);
            HashMap<String,String> userdata = new HashMap<>();
            userdata.put("firstname", user.getFirstname());
            userdata.put("lastname", user.getLastname());
            userdata.put("username", user.getUsername());
            userdata.put("link",link);
            
            WebMailService.sendMail(email, "NotesKeeper password", path+"/emailtemplates/resetpassword.html", userdata);
            return true;
            
        } catch (NotesDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    public boolean changePassword (String uuid, String password){
        
            UserService us = new UserService();
            
            try {
                
            User user = us.getUUID(uuid);
            
            if(user==null){
                return false;
            } 
                user.setPassword(password);
                user.setResetPasswordUUID(null);
                us.update(user);
                return true;
            
        } catch (Exception ex) {
            return false;
        }
    }
}
