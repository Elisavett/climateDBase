package net.company.orders.databases;

import net.company.orders.model.Entities.ObservationPoint;
import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.model.Entities.Sensor;
import net.company.orders.model.TemperatureFromBase;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataBaseManager {
    public static Connection getConnection(int number) throws SQLException, IOException {

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("databases.properties"))) {
            props.load(in);
        }
        String url = props.getProperty("url" + number);
        String username = props.getProperty("username" + number);
        String password = props.getProperty("password" + number);

        return DriverManager.getConnection(url, username, password);
    }

    public static List<TemperatureFromBase> getDbData(PhysicalQuantity physicalQuantity) {
        List<Sensor> sensorList = physicalQuantity.getSensors();
        List<TemperatureFromBase> obsPointValues = new ArrayList<>();
        String valueUint = physicalQuantity.getUnit();
        for (Sensor sensor : sensorList) {
            TemperatureFromBase obsPointValue = new TemperatureFromBase(sensor.getMeasuringInstrument().getObservationPoint());
            String dbName = sensor.getSensor_DB();
            int connectionNum;
            if (dbName.equals("apik3")) connectionNum = 1;
            else connectionNum = 2;
            double value = 0.;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = getConnection(connectionNum)) {

                    PreparedStatement statement = conn.prepareStatement("SELECT `" + sensor.getSensor_num() + "` FROM `" + sensor.getSensor_table() + "` order by `time` desc");
                    statement.setMaxRows(1);
                    ResultSet resultSet = statement.executeQuery();
                    resultSet.next();

                    value = resultSet.getDouble(1);
                }
            } catch (Exception ex) {
                System.out.println("Connection failed...");

                System.out.println(ex);
            }

            obsPointValue.setMeasuredValue(Math.round(value) + valueUint);
            obsPointValues.add(obsPointValue);

        }
        return obsPointValues;
    }
}
