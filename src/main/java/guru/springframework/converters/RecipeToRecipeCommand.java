package guru.springframework.converters;

import java.util.HashSet;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter, IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand.RecipeCommandBuilder command = RecipeCommand.builder();
        command.id(source.getId())
        .cookTime(source.getCookTime())
        .prepTime(source.getPrepTime())
        .description(source.getDescription())
        .difficulty(source.getDifficulty())
        .directions(source.getDirections())
        .servings(source.getServings())
        .source(source.getSource())
        .url(source.getUrl())
        .notes(notesConverter.convert(source.getNotes()));


        HashSet<CategoryCommand> categories = new HashSet<>();
        source.getCategories().iterator()
                .forEachRemaining(cat -> categories.add(categoryConveter.convert(cat)));
        command.categories(categories);

        HashSet<IngredientCommand> ingredients = new HashSet<>();
        source.getIngredients().iterator()
                .forEachRemaining(i -> ingredients.add(ingredientConverter.convert(i)));
        command.ingredients(ingredients);

        return command.build();
    }
}
