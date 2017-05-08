package com.example._10hin.mysql_jdbc_blob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertByteArray extends InsertWithJDBC {

    private byte[] data;

    public InsertByteArray(final Connection connection) {
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
        this.data = data;
    }

    public void setData(final InputStream data) {
        Error thrownError = null;
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            int b;
            while (-1 != (b = data.read())) {
                byteOut.write(b);
            }
            this.data = byteOut.toByteArray();
        } catch (IOException e) {
            thrownError = new InternalError(e);
            throw thrownError;
        } finally {
            try {
                data.close();
            } catch (IOException e) {
                if (thrownError != null) {
                    thrownError.addSuppressed(e);
                } else {
                    throw new InternalError(e);
                }
            }
        }
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
            statement.setBytes(1, this.data);
            statement.execute();
        } catch (SQLException e) {
            throw new InternalError(e);
        }
    }

}