package me.billzo.billzovillagers.listeners;

import me.billzo.billzovillagers.BillzoVillagers;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEntityListener implements Listener {
  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    FileConfiguration config = BillzoVillagers.getPlugin().getConfig();

    Entity entity = event.getRightClicked();
    if((entity instanceof Villager && config.getBoolean("leashing.villager")) ||
        (entity instanceof ZombieVillager && config.getBoolean("leashing.zombie_villager")) ||
        (entity instanceof WanderingTrader && config.getBoolean("leashing.wandering_trader"))) {
      LivingEntity livingEntity = (LivingEntity) entity;
      if(!livingEntity.isLeashed()) {
        HumanEntity player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if(!heldItem.isSimilar(new ItemStack(Material.LEAD)))
          return;

        event.setCancelled(true);
        livingEntity.setLeashHolder(player);
        if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
          ItemStack newHeldItem = heldItem;
          newHeldItem.setAmount(newHeldItem.getAmount() - 1);
          if(newHeldItem.getAmount() <= 0)
            newHeldItem = new ItemStack(Material.AIR);

          player.getInventory().setItemInMainHand(newHeldItem);
        }
      }
    }
  }
}
