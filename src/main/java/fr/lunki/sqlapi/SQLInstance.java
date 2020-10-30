package fr.lunki.sqlapi;

import org.bukkit.Bukkit;
import org.bukkit.command.defaults.BukkitCommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLInstance {

    private static SQLInstance instance;

    private Connection connection;

    private SQLInstance(String host,int port,String database,String username,String password){
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://"+host+":" +
                    port+"/"+database,username,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("Error loading the database" +
                    "check your config file");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void close(){
        try {
            getInstance().connection.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("Error closing the database");
        }
    }

    public static void createDatabaseLink(String host, int port, String database, String username, String password){
        instance = new SQLInstance(host,port,database,username,password);
    }

    public static SQLInstance getInstance() {
        if(instance == null){
            Bukkit.getConsoleSender().sendMessage("Error link to database not set");
        }
        return instance;
    }
}
