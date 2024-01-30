package io.github.seriousguy888.billzovillagers.listeners;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import io.github.seriousguy888.billzovillagers.utils.UpdateChecker;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final BillzoVillagers plugin;

    public PlayerJoinListener(BillzoVillagers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if(!player.isOp())
      return;
    if(!plugin.getUpdateChecker().isUpdateAvailable())
      return;

    player.sendMessage("\n" + ChatColor.AQUA + ChatColor.BOLD
        + "A new version of the BillzoVillagers plugin is available." + ChatColor.AQUA
        + "\nCurrently installed version: v" + BillzoVillagers.getPlugin().getDescription().getVersion()
        + "\nLatest available version: " + plugin.getUpdateChecker().getLatestVersion());

    TextComponent linkMessage = new TextComponent(
            "" + ChatColor.BLUE + ChatColor.UNDERLINE + "Updated Release Page" + "\n");
    linkMessage.setClickEvent(
            new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getUpdateChecker().getLatestReleasePageURL()));
    player.spigot().sendMessage(linkMessage);
  }
}
