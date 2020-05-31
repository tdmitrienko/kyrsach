package model;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;

/**
 * Класс для работы с базой данных
 */
public class DBConnect implements DAO{
    /**
     * Переменная: подключение драйвера базы данных
     */
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    /**
     * Переменная: для подключения к базе данных
     */
    private static final String DB_CONNECTION ="jdbc:mysql://localhost:3306/BuildingConstruction" ;
    /**
     * Переменная: пользователь базы данных
     */
    private static final String DB_USER = "root";
    /**
     * Переменная: пароль от базы данных
     */
    private static final String DB_PASSWORD = "1234";


    /**
     * метод для создания соединения
     * @return
     */
    //создание соединения
    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("хуйня");
        }
        return dbConnection;
    }


    /**
     * метод для создания базы данных
     * @throws SQLException
     */
    // создание БД
    public static void createDbUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        String createTableSQL = "CREATE TABLE buildconstruction ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(50) NOT NULL, "
                + "PRIMARY KEY (ID) "
                + ")";

        String createTableSQL1 = "CREATE TABLE Mark ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "NAME VARCHAR(50) NOT NULL, "
                + "PRIMARY KEY (ID) "
                + ")";

        String createTableSQL2 = "CREATE TABLE builmaterial ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "ID_MARK INT NOT NULL references Mark(ID), "
                +"Id_buildconstruction int not null references buildconstruction(ID),"
                + "DIFFUSION DOUBLE NOT NULL, "
                + "NAME VARCHAR(100) NOT NULL, "
                + "PRIMARY KEY (ID) "
                + ")";


        String createTableSQL4 = "CREATE TABLE chemicalsubstance ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(50) NOT NULL, "
                + "Emission DOUBLE NOT NULL, "
                + "PDK DOUBLE NOT NULL, "
                + "PRIMARY KEY (ID) "
                + ")";

        String createTableSQL5 = "CREATE TABLE ChemicalSubstance_BuildMaterial ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "ID_ChemicalSubstance INT NOT NULL references chemicalsubstance(ID), "
                + "ID_BuildMaterial INT NOT NULL references builmaterial(ID), "
                + "PRIMARY KEY (ID) "
                + ")";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(createTableSQL);
            statement.execute(createTableSQL1);
            statement.execute(createTableSQL2);
            statement.execute(createTableSQL4);
            statement.execute(createTableSQL5);
            System.out.println("Table создана!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    /**
     * метод для выполнения запросов к базе данных
     * @param query
     * @return
     * @throws SQLException
     */
    // выполнение запроса к БД с получением данных
    public static ResultSet dbExecuteQuery(String query) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try {
            dbConnection=getDBConnection();
            System.out.println("Select statement: " + query + "\n");
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(query);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return crs;
    }

    /**
     * метод для обновления базы данных
     * @param sqlStmt
     * @throws SQLException
     */
    // обновление БД
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException{
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection=getDBConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    @Override
    public Connection dbConnect() {
        return getDBConnection();
    }

    @Override
    public void dbDisconnect() throws SQLException {
        getDBConnection().close();
    }

    @Override
    public ResultSet dbExecuteQueryDao(String queryStmt) throws SQLException {
        return dbExecuteQuery(queryStmt);
    }

    @Override
    public void dbExecuteUpdateDao(String sqlStmt) throws SQLException {
        dbExecuteUpdate(sqlStmt);
    }

    @Override
    public void createDbUserTableDao() throws SQLException {
        createDbUserTable();
    }

}
