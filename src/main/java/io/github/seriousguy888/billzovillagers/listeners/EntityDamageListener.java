package io.github.seriousguy888.billzovillagers.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.awt.*;
import java.util.Locale;

public class EntityDamageListener implements Listener {
  FileConfiguration config = BillzoVillagers.getPlugin().getConfig();

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    Entity victim = event.getEntity();
    if(!(victim instanceof Villager villager))
      return;
    if (event.getFinalDamage() < villager.getHealth())
      return;

    String villName = villager.getName();
    String damageCause = event.getCause().name().toLowerCase().replaceAll("_", " ");
    String deathMessage = villName + " died from " + damageCause;

    if(event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
      Entity attacker = damageByEntityEvent.getDamager();
      if(attacker instanceof Projectile projectile && projectile.getShooter() != null) {
        attacker = (Entity) projectile.getShooter();
        deathMessage = villName + " was killed by " + attacker.getName() + " with " + projectile.getName();
      } else {
        deathMessage = villName + " was killed by " + attacker.getName();
      }
    } else if(event instanceof EntityDamageByBlockEvent damageByBlockEvent) {
      Block block = damageByBlockEvent.getDamager();
      if(block != null)
        deathMessage = villName + " died from " + block.getType().name().toLowerCase();
    }

    Bukkit.broadcastMessage(deathMessage);

    TextChannel channel = BillzoVillagers.getPlugin().getDiscordChannel();
    if(channel != null) {
      Location location = villager.getLocation();
      String worldName = location.getWorld() != null
          ? location.getWorld().getName()
          : "unknown world";
      String locationFooter = "At "
          + location.getBlockX() + " "
          + location.getBlockY() + " "
          + location.getBlockZ() + " in world "
          + worldName.toUpperCase();

      MessageEmbed embed = new EmbedBuilder()
          .setColor(new Color(0))
          .setDescription(deathMessage)
          .setFooter(locationFooter)
          .build();
      channel.sendMessageEmbeds(embed).queue();
    }
  }
}
