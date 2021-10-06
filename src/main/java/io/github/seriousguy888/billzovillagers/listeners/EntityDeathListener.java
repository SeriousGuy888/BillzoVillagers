package io.github.seriousguy888.billzovillagers.listeners;

import io.github.seriousguy888.billzovillagers.utils.MeatManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class EntityDeathListener implements Listener {
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    if((entity instanceof Villager) || (entity instanceof WanderingTrader)) {
      boolean wanderingTrader = entity instanceof WanderingTrader;
      ItemStack itemStack = new MeatManager().getVillagerMeat(wanderingTrader);
      itemStack.setAmount(new Random().nextInt(3) + 1);
      entity.getWorld().dropItem(entity.getLocation(), itemStack);
    }
  }
}