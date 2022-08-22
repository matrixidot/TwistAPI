package me.neo.twistapi;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collections;

/**
 * Class that houses the methods for the ItemBuilder builder
 */
public class ItemBuilder {
    private ItemStack item;
    private ItemMeta meta;
    private PersistentDataContainer container;
    private String twist;

    /**
     *
     * @param mat Material of the itemstack
     * @param amount Amount of the itemstack
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
     * @param key The String value of a NamespacedKey
     * @param type The type of PersistentDataType to use
     * @param value The value of the persistent data container using the specified key and type
     * @return Instance of ItemBuilder
     */
    public ItemBuilder addDataContainer(NamespacedKey key, PersistentDataType type, Object value) {
        this.container.set(key, type, value);
        return this;
    }

    /**
     * Used for setting the twist of an item
     * This method must ALWAYS be called in order for the items to function as intended
     * @param twist The twist (Custom Scenario) to be linked to the item. Must be the same as the twist specified in the constructor in onLoad() for your plugins
     * @return Instance of ItemBuilder
     */
    public ItemBuilder setTwist(String twist) {
        this.twist = twist;
        return this;
    }

    /**
     * Updates the itemMeta
     * And updates the items hashmap with the specified item and it's twist
     * @return An ItemStack object to be used wherever
     */
    public ItemStack build() {
        this.updateItemMeta();
        TwistAPI.items.asMap().put(this.item, Collections.singleton(this.twist));
        System.out.println("Twist " + TwistAPI.items.asMap().get(this.item));
        return this.item;
    }
}
