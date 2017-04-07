package testDB;

import java.sql.*;

/**
 * Created by eeliz_000 on 4/3/2017.
 */
public class DB {
    public static void main(String[] args) {
        // connect to a database
        // id no db is made, it will automatically be created
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\MyFiles\\School\\Java Cryptology\\databases\\testdb.db");
//            // statement to not commit any changes to db
//            conn.setAutoCommit(false);
            // create a table
            Statement statement = conn.createStatement();
            // creates and instance statement
            statement.execute("CREATE TABLE IF NOT EXISTS contacts (name TEXT, phone INTEGER, email TEXT)");
//            /*
//                INSERT DATA
//             */
////            statement.execute("INSERT INTO contacts (name, phone, email)" +
////                                    "VALUES('Mike', 7705461923, 'joe@gmail.com')");
////            statement.execute("INSERT INTO contacts (name, phone, email)" +
////                    "VALUES('Enrico', 6785174706, 'eelizanjr@gmail.com')");
////            statement.execute("INSERT INTO contacts (name, phone, email)" +
////                    "VALUES('James', 770667897, 'james@gmail.com')");
//            /*
//            UPDATE DATA
//             */
//            statement.execute("UPDATE contacts SET phone=678252679 WHERE name='James'");
//
//            /*
//            DELETE
//             */
//            statement.execute("DELETE FROM contacts WHERE name='James' ");

            /*
            FETCHING DATA
             */
            statement.execute("SELECT * FROM contacts");
            ResultSet results = statement.getResultSet();
            while(results.next()) {
                System.out.println(results.getString("name") + " " +
                                    results.getInt("phone") + " " +
                                        results.getString("email"));
            }
            results.close();

            // close connection
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
}
