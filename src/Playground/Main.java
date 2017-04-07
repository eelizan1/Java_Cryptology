package Playground;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.sql.*;
/**
 * Created by eeliz_000 on 4/3/2017.
 * Adds a new user to the userdb while hashing their password
 */
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\MyFiles\\School\\Java Cryptology\\databases\\testdb.db");
//            // statement to not commit any changes to db
//            conn.setAutoCommit(false);
            // create a table
            Statement statement = conn.createStatement();
            // creates and instance statement
            statement.execute("CREATE TABLE IF NOT EXISTS usersTest (name TEXT, username TEXT, password TEXT)");

            System.out.println("Register");
            System.out.println("What is your name?");
            String name = scan.nextLine();
            System.out.println("What is your username?");
            String username = scan.nextLine();
            System.out.println("What is your password?");
            String password = scan.nextLine();

            String sql = "INSERT INTO usersTest (name, username, password)" +
                                "VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, hash(password));
            ps.execute();

            System.out.println("Login");
            System.out.println("What is your username?");
            String usernamelog = scan.nextLine();
            System.out.println("What is your password?");
            String passwordlog = scan.nextLine();
            String passwordloghash = hash(passwordlog);

            statement.execute("SELECT * FROM usersTest WHERE name='Sarah'");
            ResultSet results = statement.getResultSet();
            String comparepass = results.getString("password");
            String compareusername = results.getString("username");
            if(passwordloghash.equals(comparepass) && usernamelog.equals(compareusername)) {
                System.out.println("Logged in");
            } else {
                System.out.println("Not Logged in");
            }
            results.close();

            // close connection
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static String hash(String password) {
        String hashpass = password;
        String verify = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] ret = md.digest(hashpass.getBytes("UTF-8"));
            verify = Hex.encodeHexString(ret);

        } catch (Exception e) {
            System.out.println("Error hashing");
        }
        return verify;
    }
}
