package dogboy602k.ColorDouble.Util;

import dogboy602k.ColorDouble.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by dogboy on 12/23/2016.
 */
public class FileManager {
    private Main plugin;

    public FileManager(Main plugin){
        this.plugin=plugin;
    }

    public FileConfiguration getConfiguration(File file) {
        if (file == null) {
            System.out.println("Error config file null");
            return null;
        }
        return YamlConfiguration.loadConfiguration((File)file);
    }

    public void saveConfiguration(File file) {
        if (file == null) {
            System.out.println("Error config file null");
            return;
        }
        try {
            this.getConfiguration(file).save(file);
        }
        catch (IOException e) {
            System.out.println("Error saving configuration file! " + e.getMessage());
        }
    }

    public String getColor1 (){
        return plugin.getConfig().getString("color1");
    }

    public String getColor2 (){
        return plugin.getConfig().getString("color2");
    }

    public String getColor3 (){
        return plugin.getConfig().getString("color3");
    }

    public double getButtonAdd1() {
        return this.plugin.getConfig().getDouble("buttonadd1");
    }

    public double getButtonAdd2() {
        return this.plugin.getConfig().getDouble("buttonadd2");
    }

    public double getButtonSubtract1() {
        return this.plugin.getConfig().getDouble("buttonsubtract1");
    }

    public double getButtonSubtract2() {
        return this.plugin.getConfig().getDouble("buttonsubtract2");
    }

    public double getMultiplier1() {
        return this.plugin.getConfig().getDouble("multipliercolor1");
    }

    public double getMultiplier2() {
        return this.plugin.getConfig().getDouble("multipliercolor2");
    }

    public double getMultiplier3() {
        return this.plugin.getConfig().getDouble("multipliercolor3");
    }

}
