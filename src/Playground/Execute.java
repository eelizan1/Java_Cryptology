package Playground;

/*
 * Created by eeliz_000 on 4/7/2017.
 */
import PBKDF2.PBKDF2;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Arrays;
import java.sql.*;
import java.util.*;
import java.io.*;
public class Execute {
    public static void main(String[] args) {
        try {
        Scanner scan = new Scanner(System.in);
        System.out.println("Login");
        System.out.println("Enter you username");
        String username = scan.nextLine();
        System.out.println("Enter you password");
        String password = scan.nextLine();

        Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\MyFiles\\School\\Java Cryptology\\databases\\testdb.db");
        Statement statement = conn.createStatement();

            statement.execute("SELECT * FROM usersTest WHERE name='Enrico'");
            ResultSet results = statement.getResultSet();
            String matchPassword = results.getString("password");
            String matchUsername = results.getString("username");
            if(password.equals(matchPassword) && username.equals(matchUsername)) {
                System.out.println("You are logged in");
            } else {
                System.out.println("You are not logged in");
            }
            results.close();
            // close connection
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    public static void hash(String password) {
//        String hashpass = password;
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\MyFiles\\School\\Java Cryptology\\databases\\testdb.db");
//            Statement statement = conn.createStatement();
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] ret = md.digest(hashpass.getBytes("UTF-8"));
//            String verify = Hex.encodeHexString(ret);
//            statement.execute("UPDATE contacts SET phone=678252679 WHERE name='James'");
//        } catch (Exception e) {
//            System.out.println("Error");
//        }
//    }
}
