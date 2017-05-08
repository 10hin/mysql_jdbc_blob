package com.example._10hin.mysql_jdbc_blob;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertInputStream extends InsertWithJDBC {

    private InputStream data;

    public InsertInputStream(final Connection connection) {
        super(connection);
        if (connection == null) {
            throw new NullPointerException();
        }
        try {
            if (connection.isClosed()) {
                throw new InternalError("connection already closed");
            }
        } catch (SQLException e) {
            throw new InternalError(e);
        }
    }

    public void setData(final byte[] data) {
        if (data == null) {
            throw new NullPointerException();
        }

        ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
        this.data = byteIn;
    }

    public void setData(final InputStream data) {
        if (data == null) {
            throw new NullPointerException();
        }

        this.data = data;
    }

    public void execute() {
        // check
        if (this.data == null) {
            throw new NullPointerException();
        }
        try {
            if (this.connection.isClosed()) {
                throw new InternalError("connection already closed");
            }
        } catch (SQLException e) {
            throw new InternalError(e);
        }

        PreparedStatement statement = prepareInsert();
        try {
            statement.setBinaryStream(1, this.data);
            statement.execute();
        } catch (SQLException e) {
            throw new InternalError(e);
        }
    }

}