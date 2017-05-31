package com.example.priyankanandiraju.mybakingtime.detailUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyankanandiraju.mybakingtime.R;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;
import com.example.priyankanandiraju.mybakingtime.recipe.Step;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String STEP_POSITION = "STEP_POSITION";

    /**
     * The dummy content this fragment is presenting.
     */
    private Recipe mItem;
    private Step mStep;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args.containsKey(ARG_ITEM_ID)) {
            mItem = args.getParcelable(ARG_ITEM_ID);
            if (args.containsKey(STEP_POSITION)) {
                int position = args.getInt(STEP_POSITION);
                mStep = mItem.getStepsList().get(position);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mStep != null) {
            ((TextView) rootView.findViewById(R.id.tv_step_description)).setText(mStep.getDescription());
        }

        return rootView;
    }
}
