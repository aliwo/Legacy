package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuestionListActivity extends AppCompatActivity
//질문들 더보기를 누르면 무조건 이 엑티비티로 들어온다.
{
    String QuestionList;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Intent intent = getIntent();
        QuestionList = intent.getStringExtra("QuestionList");

    }



}
