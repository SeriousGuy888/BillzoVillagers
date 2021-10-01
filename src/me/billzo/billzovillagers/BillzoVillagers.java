package me.billzo.billzovillagers;

import me.billzo.billzovillagers.listeners.EntityBreedListener;
import me.billzo.billzovillagers.listeners.EntityDeathListener;
import me.billzo.billzovillagers.listeners.FoodLevelChangeListener;
import me.billzo.billzovillagers.listeners.PlayerInteractEntityListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BillzoVillagers extends JavaPlugin {
  private static BillzoVillagers plugin;
  FileConfiguration config = getConfig();

  @Override
  public void onEnable() {
    plugin = this;

    config.addDefault("leashing.villager", true);
    config.addDefault("leashing.wandering_trader", true);
    config.addDefault("leashing.zombie_villager", false);
    config.addDefault("names.first", new String[]{"Aaliyah", "Abigail", "Adalyn", "Aiden", "Alexander", "Amelia", "Aria", "Ariana", "Asher", "Aubrey", "Aurora", "Ava", "Avery", "Barry", "Benjamin", "Berry", "Billzo", "Birch", "Blackberry", "Blackcherry", "Blueberry", "Blunt", "Bovine", "Caden", "Caleb", "Cameron", "Camilla", "Carry", "Carson", "Carter", "Cary", "Charlie", "Charlotte", "Chloe", "Chris", "Chunk", "Cocaniny", "Colton", "Cranberry", "Dairy", "Daniel", "Druggie", "Earring", "Elena", "Eliana", "Elijah", "Ella", "Ellie", "Emilia", "Emily", "Emma", "Ethan", "Evelyn", "Everly", "Ezra", "Fairy", "Ferry", "Fynchy", "Gabriel", "Gary", "Gerry", "Gianna", "Glary", "Glory", "Goldie", "Googly", "Grayson", "Hairy", "Hannah", "Harper", "Harry", "Henry", "Holdy", "Hrrm", "Hurrm", "Hurrrm", "Isaac", "Isabella", "Isaiah", "Isla", "Jack", "Jackie", "Jackson", "Jackson", "Jakob", "James", "Jasper", "Jayce", "Jayden", "Jerry", "Josiah", "Julian", "Karen", "Kerosene", "Kinsley", "Laim", "Larry", "Layla", "Lazy", "Leah", "Leo", "Levi", "Liam", "Lila", "Lily", "Lincoln", "Logan", "Luca", "Lucas", "Luke", "Luna", "Madelyn", "Madison", "Mary", "Mason", "Matteo", "Matthew", "Aary", "Maverick", "Maya", "Merry", "Mia", "Michael", "Mila", "Miles", "Most popular baby names of 2020", "Muhammad", "Noah", "Nora", "Nova", "Oliver", "Olivia", "Owen", "Paisley", "Parry", "Penelope", "Peyton", "Quantum", "Quarry", "Raspberry", "Riley", "Ryan", "Samuel", "Sarah", "Scarlett", "Scary", "Sebastian", "Sherry", "Smith", "Sophia", "Sorry", "Terry", "Toby", "Very", "Walrus", "Wary", "Weedy", "William", "Wyatt", "Yakson", "Yeti", "Zoe"});
    config.addDefault("names.last", new String[]{"Trumpet", "Snowman", "Toothbrush", "Smith", "Duck", "Ass", "Bass", "Bypass", "Class", "Crass", "Grass", "Mass", "Pass", "Gas", "Jackass", "Nervegas", "Trespass", "Pompeii", "Trump", "Biden", "Sandals", "Blunt", "Elchapo", "Pie", "Garfield", "Inbred", "Hrrm", "Hrrrm", "Hurm", "Hurrm", "Herrm", "Hurrrm", "Hrm", "Hrrrmson", "Claus", "Candycane", "Golem", "Stolen", "Goldfish", "Ingot", "Baron", "Christ", "Yeti", "Rubik", "Cube", "Death", "Nuke", "Sheppard", "Liam", "Bee", "Ironforests", "Jasper", "Bovine", "Thorium", "Plutonium", "Uranium", "Hexagon", "Matteoville", "Dragonson", "Bluntson", "Puzic", "Aardvark", "Weeeeed", "Druggledealerson", "Caterpillar", "Jasperlandtwo", "Holden", "Tobytopia", "Youtubson", "Netherite", "Moolah", "Hmm", "Hm", "Hmmm", "H'mm", "Jakob", "Porkchop", "Honag", "Ohio", "Newrhodes", "RandumPerson314"});
    config.options().copyDefaults(true);
    saveConfig();

    new TaskNameVillagers().runTaskTimer(plugin, 0L, 200L);
    registerListeners();
    this.getCommand("villager").setExecutor(new CommandVillager());
  }

  private void registerListeners() {
    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(new EntityBreedListener(), this);
    pluginManager.registerEvents(new EntityDeathListener(), this);
    pluginManager.registerEvents(new FoodLevelChangeListener(), this);
    pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
  }

  @Override
  public void onDisable() {
    System.out.println("f");
  }

  public static BillzoVillagers getPlugin() {
    return plugin;
  }
}