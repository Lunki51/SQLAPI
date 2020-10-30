package fr.lunki.sqlapi;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLAPI extends JavaPlugin {

    public SQLAPI() {
        super();
    }

    protected SQLAPI(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new Events(),this);
        SQLInstance.createDatabaseLink(config.getString("host"),config.getInt("port"),
                config.getString("database"),config.getString("username"),
                config.getString("password"));
        //SQLAPI.edit("DROP TABLE Users");
        SQLAPI.edit("CREATE TABLE IF NOT EXISTS Users(UUID VARCHAR(50) PRIMARY KEY);");
    }

    public static ResultSet select(String request){
        return select(request,new Object[0]);
    }

    public static ResultSet select(String request, Object[] args){
        try {
            Connection connection = SQLInstance.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(request);
            for(int i=1;i<args.length+1;i++){
                stmt.setObject(i,args[i-1]);
            }
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("Error executing a request");
            return null;
        }
    }

    public static void edit(String request){
        edit(request,new Object[0]);
    }

    public static void edit(String request,Object[] args){
        try{
            Connection connection = SQLInstance.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(request);
            for(int i=1;i<args.length+1;i++){
                stmt.setObject(i,args[i-1]);
            }
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        SQLInstance.close();
    }
}
