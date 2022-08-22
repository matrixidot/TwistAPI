package me.neo.twistapi;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

/**
 * Class that houses the methods for the ItemBuilder builder
 */
public class ItemBuilder {
    private ItemStack item;
    private ItemMeta meta;
    private PersistentDataContainer container;

    /**
     *
     * @param mat Material of the ItemStack
     * @param amount Amount of the ItemStack
     */
    public ItemBuilder(Material mat, int amount) {
        this.item = new ItemStack(mat, amount);
        this.meta = item.getItemMeta();
    }
    // Called at the end
    private void updateItemMeta() {
        this.item.setItemMeta(this.meta);
    }

    /**
     * Sets the display name of the item
     * @param name Display name of the item
     * @return Instance of ItemBuilder
     */
    public ItemBuilder setDisplayName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the lore of the item
     * @param lines Strings that determine the lore
     * @return Instance of ItemBuilder
     */

    public ItemBuilder setLore(String... lines) {
        this.meta.setLore(Arrays.asList(lines));
        return this;
    }

    /**
     * Gets the item's persistent data container
     * @return instance of ItemBuilder
     */
    public ItemBuilder getContainer() {
        this.container = this.meta.getPersistentDataContainer();
        return this;
    }

    /**
     * Adds a persistent data container
     * @param key The String value of a <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/NamespacedKey.html">NamespacedKey</a>
     * @param type The type of <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/persistence/PersistentDataType.html">PersistentDataType</a> to use
     * @param value The value of the persistent data container using the specified key and type
     * @return Instance of ItemBuilder
     */
    public ItemBuilder addDataContainer(NamespacedKey key, PersistentDataType type, Object value) {
        this.container.set(key, type, value);
        return this;
    }

    /**
     * Updates the itemMeta
     * And updates the items hashmap with the specified item and it's twist
     * @return An ItemStack object to be used wherever
     */
    public ItemStack build() {
        this.updateItemMeta();
        return this.item;
    }
}
