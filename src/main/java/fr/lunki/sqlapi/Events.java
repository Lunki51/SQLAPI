package fr.lunki.sqlapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Events implements Listener {

    @EventHandler
    public void playerConnectEvent(PlayerJoinEvent e){
        System.out.println("PLAYER ADDING DETECTED");
        ResultSet players = SQLAPI.select("SELECT * FROM Users");
        try {
            boolean already = false;
            while(players.next()){
                if(players.getString(1).equals(e.getPlayer().getUniqueId().toString()))already = true;
            }
            if(!already){
                System.out.println("ADDING ONE PLAYER TO DB");
                SQLAPI.edit("INSERT INTO Users VALUES(?)",new String[]{e.getPlayer().getUniqueId().toString()});
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
