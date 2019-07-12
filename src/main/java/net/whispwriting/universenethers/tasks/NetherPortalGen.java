package net.whispwriting.universenethers.tasks;

import net.whispwriting.universenethers.files.WorldList;
import net.whispwriting.universenethers.files.WorldSettingsFile;
import net.whispwriting.universes.Universes;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class NetherPortalGen implements Runnable {

    private WorldSettingsFile worldSettings = new WorldSettingsFile(Universes.getPlugin(Universes.class));
    private WorldList worldList = new WorldList(Universes.getPlugin(Universes.class));
    private World nether;
    private Player player;
    public NetherPortalGen(World w, Player p){
        nether = w;
        player = p;
    }

    @Override
    public void run() {
        Location newNether = netherPortalPositioner(nether);
        if (newNether == null){
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "An error occured generating the nether portal. Pleae check console for more information.");
            System.out.println("Failed to find a valid place to generate a portal.");
        }
        Location loc1 = newNether;
        loc1.getBlock().setType(Material.OBSIDIAN);
        System.out.println(loc1.getBlock().getType());
        System.out.println(loc1);
        System.out.println(newNether);

        Location loc2 = newNether;
        loc2.setX(loc2.getX()+1);
        loc2.getBlock().setType(Material.OBSIDIAN);

        Location loc3 = newNether;
        loc3.setZ(loc3.getZ()+2);
        loc3.getBlock().setType(Material.OBSIDIAN);

        Location loc4 = newNether;
        loc4.setX(loc4.getX()+2);
        loc4.setY(loc4.getY()+1);
        loc4.getBlock().setType(Material.OBSIDIAN);

        Location loc5 = newNether;
        loc5.setX(loc5.getX()+2);
        loc5.setY(loc5.getY()+2);
        loc5.getBlock().setType(Material.OBSIDIAN);

        Location loc6 = newNether;
        loc6.setY(loc6.getY()+3);
        loc6.setX(loc6.getX()+2);
        loc6.getBlock().setType(Material.OBSIDIAN);

        Location loc7 =  newNether;
        loc7.setY(loc7.getY()+4);
        loc7.setX(loc7.getX()+2);
        loc7.getBlock().setType(Material.OBSIDIAN);

        Location loc8 = newNether;
        loc8.setX(loc8.getX()+1);
        loc8.setY(loc8.getY()+4);
        loc8.getBlock().setType(Material.OBSIDIAN);

        Location loc9 = newNether;
        loc9.setY(loc9.getY()+4);
        loc9.getBlock().setType(Material.OBSIDIAN);

        Location loc10 = newNether;
        loc10.setX(loc10.getX()-1);
        loc10.setY(loc10.getY()+4);
        loc10.getBlock().setType(Material.OBSIDIAN);

        Location loc11 = newNether;
        loc11.setY(loc11.getY()+3);
        loc11.setX(loc11.getX()-1);
        loc11.getBlock().setType(Material.OBSIDIAN);

        Location loc12 = newNether;
        loc12.setY(loc12.getY()+2);
        loc12.setX(loc12.getX()-1);
        loc12.getBlock().setType(Material.OBSIDIAN);

        Location loc13 = newNether;
        loc13.setY(loc13.getY()+1);
        loc13.setX(loc13.getX()-1);
        loc13.getBlock().setType(Material.OBSIDIAN);

        Location loc14 = newNether;
        loc14.setX(loc14.getX()-1);
        loc14.getBlock().setType(Material.OBSIDIAN);

        Location spawnpoint = newNether;
        spawnpoint.setY(spawnpoint.getY() + 1);
        spawnpoint.setX(spawnpoint.getX() + 1);
        player.teleport(spawnpoint);
    }

    private Location netherPortalPositioner(World nether) {
        Location netherLoc = nether.getSpawnLocation();
        System.out.println(netherLoc);
        netherLoc.setY(60);
        Location loc = checkX(netherLoc);
        return loc;
    }

    private Location checkX(Location start) {
        int count = 0;
        while(true) {
            double x = start.getX() + count;
            double y = start.getY();
            double z = start.getZ();
            Location loc = new Location(start.getWorld(), x ,y ,z);
            Block block = loc.getBlock();
            if (block.getType() != Material.AIR) {
                count++;
            } else {
                boolean safeBase = doubleCheckX(loc);
                if (!safeBase) {
                    count += 3;
                } else {
                    boolean safeY = checkY(loc);
                    if (!safeY) {
                        count += 3;
                    } else {
                        double yOfGround = findGroundLevel(loc);
                        Bukkit.getServer().broadcastMessage("yOfGround: " + yOfGround);
                        if (yOfGround == -1) {
                            count += 3;
                        } else {
                            Bukkit.getServer().broadcastMessage("Check X count: " + count);
                            double x2 = loc.getX();
                            double y2 = loc.getY() - yOfGround;
                            Bukkit.getServer().broadcastMessage("Y is: " + y);
                            double z2 = loc.getZ();
                            Location newLoc = new Location(start.getWorld(), x2 ,y2, z2);
                            return newLoc;
                        }
                    }
                }
            }
        }
    }

    private double findGroundLevel(Location startXForGeneration) {
        int count = 0;
        while (true){
            Location loc = startXForGeneration;
            loc.setY(loc.getY() - count);
            System.out.println(startXForGeneration);
            System.out.println(loc);
            Material m = loc.getBlock().getType();
            if (m == Material.LAVA){
                return -1;
            }
            if (m == Material.NETHERRACK || m == Material.SOUL_SAND || m == Material.NETHER_BRICK){
                Bukkit.getServer().broadcastMessage("Check ground count: " + count);
                return loc.getY()-1;
            }
            Bukkit.getServer().broadcastMessage("Checking for ground, still air.");
            count ++;
        }
    }

    private boolean checkY(Location startXForGeneration) {
        Location loc1 = startXForGeneration;
        Material m1 = loc1.getBlock().getType();

        Location loc2 = startXForGeneration;
        loc2.setY(loc2.getY() + 1);
        Material m2 = loc2.getBlock().getType();

        Location loc3 = startXForGeneration;
        loc3.setY(loc3.getY() + 2);
        Material m3 = loc3.getBlock().getType();

        Location loc4 = startXForGeneration;
        loc4.setY(loc4.getY() + 3);
        Material m4 = loc4.getBlock().getType();

        Location loc5 = startXForGeneration;
        loc5.setY(loc5.getY() + 4);
        Material m5 = loc5.getBlock().getType();

        if (m1 == Material.AIR && m2 == Material.AIR && m3 == Material.AIR && m4 == Material.AIR && m5 == Material.AIR){
            Bukkit.getServer().broadcastMessage("Confirmed Y is all air.");
            return true;
        }else{
            return false;
        }
    }

    private boolean doubleCheckX(Location startXForGeneration) {
        Location loc1 = startXForGeneration;
        Material m1 = loc1.getBlock().getType();

        Location loc2 = startXForGeneration;
        loc2.setX(loc2.getX() + 1);
        Material m2 = loc2.getBlock().getType();

        Location loc3 = startXForGeneration;
        loc3.setX(loc3.getX() + 2);
        Material m3 = loc3.getBlock().getType();

        Location loc4 = startXForGeneration;
        loc4.setX(loc4.getX() + 3);
        Material m4 = loc4.getBlock().getType();

        if (m1 == Material.AIR && m2 == Material.AIR && m3 == Material.AIR && m4 == Material.AIR){
            return true;
        }else{
            return false;
        }
    }
}
