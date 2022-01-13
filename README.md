# RecipeBuilder

## How to Use

1- Copy and paste `RecipeBuilder.java`

2- Create your recipe. Example:

```java 
//First paremeter is main plugin class, second is NameSpaceKey and last is resut
ShapedRecipe recipe = new RepiceBuilder(this, "test", new ItemStack(Material.NETHER_STAR))
            //First paremeter item and secon is column
            .addIngredientsToColumn(Material.DIRT, 1)
            .build();
```


3- Add recipe to server in `OnEnable`. Example:

```java
    @Override
    public void onEnable() {
        ShapedRecipe recipe = new RepiceBuilder(this, "test", new ItemStack(Material.NETHER_STAR))
            .addIngredientsToColumn(Material.DIRT, 1)
            .build();
            
        getServer().addRecipe(recipe);
    }
```
## Example Output
![This is an image](https://i.ibb.co/drZ2wJj/Captura-de-pantalla-2022-01-12-215740.png)
## Other Examples

```java
ShapedRecipe recipe = new RepiceBuilder(this, "test", new ItemStack(Material.NETHER_STAR))
            .addIngredient(Material.DIRT, 1)
            .build();
```

```java
ShapedRecipe recipe = new RepiceBuilder(this, "test", new ItemStack(Material.NETHER_STAR))
            .addIngredientsToRow(Material.DIRT, 1)
            .build();
```
