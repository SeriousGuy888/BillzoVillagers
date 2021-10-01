package me.billzo.billzovillagers;

import org.bukkit.plugin.java.JavaPlugin;

public class BillzoVillagers extends JavaPlugin {
  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new Listeners(), this);

    this.getCommand("villager").setExecutor(new CommandVillager());
  }

  @Override
  public void onDisable() {
    System.out.println("f");
  }
}