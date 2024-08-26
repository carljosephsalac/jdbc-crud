/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Salac
 */
public class DB {
    private static final String URL = "jdbc:mysql://localhost:4306/db_salac";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = ""; // Replace with your MySQL password
    
    public static void main(String[] args) {
        DB db = new DB();
        Connection connection = null;
        try {
            // Establish the connection
            connection = db.connect();
            
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        //db.create("Carlson", 1.27f);
        //db.update(8, "Carlson Johnson", 1.1f);
        db.delete(8);
        db.read();
    }
    
    // Method to establish a database connection
    private Connection connect() throws SQLException {
        Connection connection = null;
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void create(String name, float grade) {
        String query = "INSERT INTO students (name, grade) VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setFloat(2, grade);
            pstmt.executeUpdate();
            System.out.println("Record inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void read() {
        String query = "SELECT * FROM students";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Loop through the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float grade = rs.getFloat("grade");

                // Display the data
                System.out.println("ID: " + id + ", Name: " + name + ", Grade: " + grade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(int id, String newName, float newGrade) {
        String query = "UPDATE students SET name = ?, grade = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the parameters for the query
            pstmt.setString(1, newName);
            pstmt.setFloat(2, newGrade);
            pstmt.setInt(3, id);

            // Execute the update
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No record found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        String query = "DELETE FROM students WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the parameter for the query
            pstmt.setInt(1, id);

            // Execute the delete operation
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("No record found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
