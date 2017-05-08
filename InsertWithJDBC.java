

public class InsertWithJDBC  {

    private Connection connection;

    protected InsertWithJDBC(Connection connection) {
        this.connection = connection;
    }

    public void close() {
    }

}
