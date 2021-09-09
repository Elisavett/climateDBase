package net.company.orders.databases;

import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.model.Entities.Sensor;
import net.company.orders.model.ValuesFromBase;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

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

    public static List<ValuesFromBase> getDbData(PhysicalQuantity physicalQuantity) {
        List<Sensor> sensorList = physicalQuantity.getSensors();
        List<ValuesFromBase> obsPointValues = new ArrayList<>();
        String valueUint = physicalQuantity.getUnit();
        for (Sensor sensor : sensorList) {
            ValuesFromBase obsPointValue = new ValuesFromBase(sensor.getMeasuringInstrument().getObservationPoint());
            String dbName = sensor.getSensor_DB();
            int connectionNum;
            if (dbName.equals("apik3")) connectionNum = 1;
            else connectionNum = 2;
            double value = 0.;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = getConnection(connectionNum)) {

                    PreparedStatement statement = conn.prepareStatement("SELECT `" + sensor.getSensor_db_key() + "` FROM `" + sensor.getSensor_table() + "` order by `time` desc");
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

    public static Map<Date, Double> countWeekTemperatures(int period, Sensor sensor) {
        Map<Date, Double> map2002 = new TreeMap<>();
        //Время сейчас отнимает количество секунд в неделе
        long ut = (Instant.now().getEpochSecond() - 604800L);
        String dbName = sensor.getSensor_DB();
        int connectionNum;
        if (dbName.equals("apik3")) connectionNum = 1;
        else connectionNum = 2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection(connectionNum)) {

                PreparedStatement statement = conn.prepareStatement(
                        "SELECT t.time, `" + sensor.getSensor_db_key() +
                                "` FROM `" + sensor.getSensor_table() +
                                "` as t where t.time >= "+ ut +" and MOD(t.time, " + period + ") = 0" +
                                " order by t.time desc");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    map2002.put(new java.util.Date((long) resultSet.getLong(1) * 1000), (Double) resultSet.getDouble(2));
                }
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        return map2002;
    }
}
