package net.whispwriting.universenethers.events;

import net.whispwriting.universenethers.UniverseNethers;
import net.whispwriting.universenethers.files.PreviousLocationFile;
import net.whispwriting.universenethers.files.WorldList;
import net.whispwriting.universenethers.files.WorldSettingsFile;
import net.whispwriting.universenethers.tasks.NetherPortalGen;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.Utils.Generator;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class NetherPortalHandler implements Listener {

    private WorldSettingsFile worldSettings = new WorldSettingsFile(Universes.getPlugin(Universes.class));
    private WorldList worldList = new WorldList(Universes.getPlugin(Universes.class));
    private UniverseNethers plugin;
    private PreviousLocationFile previousLocation;

    public NetherPortalHandler(UniverseNethers pl){
        plugin = pl;
    }

    @EventHandler
    public void onPortalUser(PlayerTeleportEvent event){
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL){
            worldSettings.reload();
            event.setCancelled(true);
            String worldName = event.getPlayer().getLocation().getWorld().getName();
            if (worldName.contains("_nether")){
                previousLocation = new PreviousLocationFile(plugin, event.getPlayer().getUniqueId().toString());
                Location loc = (Location) previousLocation.get().get("location");
                loc.setPitch(event.getPlayer().getLocation().getPitch());
                loc.setYaw(event.getPlayer().getLocation().getYaw());
                event.getPlayer().teleport(loc);
                previousLocation.get().set("location", null);
                previousLocation.save();
                previousLocation.reload();
            }else{
                previousLocation = new PreviousLocationFile(plugin, event.getPlayer().getUniqueId().toString());
                Location loc = event.getPlayer().getLocation();
                previousLocation.get().set("location", loc);
                previousLocation.save();
                previousLocation.reload();
                String netherWorldName = loc.getWorld().getName()+"_nether";
                World world = Bukkit.getWorld(netherWorldName);
                if (world == null){
                    Generator generator = new Generator(Universes.getPlugin(Universes.class), netherWorldName);
                    generator.setType(WorldType.NORMAL);
                    generator.setEnvironment(World.Environment.NETHER);
                    generator.createWorld();
                    World newNether = generator.getWorld();
                    Location spawnpoint = event.getPlayer().getLocation();
                    spawnpoint.setX(spawnpoint.getX()+8);
                    spawnpoint.setZ(spawnpoint.getZ()+8);
                    spawnpoint.setY(spawnpoint.getY()+8);
                    spawnpoint.setWorld(newNether);
                    newNether.setSpawnLocation(spawnpoint);
                    setDefaults(newNether.getName(), "nether", spawnpoint);
                    Bukkit.getScheduler().runTaskLater(UniverseNethers.getPlugin(UniverseNethers.class), new NetherPortalGen(newNether, event.getPlayer()), 20);
                }else{
                    String name = worldSettings.get().getString("worlds."+netherWorldName+".spawn.world");
                    double x = worldSettings.get().getDouble("worlds."+netherWorldName+".spawn.x");
                    double y = worldSettings.get().getDouble("worlds."+netherWorldName+".spawn.y");
                    double z = worldSettings.get().getDouble("worlds."+netherWorldName+".spawn.z");
                    Location teleTo = new Location(world, x, y, z, event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch());
                    event.getPlayer().teleport(teleTo);
                }
            }
        }
    }

    public void setDefaults(String name, String type, Location spawn){
        List<String> worlds = worldList.get().getStringList("worlds");
        worlds.add(name);
        worldList.get().set("worlds", worlds);

        double x = spawn.getX();
        double y = spawn.getY();
        double z = spawn.getZ();
        worldSettings.get().set("worlds." + name + ".name", name);
        worldSettings.get().set("worlds." + name + ".type", type);
        worldSettings.get().set("worlds." + name + ".pvp", true);
        worldSettings.get().addDefault("worlds." + name + ".spawn.world", name);
        worldSettings.get().addDefault("worlds." + name + ".spawn.x", x);
        worldSettings.get().addDefault("worlds." + name + ".spawn.y", y);
        worldSettings.get().addDefault("worlds." + name + ".spawn.z", z);
        worldSettings.get().set("worlds." + name + ".allowMonsters", true);
        worldSettings.get().set("worlds." + name + ".allowAnimals", true);
        worldSettings.get().set("worlds." + name + ".gameMode", "survival");
        worldSettings.get().set("worlds." + name + ".respawnWorld", "world");
        worldSettings.get().set("worlds." + name + ".playerLimit", -1);
        worldSettings.get().set("worlds." + name + ".allowFlight", true);
        worldSettings.save();
        worldList.save();
        Universes.reload();
    }
}
