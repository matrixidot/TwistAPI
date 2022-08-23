package me.neo.twistapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.UUID;

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
     * Will try to fail silently if Enchant name is incorrect
     * @param enchant The String of the enchantment to add to the item <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/enchantments/Enchantment.html>List of Enchants</a>
     * @param level The Level of the applied Enchant
     * @return Instance of ItemBuilder
     */
    public ItemBuilder addEnchantment(String enchant, int level) {
        try {
            this.item.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.fromString(enchant)), level);
        } catch (NullPointerException e) {
            Bukkit.getServer().getLogger().info("ItemBuilder Error: Incorrect enchantment name given: " + enchant);
        }

        return this;
    }

    /**
     * Sets the duribility (damage) of an item. Will fail silently if the item cannot be damaged.
     * @param durability The value of durability. 0 being full durability.
     * @return Instance of ItemBuilder
     */
    public ItemBuilder setDurability(int durability) {
        if (this.meta instanceof Damageable) { ((Damageable) this.meta).setDamage(durability);
        } else { Bukkit.getServer().getLogger().info("ItemBuilder Error: Item without durability cannot have a durability. Failing silently"); }
        return this;
    }

    /**
     *
     * @param modelData The <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/meta/ItemMeta.html#setCustomModelData(java.lang.Integer)>Custom Model Data</a> number
     * @return Instance of ItemBuilder
     */
    public ItemBuilder setCustomModelData(int modelData) {
        this.meta.setCustomModelData(modelData);
        return this;
    }

    /**
     *
     * @param flag The <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ItemFlag.html>Item Flags</a>
     * @return instance of ItemBuilder
     */
    public ItemBuilder addItemFlag(String flag) {
        this.meta.addItemFlags(ItemFlag.valueOf(flag));
        return this;
    }

    /**
     *
     * @param attribute The <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/Attribute.html">Attribute</a> to add
     * @param uuid The String of the UUID of the attribute
     * @param name The Name of the Attribute Modifier (Can be anything)
     * @param amount The Amount to add/multipy by
     * @param operation The types of <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/AttributeModifier.Operation.html">Attribute Modifier Operations</a>
     * @param slot The <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/EquipmentSlot.html">Equipment Slot to use</a>
     * @return Instance of ItemBuilder
     */
    public ItemBuilder addAttributeModifier(String attribute, UUID uuid, String name, double amount, String operation, String slot) {
        this.meta.addAttributeModifier(Attribute.valueOf(attribute), new AttributeModifier(uuid, name, amount, AttributeModifier.Operation.valueOf(operation), EquipmentSlot.valueOf(slot)));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }
    private void getContainer() {
        this.container = this.meta.getPersistentDataContainer();
    }

    /**
     * Adds a persistent data container
     * @param key The String value of a <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/NamespacedKey.html">NamespacedKey</a>
     * @param type The type of <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/persistence/PersistentDataType.html">PersistentDataType</a> to use
     * @param value The value of the persistent data container using the specified key and type
     * @return Instance of ItemBuilder
     */
    public ItemBuilder addDataContainer(NamespacedKey key, PersistentDataType type, Object value) {
        getContainer();
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
