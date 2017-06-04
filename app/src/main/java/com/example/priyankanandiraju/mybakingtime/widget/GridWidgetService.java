package com.example.priyankanandiraju.mybakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.priyankanandiraju.mybakingtime.R;
import com.example.priyankanandiraju.mybakingtime.recipe.Ingredient;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.priyankanandiraju.mybakingtime.utils.Constants.PREFS_NAME;
import static com.example.priyankanandiraju.mybakingtime.utils.Constants.SHARED_PREF_RECIPE;

/**
 * Created by priyankanandiraju on 6/3/17.
 */

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext());
    }
}

class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Ingredient> ingredientList = new ArrayList<>();

    public GridRemoteViewFactory(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPref;
        Recipe recipe;

        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPref.contains(SHARED_PREF_RECIPE)) {
            String jsonFavorites = sharedPref.getString(SHARED_PREF_RECIPE, null);
            Gson gson = new Gson();
            recipe = gson.fromJson(jsonFavorites, Recipe.class);
            ingredientList.clear();
            ingredientList.addAll(recipe.getIngredientsList());
        }
    }

    @Override
    public void onDestroy() {
        ingredientList.clear();
    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient currentIngredient = ingredientList.get(position);
        String ingredient = currentIngredient.getIngredient();
        String measure = currentIngredient.getMeasure();
        String quantity = String.valueOf(currentIngredient.getQuantity());
        String detail = quantity + " - " + measure;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.widget_ingredient_name, ingredient);
        views.setTextViewText(R.id.widget_ingredient_quantity, detail);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
