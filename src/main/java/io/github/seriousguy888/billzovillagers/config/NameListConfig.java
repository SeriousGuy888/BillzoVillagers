package io.github.seriousguy888.billzovillagers.config;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;

import java.io.FileNotFoundException;
import java.util.List;

public class NameListConfig extends ConfigReader {
    public NameListConfig(BillzoVillagers plugin, String name) throws FileNotFoundException {
        super(plugin, name, true);
    }

    public boolean migrateFromMainConfig(List<List<String>> importedNames) {
        if (importedNames == null || importedNames.size() < 2) {
            plugin.getLogger().warning("Received invalid name list data to migrate.");
            return false;
        }

        config.set("personal_names", importedNames.get(0));
        config.set("family_names", importedNames.get(1));
        saveToDisk();

        plugin.getLogger().info("Migrated name list from main config file into name list file.");
        return true;
    }


    public List<String> getFirstNames() {
        return config.getStringList("personal_names");
    }

    public List<String> getLastNames() {
        return config.getStringList("family_names");
    }
}
