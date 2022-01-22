package io.github.seriousguy888.billzovillagers;

import io.github.seriousguy888.billzovillagers.guis.VillagerMenu;
import io.github.seriousguy888.billzovillagers.listeners.EntityBreedListener;
import io.github.seriousguy888.billzovillagers.listeners.EntityDeathListener;
import io.github.seriousguy888.billzovillagers.listeners.FoodLevelChangeListener;
import io.github.seriousguy888.billzovillagers.listeners.PlayerInteractEntityListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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
    registerCommands();
  }

  private void registerListeners() {
    PluginManager pluginManager = getServer().getPluginManager();

    pluginManager.registerEvents(new EntityBreedListener(), this);
    pluginManager.registerEvents(new EntityDeathListener(), this);
    pluginManager.registerEvents(new FoodLevelChangeListener(), this);
    pluginManager.registerEvents(new PlayerInteractEntityListener(), this);

    pluginManager.registerEvents(new VillagerMenu(), this);
  }

  private void registerCommands() {
    Objects.requireNonNull(this.getCommand("villager")).setExecutor(new CommandVillager());
  }

  @Override
  public void onDisable() {
    System.out.println("f");
  }

  public static BillzoVillagers getPlugin() {
    return plugin;
  }
}