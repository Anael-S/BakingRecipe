package anaels.com.bakingrecipe.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import anaels.com.bakingrecipe.R;
import anaels.com.bakingrecipe.api.model.Step;

/**
 * Display the Step on recipe activity
 */
public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {
    private ArrayList<Step> listStep;
    private Activity mActivity;
    private final OnItemClickListener listener;

    private static final String RECIPE_INTRODUCTION_VIDEO = "Recipe Introduction";

    public interface OnItemClickListener {
        void onItemClick(Step item);
    }

    public RecipeStepAdapter(Activity activity, ArrayList<Step> listStep, OnItemClickListener listener) {
        this.mActivity = activity;
        this.listStep = listStep;
        this.listener = listener;
    }

    @Override
    public RecipeStepAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_step, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //Text
        //If this is the introduction step, we dont display the step number
        if (listStep.get(i).getDescription().equals(RECIPE_INTRODUCTION_VIDEO)){
            viewHolder.stepNumberTextView.setText("");
        } else {
            viewHolder.stepNumberTextView.setText(mActivity.getString(R.string.step_number, i));
        }
        viewHolder.stepShortDescriptionTextView.setText(listStep.get(i).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return listStep.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepNumberTextView;
        TextView stepShortDescriptionTextView;
        View mView;

        public ViewHolder(View view) {
            super(view);
            mView = itemView;
            stepNumberTextView = (TextView) view.findViewById(R.id.stepNumberTextView);
            stepShortDescriptionTextView = (TextView) view.findViewById(R.id.stepShortDescriptionTextView);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    if (listStep != null && position >= 0 && position <= listStep.size() - 1 && listStep.get(position) != null) {
                        listener.onItemClick(listStep.get(position));
                    }
                }
            });
        }
    }

    public void setListStep(ArrayList<Step> listStep) {
        this.listStep = listStep;
    }
}