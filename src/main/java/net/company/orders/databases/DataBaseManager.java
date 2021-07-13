package net.company.orders.databases;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DataBaseManager {
    public static Connection getConnection(int number) throws SQLException, IOException {

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("databases.properties"))){
            props.load(in);
        }
        String url = props.getProperty("url" + number);
        String username = props.getProperty("username" + number);
        String password = props.getProperty("password" + number);

        return DriverManager.getConnection(url, username, password);
    }
    public static Double getDbData(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection(1)) {

                PreparedStatement statement = conn.prepareStatement("SELECT `2002` FROM `50000022` order by `time` desc");
                statement.setMaxRows(1);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();

                return resultSet.getDouble(1);
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        return -1.;
    }
}
