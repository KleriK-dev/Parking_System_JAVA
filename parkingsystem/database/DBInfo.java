package parkingsystem.database;

public interface DBInfo {
    String DB_NAME = "jdbc:mysql://localhost/parking_system";
    String ENCODING = "?useUnicode=yes&characterEncoding=UTF-8";
    String DB_NAME_WITH_ENCODING = DB_NAME + ENCODING;
    String USER = "parkingSystem";
    String PASSWORD = "1234";
}
