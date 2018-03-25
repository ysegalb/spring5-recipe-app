package guru.springframework.converters;

import java.util.HashSet;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>{

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter,
            IngredientCommandToIngredient ingredientConverter,
            NotesCommandToNotes notesConverter){
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source){
        if(source == null){
            return null;
        }

        final Recipe.RecipeBuilder recipe = Recipe.builder()
                .id(source.getId())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .description(source.getDescription())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .servings(source.getServings())
                .source(source.getSource())
                .url(source.getUrl())
                .notes(notesConverter.convert(source.getNotes()));

        HashSet<Category> categories = new HashSet<>();
        source.getCategories().iterator()
                .forEachRemaining(cat -> categories.add(categoryConveter.convert(cat)));
        recipe.categories(categories);

        HashSet<Ingredient> ingredients = new HashSet<>();
        source.getIngredients().iterator()
                .forEachRemaining(i -> ingredients.add(ingredientConverter.convert(i)));
        recipe.ingredients(ingredients);

        return recipe.build();
    }
}
