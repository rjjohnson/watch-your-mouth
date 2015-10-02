package com.subtextgroup.mcp.wym;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class WatchYourMouth extends JavaPlugin implements Listener {

    
    @EventHandler(ignoreCancelled = true)
    public void handleChat(AsyncPlayerChatEvent event) {
        if(containsNasty(event.getMessage())) {
            event.setMessage(getReplacementMessage());
            event.getPlayer().sendMessage("Watch your mouth!");
        }
    }
    
    boolean containsNasty(String s) {
        if(s == null || s.isEmpty()) {
            return false;
        }
        s = s.toLowerCase();
        for(String word : badWords) {
            if(s.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    String getReplacementMessage() {
        return getConfig().getString("replacement", "My breath smells like cat food");
    }

    List<String> badWords = null;
    
    
    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Plugin)this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        badWords = (ArrayList<String>)getConfig().getList("badwords", new ArrayList<String>());
        
        getServer().getPluginManager().registerEvents(this, this);
        getServer().broadcastMessage("WatchYourMouth enabled!");
    }
    
    
}
