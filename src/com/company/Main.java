package com.company;

import com.sun.jdi.connect.Connector;

import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/Notizbuch?user=root";

        Calendar calendar = Calendar.getInstance();
        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Database connected");

            Statement stmt = null;
            String query = "Select * from notiz";

            try {

                System.out.println("Geben sie eine neue Notiz ein: ");
                String nextNotiz = scanner.nextLine();

                String sql = "INSERT INTO notiz (date, notiz)" + " values (?, ?)";

                PreparedStatement preparedStmt = conn.prepareStatement(sql);
                preparedStmt.setDate (1, startDate);
                preparedStmt.setString (2, nextNotiz);
                System.out.println(preparedStmt.execute());

                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String date = rs.getString("date");
                    String notiz = rs.getString("notiz");
                    System.out.println(date + "\t" + notiz);
                }

            } catch (SQLException ex) {
                throw new Error("Problem ", ex);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Database connection closed");
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}
