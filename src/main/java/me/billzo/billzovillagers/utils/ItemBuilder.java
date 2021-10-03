package me.billzo.billzovillagers.utils;

import me.billzo.billzovillagers.BillzoVillagers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ItemBuilder {
  private Material material;
  private int amount;
  private ItemMeta meta;

  public ItemBuilder(Material material) {
    this(material, 1);
  }

  public ItemBuilder(Material material, int amount) {
    this.material = material;
    this.amount = amount;
    this.meta = Bukkit.getItemFactory().getItemMeta(material);
  }

  public ItemBuilder setName(String name) {
    meta.setDisplayName(name);
    return this;
  }

  public ItemBuilder setLore(String... lore) {
    meta.setLore(Arrays.asList(lore));
    return this;
  }

  public ItemBuilder setKeyString(String key, String value) {
    PersistentDataContainer data = meta.getPersistentDataContainer();
    data.set(new NamespacedKey(BillzoVillagers.getPlugin(), key), PersistentDataType.STRING, value);
    return this;
  }

  public ItemStack build() {
    ItemStack item = new ItemStack(material, amount);
    item.setItemMeta(meta);
    return item;
  }
}