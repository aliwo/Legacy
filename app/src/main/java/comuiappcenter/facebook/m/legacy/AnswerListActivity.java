package comuiappcenter.facebook.m.legacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// 서버로 부터 답변의 개수를 받아와서 답변 개수 만큼 질문밑에 defaultTextBox를 추가해서
// 그 안에 답변 내용을 집어 넣어야 됨.
// 그리고 추천 버튼도 구현해야 됨;;;

public class AnswerListActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);
    }
}
