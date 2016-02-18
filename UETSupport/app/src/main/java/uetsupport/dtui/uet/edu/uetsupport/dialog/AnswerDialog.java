package uetsupport.dtui.uet.edu.uetsupport.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.models.Question;

/**
 * Created by huylv on 15-Dec-15.
 */
public class AnswerDialog extends BlurDialogFragment {

    TextView tvAnswerContent;
    Button btOk;
    Context context;
    Question question;
    TextView tvAnswerTitle;
    public AnswerDialog(){}

    public AnswerDialog(Context c, Question q){
        context=c;
        question=q;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_answer,container,false);

        tvAnswerTitle = (TextView)v.findViewById(R.id.tvAnswerTitle);
        tvAnswerTitle.setText(question.getQuestion());

        tvAnswerContent = (TextView)v.findViewById(R.id.tvAnswerContent);
        btOk = (Button)v.findViewById(R.id.btAnswerOk);

        tvAnswerContent.setText(question.getAnswer());

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return v;

    }


}

