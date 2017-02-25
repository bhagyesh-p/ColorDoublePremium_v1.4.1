package dogboy602k.ColorDouble.Main;

import dogboy602k.ColorDouble.Commands.ColorDoubleCommands;
import dogboy602k.ColorDouble.Util.FileManager;
import dogboy602k.ColorDouble.Util.Manager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by dogboy on 7/19/2016.
 */
public class Main extends JavaPlugin {

    private FileManager fileManager;
    private Manager manager;
    private Economy economy = null;
    private ColorDoubleCommands colorDoubleCommands;


    @Override
    public void onEnable() {
        File Configfile;

        this.fileManager = new FileManager(this);
        if (!(Configfile = new File(this.getDataFolder(), "config.yml")).exists()) {
            this.saveDefaultConfig();
        }

        this.colorDoubleCommands = new ColorDoubleCommands(this);

        setupEconomy();
        this.manager = new Manager(this);
        getCommand("CD").setExecutor(new ColorDoubleCommands(this));
        this.getManager().pickWinner();
        Bukkit.getPluginManager().registerEvents(manager, this);


    }

    @Override
    public void onDisable() {

    }

    public Manager getManager() {
        return manager;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);

    }

    public Economy getEconomy() {
        return economy;
    }

    public FileManager getFileManager() {
        return fileManager;
    }


}
