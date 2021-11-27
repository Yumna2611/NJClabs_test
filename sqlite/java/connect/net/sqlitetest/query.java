package net.sqlitetest;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class query {
    /**
     * Connect to the moviesData.db database
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/moviesData.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the movies table
     */
    public void insert(String name, String actor, String actress, String director, String YearofRelease) {
        String sql = "INSERT INTO movies(name,actor,actress,director,YearofRelease) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, actor);
            pstmt.setString(3, actress);
            pstmt.setString(4, director);
            pstmt.setString(5, YearofRelease );
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*
     select all rows in the movies table
     */
    public void selectAll(){
        String sql = "SELECT * FROM movies";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getString("actor") + "\t" +
                                   rs.getString("actress") + "\t" +
                                   rs.getString("director") + "\t" +
                                   rs.getInt("YearofRelease"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void selectByActor(String actor_name) {
        String sql = "SELECT * FROM movies WHERE actor = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,actor_name);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("actor") + "\t" +
                        rs.getString("actress") + "\t" +
                        rs.getString("director") + "\t" +
                        rs.getString("YearofRelease"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        query data = new query();
        
        int choice;
        System.out.println("Options:\n1. Insert\n2. Show all\n3. Select by Actor\n4.Exit");
        Scanner in = new Scanner(System.in);
        do{
            System.out.print("Enter your choice: ");
            choice = in.nextInt();
            in.nextLine();
            switch(choice)
            {
                case 1: System.out.print("Enter movie's name: ");
                    String name = in.next();
                    System.out.print("\nEnter actor's name: ");
                    String actor = in.next();
                    System.out.print("\nEnter actress's name: ");
                    String actress = in.next();
                    System.out.print("\nEnter director's name: ");
                    String director = in.next();
                    System.out.print("\nEnter year of release: ");
                    String YearofRelease = in.next();
                    data.insert(name, actor, actress, director, YearofRelease);
                    break;
                case 2: data.selectAll();
                    break;
                case 3: System.out.print("\nEnter actor's name: ");
                    String actor_name=in.next();
                    data.selectByActor(actor_name);
                    break;
                case 4: return;
                default: System.out.println("Invalid choice");
            }
        }while(choice != 5);
    }
}

