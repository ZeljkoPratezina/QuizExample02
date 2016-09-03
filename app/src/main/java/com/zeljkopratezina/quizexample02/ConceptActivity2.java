package com.zeljkopratezina.quizexample02;
/**
 * Created by ZELJKO on 8/23/2016.
 */
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.zeljkopratezina.quizexample02.db.DBAdapter2;
import com.zeljkopratezina.quizexample02.model.Question;

import java.util.ArrayList;
import java.util.List;

public class ConceptActivity2 extends AppCompatActivity {

    private List<Question> questionsList;
    private Question currentQuestion;

    private TextView txtQuestion,tvNoOfQs;
    private RadioButton rbtnE, rbtnF, rbtnG,rbtnH;
    private Button btnNext;

    private int obtainedScore=0;
    private int questionId=0;

    private int answeredQsNo=0;

    ArrayList<String> myAnsList2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concept2);

        AdView mAdView2 = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView2.loadAd(adRequest);


        //Initialize the view
        init();

        //Initialize the database
        final DBAdapter2 dbAdapter=new DBAdapter2(this);
        questionsList= dbAdapter.getAllQuestions();
        currentQuestion=questionsList.get(questionId);

        //Set question
        setQuestionsView();

        //Check and Next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup grp2=(RadioGroup)findViewById(R.id.radioGroup);
                RadioButton answer2=(RadioButton)findViewById(grp2.getCheckedRadioButtonId());

                Log.e("Answer ID", "Selected Positioned  value - "+grp2.getCheckedRadioButtonId());

                if(answer2!=null){
                    Log.e("Answer", currentQuestion.getANSWER() + " -- " + answer2.getText());
                    //Add answer to the list
                    myAnsList2.add(""+answer2.getText());

                    if(currentQuestion.getANSWER().equals(answer2.getText())){
                        obtainedScore++;
                        Log.e("comments", "Correct Answer");
                        Log.d("score", "Obtained score " + obtainedScore);
                    }else{
                        Log.e("comments", "Wrong Answer");
                    }
                    if(questionId<dbAdapter.rowCount()){
                        currentQuestion=questionsList.get(questionId);
                        setQuestionsView();
                    }else{
                        Intent intent2 = new Intent(ConceptActivity2.this, ResultActivity.class);

                        Bundle b = new Bundle();
                        b.putInt("score", obtainedScore);
                        b.putInt("totalQs", questionsList.size());
                        b.putStringArrayList("myAnsList", myAnsList2);
                        intent2.putExtras(b);
                        startActivity(intent2);
                        finish();

                    }

                }else{
                    Log.e("comments", "No Answer");
                }

                //Need to clear the checked item id
                grp2.clearCheck();


            }//end onClick Method
        });


    }

    public void init(){
        tvNoOfQs=(TextView)findViewById(R.id.tvNumberOfQuestions);
        txtQuestion=(TextView)findViewById(R.id.tvQuestion);
        rbtnE=(RadioButton)findViewById(R.id.radio0);
        rbtnF=(RadioButton)findViewById(R.id.radio1);
        rbtnG=(RadioButton)findViewById(R.id.radio2);
        rbtnH=(RadioButton)findViewById(R.id.radio3);

        btnNext=(Button)findViewById(R.id.btnNext);

        myAnsList2 = new ArrayList<String>();
    }


    private void setQuestionsView()
    {
        rbtnE.setChecked(false);
        rbtnF.setChecked(false);
        rbtnG.setChecked(false);
        rbtnH.setChecked(false);

        answeredQsNo=questionId+1;
        tvNoOfQs.setText("Questions "+answeredQsNo+" of "+questionsList.size());

        txtQuestion.setText(currentQuestion.getQUESTION());
        rbtnE.setText(currentQuestion.getOptionA());
        rbtnF.setText(currentQuestion.getOptionB());
        rbtnG.setText(currentQuestion.getOptionC());
        rbtnH.setText(currentQuestion.getOptionD());

        questionId++;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}