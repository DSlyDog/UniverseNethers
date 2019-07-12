package net.whispwriting.universenethers;

import net.whispwriting.universenethers.tasks.EnableTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class UniverseNethers extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskLater(this, new EnableTask(), 20);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
