package me.neo.twistapi;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Recipe Builder class
 * Houses methods to create recipes easily
 */
public class RecipeBuilder {
    private ShapedRecipe recipe;
    private ItemStack item;

    /**
     *
     * @param item ItemStack to add a recipe to
     */
    public RecipeBuilder(ItemStack item) {
        this.item = item;
    }

    /**
     *
     * @param key NamespacedKey value required for unique identification of an item
     * @return Instance of RecipeBuilder
     */
    public RecipeBuilder createRecipe(NamespacedKey key) {
        this.recipe = new ShapedRecipe(key, this.item);
        return this;
    }

    /**
     *
     * @param rows The Strings used for each row of the crafting grid. More at:  <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ShapedRecipe.html#shape(java.lang.String...)">ShapedRecipe</a>
     * @return Instance of RecipeBuilder
     */
    public RecipeBuilder setShape(String[] rows) {
        this.recipe.shape(rows[0],rows[1],rows[2]);
        return this;
    }

    /**
     *
     * @param ingredients The strings used to set the ingredients of a recipe. they must be formatted "KEY:MATERIAL" where KEY is the corresponding char you set in the setShape() method
     * @return Instance of RecipeBuilder
     */
    public RecipeBuilder setIngredients(String[] ingredients) {
        for (String KvP : ingredients) {
            String[] split = KvP.split(":");
            char key = split[0].charAt(0);
            String value = split[1];
            this.recipe.setIngredient(key, new RecipeChoice.ExactChoice(new ItemStack(Material.matchMaterial(value))));
        }
        return this;
    }

    /**
     *
     * @return A ShapedRecipe to be registered
     */
    public ShapedRecipe build() {
        return this.recipe;
    }

}
