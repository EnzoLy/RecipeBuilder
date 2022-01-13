package me.enzol.recipebuilder;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;

public class RecipeBuilder {

    private final ShapedRecipe shapedRecipe;
    private final Map<Integer, Key> ingredients = Maps.newHashMap();

    public RecipeBuilder(Plugin plugin, String key, ItemStack result) {
        this.shapedRecipe = new ShapedRecipe(new NamespacedKey(plugin, key), result);
    }

    public RecipeBuilder addIngredient(Material material, int slot) {

        Optional<Key> key = ingredients.values().stream().filter(k -> k.getItem() == material).findFirst();

        key.ifPresent(value -> ingredients.put(slot, value));

        String keyString = getKeyBySize(ingredients.size());

        this.ingredients.put(slot, new Key(keyString, material));
        return this;
    }

    public RecipeBuilder addIngredientsToRow(Material material, int row) {

        if(row < 0 || row > 2) {
            throw new IllegalArgumentException("Row must be between 0 and 2");
        }

        for(int i = 0; i < 3; i++) {
            addIngredient(material, row * 3 + i);
        }

        return this;
    }

    public RecipeBuilder addIngredientsToColumn(Material material, int column) {
        if(column < 0 || column > 2) {
            throw new IllegalArgumentException("Column must be between 0 and 2");
        }

        for(int i = 0; i < 3; i++) {
            addIngredient(material, column + i * 3);
        }

        return this;
    }

    public RecipeBuilder addIngredient(Material material, int row, int column) {
        addIngredient(material, row * 3 + column);
        return this;
    }

    public RecipeBuilder addIngredientToCenter(Material material) {
        addIngredient(material, 4);
        return this;
    }

    public RecipeBuilder addIngredientToCenter(Material material, int row) {
        addIngredient(material, row * 3 + 1);
        return this;
    }

    public RecipeBuilder addIngredientToEnd(Material material) {
        addIngredient(material, 8);
        return this;
    }

    public RecipeBuilder addIngredientToFirst(Material material) {
        addIngredient(material, 0);
        return this;
    }

    public RecipeBuilder addIngredientToEdge(Material material, int row) {
        addIngredient(material, row * 3 + 2);
        return this;
    }

    public ShapedRecipe build(){

        registerStringTemplate();

        addIngredients();

        return shapedRecipe;
    }

    private void addIngredients(){
        for(Map.Entry<Integer, Key> entry : ingredients.entrySet()) {
            shapedRecipe.setIngredient(entry.getValue().getKey().charAt(0), entry.getValue().getItem());
        }
    }

    private void registerStringTemplate(){
        StringBuilder shape = new StringBuilder();
        StringBuilder shape2 = new StringBuilder();
        StringBuilder shape3 = new StringBuilder();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3 ; column++) {
                int slot = row * 3 + column;
                Key key = ingredients.get(slot);
                if(slot < 3) {
                    if(key != null) {
                        shape.append(key.getKey());
                    }else{
                        shape.append(" ");
                    }
                }else if (slot < 6) {
                    if(key != null) {
                        shape2.append(key.getKey());
                    }else{
                        shape2.append(" ");
                    }
                }else {
                    if(key != null) {
                        shape3.append(key.getKey());
                    }else{
                        shape3.append(" ");
                    }
                }
            }
        }

        shapedRecipe.shape(shape.toString(), shape2.toString(), shape3.toString());
    }

    private String getKeyBySize(int size){
        return switch (size) {
            case 2 -> "B";
            case 3 -> "C";
            case 4 -> "D";
            case 5 -> "E";
            case 6 -> "F";
            case 7 -> "G";
            case 8 -> "H";
            case 9 -> "I";
            default -> "A";
        };
    }

    @AllArgsConstructor
    @Getter
    private final class Key{
        public String key;
        public Material  item;
    }
}