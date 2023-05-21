package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;

public class TableUpdater {
    public static void truncateTable(String tableName) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Map<String, String> properties = yaml.load(new FileInputStream("src/test/resources/junit-platform-properties.yml"));

        String url = properties.get("url");
        String username = properties.get("username");
        String password = properties.get("password");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            String truncateQuery = "TRUNCATE TABLE " + tableName + " CASCADE" ;
            statement.executeUpdate(truncateQuery);
            System.out.println("Table truncated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initialiseTariffs(String tableName) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Map<String, String> properties = yaml.load(new FileInputStream("src/test/resources/junit-platform-properties.yml"));

        String url = properties.get("url");
        String username = properties.get("username");
        String password = properties.get("password");
        String query = new Scanner(new File("src/main/resources/tariffInitialisation.sql"))
                .useDelimiter("\\Z").next();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}