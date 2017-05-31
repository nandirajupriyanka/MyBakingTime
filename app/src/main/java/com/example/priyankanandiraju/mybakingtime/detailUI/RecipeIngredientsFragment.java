package com.example.priyankanandiraju.mybakingtime.detailUI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.priyankanandiraju.mybakingtime.R;
import com.example.priyankanandiraju.mybakingtime.recipe.Ingredient;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.priyankanandiraju.mybakingtime.detailUI.RecipeDetailFragment.ARG_ITEM_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFragment extends Fragment {

    @BindView(R.id.grid_ingredients)
    GridView gridIngredients;
    private Recipe mItem;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args.containsKey(ARG_ITEM_ID)) {
            mItem = args.getParcelable(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, view);
        if (mItem != null) {
            List<Ingredient> ingredients = mItem.getIngredientsList();
            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients);
            gridIngredients.setAdapter(ingredientsAdapter);
        }
        return view;
    }

    class IngredientsAdapter extends BaseAdapter {

        @BindView(R.id.tv_ingredient)
        TextView tvIngredient;
        @BindView(R.id.tv_measure)
        TextView tvMeasure;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        private List<Ingredient> mIngredientList;

        public IngredientsAdapter(List<Ingredient> ingredientsList) {
            mIngredientList = ingredientsList;
        }

        @Override
        public int getCount() {
            return mIngredientList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients_row, null);
            }

            ButterKnife.bind(this, convertView);

            Ingredient currentItem = mIngredientList.get(position);

            tvMeasure.setText(currentItem.getMeasure());
            String quantity = String.valueOf(currentItem.getQuantity());
            tvQuantity.setText(quantity);
            tvIngredient.setText(currentItem.getIngredient());
            return convertView;
        }
    }

}
