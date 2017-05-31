package com.example.priyankanandiraju.mybakingtime.detailUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyankanandiraju.mybakingtime.R;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.priyankanandiraju.mybakingtime.MainActivity.EXTRA_RECIPE_DATA;
import static com.example.priyankanandiraju.mybakingtime.detailUI.RecipeDetailActivity.INGREDIENTS;
import static com.example.priyankanandiraju.mybakingtime.detailUI.RecipeDetailActivity.SHOW_ITEM;
import static com.example.priyankanandiraju.mybakingtime.detailUI.RecipeDetailFragment.STEP_POSITION;

/**
 * An activity representing a list of Recipe. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    @BindView(R.id.recipe_list)
    RecyclerView rvRecipeList;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE_DATA)) {
                mRecipe = intent.getParcelableExtra(EXTRA_RECIPE_DATA);
                rvRecipeList.setAdapter(new RecipeListAdapter(mRecipe.getRecipeDetailList()));
                setTitle(mRecipe.getName());
            }
        }

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    public class RecipeListAdapter
            extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

        private final List<String> mValues;

        public RecipeListAdapter(List<String> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mContentView.setText(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.getAdapterPosition() == 0) {
                        // Ingredient
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelable(RecipeDetailFragment.ARG_ITEM_ID, mRecipe);
                            RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, RecipeDetailActivity.class);
                            intent.putExtra(SHOW_ITEM, INGREDIENTS);
                            intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, mRecipe);
                            context.startActivity(intent);
                        }
                    } else {
                        // Step
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putInt(STEP_POSITION, holder.getAdapterPosition() - 1);
                            arguments.putParcelable(RecipeDetailFragment.ARG_ITEM_ID, mRecipe);
                            RecipeDetailFragment fragment = new RecipeDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, RecipeDetailActivity.class);
                            intent.putExtra(SHOW_ITEM, "STEPS");
                            intent.putExtra(STEP_POSITION, holder.getAdapterPosition() - 1);
                            intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, mRecipe);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public String mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
