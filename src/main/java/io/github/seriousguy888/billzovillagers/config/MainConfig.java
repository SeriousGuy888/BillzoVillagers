package io.github.seriousguy888.billzovillagers.config;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;

import java.io.FileNotFoundException;
import java.util.List;

public class MainConfig extends ConfigReader {
    public MainConfig(BillzoVillagers plugin, String name) throws FileNotFoundException {
        super(plugin, name, true);
    }

    public boolean getMiddleNamesEnabled() {
        return config.getBoolean("naming.middle_names.enabled");
    }

    public double getMiddleNamesChancePercentage() {
        return config.getDouble("naming.middle_names.chance_percentage");
    }

    public String getDiscordSrvChannelName() {
        return config.getString("discordsrv.channel_name", "");
    }

    public boolean canLeashVillager() {
        return config.getBoolean("leashing.villager", true);
    }

    public boolean canLeashWanderingTrader() {
        return config.getBoolean("leashing.wandering_trader", true);
    }

    public boolean canLeashZombieVillager() {
        return config.getBoolean("leashing.zombie_villager", false);
    }

    public void attemptMigration(NameListConfig nameListConfig) {
        List<String> firstNames = config.getStringList("names.first");
        List<String> lastNames = config.getStringList("names.last");

        // getStringList() returns a list and never null. if the lists are empty, they likely
        // arent actually there in the config file.
        if (firstNames.isEmpty() || lastNames.isEmpty()) {
            return;
        }

        plugin.getLogger().info("Name list present in main config. Attempting to migrate to new location...");

        boolean backupSuccessful = this.saveBackupCopy() && nameListConfig.saveBackupCopy();
        if (!backupSuccessful) {
            plugin.getLogger().warning("Backup failed; aborting migration.");
            return;
        }

        boolean success = nameListConfig.migrateFromMainConfig(firstNames, lastNames);

        if (success) {
            config.set("names.first", null);
            config.set("names.last", null);
            saveToDisk();

            plugin.getLogger().info("Removed name list from main config file.");
        }
    }
}
