package by.sands.vitebsktransport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper implements AutoCloseable {
    public static final int DEF_TIMEOUT = 30;
    public static final long WRONG_ID = -1L;
    Connection conn = null;

    public SqlHelper() {
        this("time_table.db");
    }

    public SqlHelper(String dbfile) {
        try {
            conn = openConnection(dbfile);
        } catch (Exception e) {
            System.err.println("Error connecting to DB. " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    protected Connection openConnection(String db) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:" + db);
        System.out.println("Opened database successfully");
        return c;
    }

    public long selectId(String sql) {
        try {
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(DEF_TIMEOUT);
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong("_id");
            }
        } catch (SQLException e) {
            System.out.println("Error updating: " + sql + ":");
            e.printStackTrace();
        }
        return WRONG_ID;
    }

    public boolean insert(String sql) {
        try {
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(DEF_TIMEOUT);
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating: " + sql + ":");
            e.printStackTrace();
        }
        return false;
    }

    public int selectCount(String sql) {
        try {
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(DEF_TIMEOUT);
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error updating: " + sql + ":");
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Closed database successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
