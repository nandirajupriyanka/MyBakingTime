package com.example.priyankanandiraju.mybakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.priyankanandiraju.mybakingtime.data.RecipeAPI;
import com.example.priyankanandiraju.mybakingtime.data.RecipeService;
import com.example.priyankanandiraju.mybakingtime.detailUI.RecipeListActivity;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
    public static final String EXTRA_RECIPE_DATA = "EXTRA_RECIPE_DATA";
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private RecipeService mRecipeAPIService;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvRecipes.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(), this);
        rvRecipes.setAdapter(mRecipeAdapter);

        // Get recipes list
        Retrofit retrofit = RecipeAPI.getClient();
        mRecipeAPIService = retrofit.create(RecipeService.class);
        fetchRecipes();
    }

    private void fetchRecipes() {
        Call<List<Recipe>> recipeCall = mRecipeAPIService.getRecipes();
        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.v(TAG, "onResponse");
                tvEmpty.setVisibility(View.GONE);
                rvRecipes.setVisibility(View.VISIBLE);
                if (mRecipeAdapter != null) {
                    mRecipeAdapter.setRecipeData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v(TAG, "onFailure");
                tvEmpty.setVisibility(View.VISIBLE);
                rvRecipes.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Log.v(TAG, recipe.toString());
        Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
        intent.putExtra(EXTRA_RECIPE_DATA, recipe);
        startActivity(intent);
    }

}
