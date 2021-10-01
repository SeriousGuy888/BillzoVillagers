package me.billzo.billzovillagers;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {
  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    Entity entity = event.getRightClicked();
    if(!(entity instanceof Villager))
      return;

    Villager villager = (Villager) entity;
    if(!villager.isLeashed()) {
      HumanEntity player = event.getPlayer();
      ItemStack heldItem = player.getInventory().getItemInMainHand();
      if(!heldItem.isSimilar(new ItemStack(Material.LEAD)))
        return;

      event.setCancelled(true);
      villager.setLeashHolder(player);
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
