package io.github.seriousguy888.billzovillagers;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import io.github.seriousguy888.billzovillagers.listeners.*;
import io.github.seriousguy888.billzovillagers.utils.UpdateChecker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BillzoVillagers extends JavaPlugin {
  private static BillzoVillagers plugin;
  FileConfiguration config = getConfig();

  @Override
  public void onEnable() {
    plugin = this;

    config.options().copyDefaults();
    saveDefaultConfig();

    new TaskNameVillagers().runTaskTimer(plugin, 0L, 200L);
    registerListeners();
    new UpdateChecker().checkForUpdates();
  }

  private void registerListeners() {
    PluginManager pluginManager = getServer().getPluginManager();

    pluginManager.registerEvents(new EntityBreedListener(), this);
    pluginManager.registerEvents(new EntityDeathListener(), this);
    pluginManager.registerEvents(new EntityDamageListener(), this);
    pluginManager.registerEvents(new FoodLevelChangeListener(), this);
    pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
    pluginManager.registerEvents(new PlayerJoinListener(), this);
  }

  public TextChannel getDiscordChannel() {
    String channelName = config.getString("discordsrv.channel_name");

    boolean discordSrvPresent = getServer().getPluginManager().getPlugin("DiscordSRV") != null;
    return discordSrvPresent
      ? DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName(channelName)
      : null;
  }


  @Override
  public void onDisable() {
    System.out.println("f");
  }

  public static BillzoVillagers getPlugin() {
    return plugin;
  }
}