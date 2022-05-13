package io.github.seriousguy888.billzovillagers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    Entity victim = event.getEntity();
    if(!(victim instanceof Villager villager))
      return;
    if (event.getFinalDamage() < villager.getHealth())
      return;

    String villName = villager.getName();
    String deathMessage = villName + " died from " + event.getCause().name().toLowerCase();

    if(event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
      Entity attacker = damageByEntityEvent.getDamager();
      if(attacker instanceof Projectile projectile && projectile.getShooter() != null) {
        attacker = (Entity) projectile.getShooter();
        deathMessage = villName + " was shot by " + attacker.getName() + " with " + projectile.getName();
      } else {
        deathMessage = villName + " was killed by " + attacker.getName();
      }
    } else if(event instanceof EntityDamageByBlockEvent damageByBlockEvent) {
      Block block = damageByBlockEvent.getDamager();
      if(block != null)
        deathMessage = villName + " died from " + block.getType().name().toLowerCase();
    }

    Bukkit.broadcastMessage(deathMessage);
  }
}
