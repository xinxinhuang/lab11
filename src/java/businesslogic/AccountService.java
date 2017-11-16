/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author awarsyle
 */
public class AccountService {
    
    public User checkLogin(String username, String password) {
        User user;
        
        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);
            
            if (user.getPassword().equals(password)) {
                return user;
            }
            
        } catch (NotesDBException ex) {
        }
        
        return null;
    }
    
}
