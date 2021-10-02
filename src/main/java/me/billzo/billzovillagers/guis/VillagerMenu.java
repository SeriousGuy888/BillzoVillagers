package me.billzo.billzovillagers.guis;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class VillagerMenu implements Listener {
  //  private Inventory gui;
  private Villager villager;

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    Player player = event.getPlayer();
    Entity entity = event.getRightClicked();
    ItemStack heldItem = player.getInventory().getItemInMainHand();

    if(entity instanceof Villager && player.isSneaking() && heldItem.getType().equals(Material.AIR)) {
      event.setCancelled(true);
      villager = (Villager) entity;

      String guiTitle = villager.getCustomName();
      if(guiTitle == null)
        guiTitle = "Villager Menu";


      ChestGui gui = new ChestGui(1, guiTitle);
      gui.setOnGlobalClick(evt -> evt.setCancelled(true));
      gui.setOnGlobalDrag(evt -> evt.setCancelled(true));

      gui.show(player);
    }
  }
}