package com.example.priyankanandiraju.mybakingtime.detailUI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyankanandiraju.mybakingtime.R;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.priyankanandiraju.mybakingtime.detailUI.RecipeDetailFragment.ARG_ITEM_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFragment extends Fragment {


    @BindView(R.id.tv_ingredient)
    TextView tvIngredient;
    @BindView(R.id.tv_measure)
    TextView tvMeasure;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
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
            // TODO: 5/30/17 Get all ingredients
            tvMeasure.setText(mItem.getIngredientsList().get(0).getMeasure());
            String quantity = String.valueOf(mItem.getIngredientsList().get(0).getQuantity());
            tvQuantity.setText(quantity);
            tvIngredient.setText(mItem.getIngredientsList().get(0).getIngredient());
        }
        return view;
    }

}
