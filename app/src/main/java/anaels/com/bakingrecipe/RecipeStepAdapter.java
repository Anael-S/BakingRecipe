package anaels.com.bakingrecipe;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import anaels.com.bakingrecipe.api.model.Step;

/**
 * Display the Step on recipe activity
 */
public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {
    private ArrayList<Step> listStep;
    private Activity mActivity;


    public RecipeStepAdapter(Activity activity, ArrayList<Step> listStep) {
        this.mActivity = activity;
        this.listStep = listStep;
    }

    @Override
    public RecipeStepAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_step, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //Text
        viewHolder.stepNumberTextView.setText(mActivity.getString(R.string.step_number, i+1));
        viewHolder.stepShortDescriptionTextView.setText(listStep.get(i).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return listStep.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepNumberTextView;
        TextView stepShortDescriptionTextView;

        public ViewHolder(View view) {
            super(view);
            stepNumberTextView = (TextView) view.findViewById(R.id.stepNumberTextView);
            stepShortDescriptionTextView = (TextView) view.findViewById(R.id.stepShortDescriptionTextView);
        }
    }

    public void setListStep(ArrayList<Step> listStep) {
        this.listStep = listStep;
    }
}