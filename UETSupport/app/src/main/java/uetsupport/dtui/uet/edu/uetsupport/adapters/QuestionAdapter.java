package uetsupport.dtui.uet.edu.uetsupport.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uetsupport.dtui.uet.edu.uetsupport.MainActivity;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.dialog.AnswerDialog;
import uetsupport.dtui.uet.edu.uetsupport.models.Question;

/**
 * Created by huylv on 08/12/2015.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> implements Filterable{

    private List<Question> orig;
    Context context;
    ArrayList<Question> questionArrayList;

    public QuestionAdapter(Context context, ArrayList questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        QuestionViewHolder qvh = new QuestionViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.tvQuestion.setText(questionArrayList.get(position).getQuestion());
        holder.llQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerDialog ad = new AnswerDialog(context, questionArrayList.get(position));
                ad.show(((MainActivity) context).getFragmentManager(), "FTAG");
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llQuestion;
        TextView tvQuestion;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            llQuestion = (LinearLayout) itemView.findViewById(R.id.llQuestion);
            tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
        }

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Question> results = new ArrayList<Question>();
                if (orig == null)
                    orig = questionArrayList;
                if (constraint != null) {
                    if (orig != null & orig.size() > 0) {
                        for (final Question g : orig) {
                            if (g.getQuestion().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                questionArrayList = (ArrayList<Question>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}
