package com.popogonry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    private File file;
    private FileConfiguration config;


    public Config(String basePath, String fileName) {

        this.file = new File(basePath, fileName);
        this.config = YamlConfiguration.loadConfiguration(this.file);

    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void store() {
        if(config == null) return;

        try{
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean exists() {
        return file != null && file.exists();
    }

    public void reload() {
        if(!exists()) return;
        config = YamlConfiguration.loadConfiguration(file);
    }

    public abstract void loadDefaults();
    public abstract void applySettings();

}
