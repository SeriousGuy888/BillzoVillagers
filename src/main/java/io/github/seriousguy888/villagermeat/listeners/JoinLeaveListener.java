package io.github.seriousguy888.villagermeat.listeners;


import io.github.seriousguy888.villagermeat.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {
  Main plugin;
  public JoinLeaveListener() {
    plugin = Main.getPlugin();
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    plugin.dataManager.loadPlayerData(player);
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    plugin.dataManager.savePlayerData(player);
  }
}