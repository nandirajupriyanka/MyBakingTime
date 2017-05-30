package com.example.priyankanandiraju.mybakingtime;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankanandiraju on 5/30/17.
 */

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private List<Recipe> mRecipeList;
    @Nullable
    private OnRecipeClickListener mOnRecipeClickListener;

    public RecipeAdapter(ArrayList<Recipe> recipes, @Nullable OnRecipeClickListener onRecipeClickListener) {
        mRecipeList = recipes;
        mOnRecipeClickListener = onRecipeClickListener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        Recipe currentRecipe = mRecipeList.get(position);
        holder.setRecipeData(currentRecipe);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void setRecipeData(List<Recipe> recipeList) {
        mRecipeList.clear();
        mRecipeList.addAll(recipeList);
        notifyDataSetChanged();
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final String TAG = RecipeHolder.class.getSimpleName();

        @BindView(R.id.tv_recipe_name)
        TextView tvRecipeName;

        public RecipeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setRecipeData(Recipe currentRecipe) {
            tvRecipeName.setText(currentRecipe.getName());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Recipe currentRecipe = mRecipeList.get(pos);
            Log.v(TAG, "onClick() " + pos + " : " + currentRecipe.getName());
            if (mOnRecipeClickListener != null) {
                mOnRecipeClickListener.onRecipeClick(currentRecipe);
            }
        }
    }
}
