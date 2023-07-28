package application;

import db.DB;
import db.DBException;
import db.DBIntegrityException;

import java.sql.*;

public class Program {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            st = conn.createStatement();
            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
            int x = 1;
            if (x < 2) {
                throw new SQLException("Fake error");
            }
            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
            conn.commit();
            System.out.println("Rows 1: " + rows1);
            System.out.println("Rows 2: " + rows2);

        }
        catch (SQLException e) {
            try {
                conn.rollback();
                throw new DBException("Transaction rolled back: " + e.getMessage());
            } catch (SQLException ex) {
                throw new DBException("Error trying to rollback: " + ex.getMessage());
            }
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
