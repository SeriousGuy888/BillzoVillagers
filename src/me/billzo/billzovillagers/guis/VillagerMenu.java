package me.billzo.billzovillagers.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.Arrays;

public class VillagerMenu implements Listener {
  private Inventory gui;
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
      gui = Bukkit.createInventory(player, 9, guiTitle);

      ItemStack[] menuItems = {
//          createGuiItem(Material.FLINT_AND_STEEL, "§3Fire", "§bRemove the villager's profession if it has not been traded with."),
      };
      gui.setContents(menuItems);

      player.openInventory(gui);
    }
  }


  protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
    final ItemStack item = new ItemStack(material, 1);
    final ItemMeta meta = item.getItemMeta();

    meta.setDisplayName(name);
    meta.setLore(Arrays.asList(lore));
    item.setItemMeta(meta);

    return item;
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if(event.getInventory() != gui)
      return;

    Player player = (Player) event.getWhoClicked();
    switch(event.getCurrentItem().getType()) {
//      case FLINT_AND_STEEL:
    }

    event.setCancelled(true);
  }

  @EventHandler
  public void onInventoryDrag(InventoryDragEvent event) {
    if(event.getInventory().equals(gui)) {
      event.setCancelled(true);
    }
  }
}