/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gian.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * @author Antonella
 */
public class GenerateUtils {

    public String generateCode() {
        String text = "";
        String possible = "0123456789";

        for (int i = 0; i < 8; i++) {
            text += possible.charAt((int) Math.floor(Math.random() * possible.length()));
        }
        return text;
    }

    public String generatePassword() {
        
        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 8; i++) {
            text += possible.charAt((int) Math.floor(Math.random() * possible.length()));
        }
        return text;
    }
    
    public String generateUsername(String name){
    
        String text = name;
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 4; i++) {
            text += possible.charAt((int) Math.floor(Math.random() * possible.length()));
        }
        return text;
    }
    
    public String hashMD5(String password) throws NoSuchAlgorithmException{
    
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        //Add password bytes to digest
        md.update(password.getBytes());
        //Get the hash's bytes 
        byte[] bytes = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        return sb.toString();
    
    }

}
