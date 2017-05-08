package com.example._10hin.mysql_jdbc_blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertWithJDBC implements AutoCloseable {

    protected Connection connection;

    private static final String INSERT = "INSERT INTO raw_data (data) VALUES (?)";

    protected InsertWithJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new InternalError(e);
        } finally {
            this.connection = null;
        }
    }

    protected PreparedStatement prepareInsert() {
        try {
            return this.connection.prepareStatement(INSERT);
        } catch (SQLException e) {
            throw new InternalError(e);
        }
    }

}
