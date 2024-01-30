package io.github.seriousguy888.billzovillagers;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import io.github.seriousguy888.billzovillagers.commands.ToggleVillagerDeathMessagesCommand;
import io.github.seriousguy888.billzovillagers.config.MainConfig;
import io.github.seriousguy888.billzovillagers.config.NameListConfig;
import io.github.seriousguy888.billzovillagers.listeners.*;
import io.github.seriousguy888.billzovillagers.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class BillzoVillagers extends JavaPlugin {
    private static BillzoVillagers plugin;

    public File dataFile;
    public FileConfiguration dataConfig;
    public DataManager dataManager;
    public HashMap<Player, Boolean> villagerDeathMessagesEnabled;

    private MainConfig mainConfig;
    private NameListConfig nameListConfig;

    private UpdateChecker updateChecker;

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onEnable() {
        plugin = this;

        try {
            mainConfig = new MainConfig(this, "config");
            nameListConfig = new NameListConfig(this, "villager_names");
        } catch (FileNotFoundException e) {
            getLogger().severe("Failed to initialise a config file. Cannot continue; disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        mainConfig.attemptMigration(nameListConfig);


        new TaskNameVillagers().runTaskTimer(plugin, 0L, 200L);
        registerListeners();
        registerCommands();

        dataFile = new File(getDataFolder() + File.separator + "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        dataManager = new DataManager();
        villagerDeathMessagesEnabled = new HashMap<>();
        dataManager.loadPlayerData(); // load data of all online players


        updateChecker = new UpdateChecker(this, "SeriousGuy888/BillzoVillagers");
        updateChecker.checkForUpdates();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new EntityBreedListener(), this);
        pluginManager.registerEvents(new VillagerMeatListener(), this);
        pluginManager.registerEvents(new DeathMessageListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new JoinLeaveListener(), this);
        pluginManager.registerEvents(new LeashingListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("toggle_villager_death_messages"))
                .setExecutor(new ToggleVillagerDeathMessagesCommand());
    }

    public TextChannel getDiscordChannel() {
        String channelName = mainConfig.getDiscordSrvChannelName();

        boolean discordSrvPresent = getServer().getPluginManager().getPlugin("DiscordSRV") != null;
        return discordSrvPresent
                ? DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName(channelName)
                : null;
    }


    @Override
    public void onDisable() {
        dataManager.savePlayerData();
        getLogger().info("Unloading BillzoVillagers");
    }

    public static BillzoVillagers getPlugin() {
        return plugin;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public NameListConfig getNameListConfig() {
        return nameListConfig;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
}