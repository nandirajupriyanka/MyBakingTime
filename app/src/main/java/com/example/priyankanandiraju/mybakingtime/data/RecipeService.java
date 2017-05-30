package com.example.priyankanandiraju.mybakingtime.data;


import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by priyankanandiraju on 5/21/17.
 */

public interface RecipeService {
    String RECIPES = "topher/2017/May/59121517_baking/baking.json";

    @GET(RECIPES)
    Call<List<Recipe>> getRecipes();
}