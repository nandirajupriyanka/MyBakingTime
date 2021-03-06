package com.example.priyankanandiraju.mybakingtime.detailUI;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.priyankanandiraju.mybakingtime.R;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;

import static com.example.priyankanandiraju.mybakingtime.detailUI.RecipeDetailFragment.ARG_ITEM_ID;
import static com.example.priyankanandiraju.mybakingtime.detailUI.RecipeDetailFragment.STEP_POSITION;
import static com.example.priyankanandiraju.mybakingtime.utils.Constants.INGREDIENTS;
import static com.example.priyankanandiraju.mybakingtime.utils.Constants.SHOW_ITEM;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(SHOW_ITEM)) {
                    if (intent.getStringExtra(SHOW_ITEM).equals(INGREDIENTS)) {
                        Recipe recipe = intent.getParcelableExtra(ARG_ITEM_ID);
                        setTitle(recipe.getName());
                        arguments.putParcelable(ARG_ITEM_ID, recipe);
                        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.recipe_detail_container, fragment)
                                .commit();
                    } else {
                        if (intent.hasExtra(STEP_POSITION) && intent.hasExtra(ARG_ITEM_ID)) {
                            int stepPosition = intent.getIntExtra(STEP_POSITION, 0);
                            Recipe recipe = intent.getParcelableExtra(ARG_ITEM_ID);
                            setTitle(recipe.getName());
                            arguments.putInt(STEP_POSITION, stepPosition);
                            arguments.putParcelable(ARG_ITEM_ID, recipe);

                            RecipeDetailFragment fragment = new RecipeDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.recipe_detail_container, fragment)
                                    .commit();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
