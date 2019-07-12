package net.whispwriting.universenethers.tasks;

import net.whispwriting.universenethers.events.NetherPortalHandler;
import net.whispwriting.universenethers.UniverseNethers;
import net.whispwriting.universenethers.files.WorldList;
import net.whispwriting.universenethers.files.WorldSettingsFile;
import net.whispwriting.universes.Universes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class EnableTask implements Runnable {
    @Override
    public void run() {
        WorldSettingsFile worldSettings = new WorldSettingsFile(Universes.getPlugin(Universes.class));
        WorldList worldList = new WorldList(Universes.getPlugin(Universes.class));
        if (worldSettings.get() == null){
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + "" + ChatColor.RED + "Universes settings file not found. Please install the Universes Plugin to use this plugin.");
        }else{
            Bukkit.getPluginManager().registerEvents(new NetherPortalHandler(UniverseNethers.getPlugin(UniverseNethers.class)), UniverseNethers.getPlugin(UniverseNethers.class));
        }

    }
}
