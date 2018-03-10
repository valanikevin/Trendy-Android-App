package com.aiora.trendy.quiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiora.trendy.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by HOME on 10-03-2018.
 */

public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.ViewHolder> {

    ArrayList<FetchQuizData> quizData;
    Context context;

    public QuizRecyclerViewAdapter(ArrayList<FetchQuizData> quizData, Context context) {
        this.quizData = quizData;
        this.context = context;
    }

    @Override
    public QuizRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_quiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.question.setText(quizData.get(position).getQuestion());
        holder.answer.setText(quizData.get(position).getAnswer());
        holder.explain.setText(quizData.get(position).getExplain());
        holder.optionOne.setText(quizData.get(position).getOption1());
        holder.optionTwo.setText(quizData.get(position).getOption2());
        holder.optionThree.setText(quizData.get(position).getOption3());


        float total = quizData.get(position).getResTrue()
                + quizData.get(position).getResFalse()
                + quizData.get(position).getResClose();

        float onePer = quizData.get(position).getResTrue() / total;
        float twoPer = quizData.get(position).getResFalse() / total;
        float threePer = quizData.get(position).getResClose() / total;

        DecimalFormat df = new DecimalFormat("##.#%");
        String per = df.format(onePer);//onePer + "%";
        holder.onePercentage.setText(per);
        per = df.format(twoPer);
        holder.twoPercentage.setText(per);
        per = df.format(threePer);
        holder.threePercentage.setText(per);

        onePer = onePer * 100;
        twoPer = twoPer * 100;
        threePer = threePer * 100;

        holder.oneFill.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT, 100 - onePer));
        holder.oneNoFill.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT, onePer));
        holder.twoFill.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT, 100 - twoPer));
        holder.twoNoFill.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT, twoPer));
        holder.threeFill.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT, 100 - threePer));
        holder.threeNoFill.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT, threePer));

    }

    @Override
    public int getItemCount() {
        return quizData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView question;
        TextView answer;
        TextView explain;
        TextView optionOne;
        TextView optionTwo;
        TextView optionThree;
        TextView onePercentage;
        TextView twoPercentage;
        TextView threePercentage;
        View oneFill;
        View oneNoFill;
        View twoFill;
        View twoNoFill;
        View threeFill;
        View threeNoFill;

        public ViewHolder(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.quizQuestion);
            answer = itemView.findViewById(R.id.quizAnswer);
            explain = itemView.findViewById(R.id.quizExplain);
            optionOne = itemView.findViewById(R.id.quizOptionOne);
            optionTwo = itemView.findViewById(R.id.quizOptionTwo);
            optionThree = itemView.findViewById(R.id.quizOptionThree);

            onePercentage = itemView.findViewById(R.id.quizOptionOnePercentage);
            twoPercentage = itemView.findViewById(R.id.quizOptionTwoPercentage);
            threePercentage = itemView.findViewById(R.id.quizOptionThreePercentage);

            oneFill = itemView.findViewById(R.id.quizOptionOneFill);
            oneNoFill = itemView.findViewById(R.id.quizOptionOneNoFill);
            twoFill = itemView.findViewById(R.id.quizOptionTwoFill);
            twoNoFill = itemView.findViewById(R.id.quizOptionTwoNoFill);
            threeFill = itemView.findViewById(R.id.quizOptionThreeFill);
            threeNoFill = itemView.findViewById(R.id.quizOptionThreeNoFill);
        }
    }

}
