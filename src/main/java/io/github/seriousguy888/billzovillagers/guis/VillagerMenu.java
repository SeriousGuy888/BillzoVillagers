package io.github.seriousguy888.billzovillagers.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VillagerMenu implements Listener {
  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    Player player = event.getPlayer();
    Entity entity = event.getRightClicked();
    ItemStack heldItem = player.getInventory().getItemInMainHand();

    if(entity instanceof Villager && player.isSneaking() && heldItem.getType().equals(Material.AIR)) {
      event.setCancelled(true);
      Villager villager = (Villager) entity;

      String guiTitle = villager.getName();

      ChestGui gui = new ChestGui(3, guiTitle);
      gui.setOnGlobalClick(evt -> evt.setCancelled(true));
      gui.setOnGlobalDrag(evt -> evt.setCancelled(true));

      OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
      background.addItem(new GuiItem(createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ")));
      background.setRepeat(true);
      gui.addPane(background);

      OutlinePane navigationPane = new OutlinePane(4, 1, 1, 1);
      navigationPane.addItem(new GuiItem(createItem(Material.BEDROCK, 1, "§6Villager Menu", "§eComing Soon")));
      gui.addPane(navigationPane);

      gui.show(player);
    }
  }

  private ItemStack createItem(Material material, int count, String name, String... lore) {
    ItemStack item = new ItemStack(material);
    item.setAmount(count);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(name);
    meta.setLore(Arrays.asList(lore));
    item.setItemMeta(meta);
    return item;
  }
}