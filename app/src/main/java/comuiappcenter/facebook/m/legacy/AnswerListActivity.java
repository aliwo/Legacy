package comuiappcenter.facebook.m.legacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

// 서버로 부터 답변의 개수를 받아와서 답변 개수 만큼 질문밑에 defaultTextBox를 추가해서
// 그 안에 답변 내용을 집어 넣어야 됨.
// 그리고 추천 버튼도 구현해야 됨;;;

public class AnswerListActivity extends AppCompatActivity
{
    RestClient client = new RestClient(this);
    defaultTextBox titleBox = (defaultTextBox) findViewById(R.id.title_box_answerList); // 질문 제목
    defaultTextBox bodyBox = (defaultTextBox) findViewById(R.id.body_box_answerList); // 질문 본문
    defaultTextBox[] answerlist_title; // 답변들의 제목 상자 배열
    defaultTextBox[] answerlist_body; //  답변들의 본문 상자 배열
    int NumOfAnswers = 0;
    LinearLayout answerZone = (LinearLayout) findViewById(R.id.answer_zone_linear);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);

        client.get("/answers", null, new TextHttpResponseHandler()  // 질문에 달린 답변이 있는지 확인합니다.
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                Toast.makeText(getApplicationContext(), "죄송합니다. 서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                //response로 문자열이 아니라 JSON 객체 같은걸 받아야 파싱하기 편할 듯.
                //boardschema에서 답변 배열을 읽고, 배열의 길이가 1이상이면 (답변이 있으면)
                //답변의 개수만큼 답변 제목과 본문 상자를 동적으로 할당하고, 그 안에 내용을 집어넣는다.

            }
        });
        //먼저 코드로 답변 제목과 본문 상자를 할당해보자.
        NumOfAnswers = 2; // 2개의 답변이 달렸다고 가정.
        for(int i=0; i< NumOfAnswers; i++)
        {
            answerlist_title[i] = new defaultTextBox(this);
            answerlist_body[i] = new defaultTextBox(this);
            answerZone.addView(answerlist_title[i]);
            answerZone.addView(answerlist_body[i]);
            //이제 서버로 부터 받아온 문자열을 answerList_title에 할당.
        }


        //서버에서 질문 작성자를 가져온 후 userInfo의 학번과 질문 작성자의 학번이 같으면 '답변하기' 버튼을 불러오지 않습니다.
        //만약 질문 작성자와 다른 사람이 Answerslist를 호출하면 답변하기 버튼을 코드로 동적 생성.


    }
}
