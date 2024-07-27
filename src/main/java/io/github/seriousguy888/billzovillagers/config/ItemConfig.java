package io.github.seriousguy888.billzovillagers.config;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import org.bukkit.inventory.ItemStack;

import java.io.FileNotFoundException;

public class ItemConfig extends ConfigReader {
    public ItemConfig(BillzoVillagers plugin, String name) throws FileNotFoundException {
        super(plugin, name, true);
    }

    public ItemStack getVillagerMeat() {
        return config.getItemStack("villager-meat", null);
    }

    public ItemStack getWanderingTraderMeat() {
        return config.getItemStack("wandering-trader-meat", null);
    }
}
