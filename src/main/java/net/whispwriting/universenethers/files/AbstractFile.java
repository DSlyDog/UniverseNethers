package net.whispwriting.universenethers.files;

import net.whispwriting.universenethers.UniverseNethers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {
    protected UniverseNethers plugin;
    private File file;
    protected FileConfiguration config;

    public AbstractFile(UniverseNethers pl, String filename, String d) {
        this.plugin = pl;
        File dir = new File(pl.getDataFolder() + d);
        if (!dir.exists()) {
            dir.mkdir();
        }

        this.file = new File(dir, filename);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException var2) {
            System.out.println("Could not save file");
        }

    }

    public FileConfiguration get() {
        return this.config;
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
}
