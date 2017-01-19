package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AnswerActivity extends AppCompatActivity
{
    defaultTextBox Questiontitle;
    defaultTextBox Questionbody;
    defaultTextBox Answertitle;
    defaultTextBox Answerbody;
    ImageView AcceptButton;
    String QuestionWas;
    RestClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        //각종 View를 선언합니다.
        Questiontitle = (defaultTextBox) findViewById(R.id.title_answer_activity);
        Questionbody = (defaultTextBox) findViewById(R.id.body_answer_activity);
        Answertitle = (defaultTextBox) findViewById(R.id.title_answer_textview);
        Answerbody = (defaultTextBox) findViewById(R.id.answer_text_body);
        AcceptButton = (ImageView) findViewById(R.id.accept_button_answer_activity);

        //질문 내용을 가져옵니다.
        Intent intent = getIntent();
        QuestionWas = intent.getStringExtra("QuestionWas");

        RequestParams params = new RequestParams();
        params.put("QuestionID", QuestionWas);

        client.post("/find_a_question", params, new JsonHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse)
            {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AnswerActivity.this, "질문 내용을 서버로 부터 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) //서버 통신에 성공하면 내 질문들을 불러와요.
            {
                for (int i = 0; i< response.length(); i++)
                {
                    try
                    {
                        JSONObject result = response.getJSONObject(i);
                        String title = result.getString("title"); // 질문 제목
                        String body = result.getString("body"); // 질문 내용
                        Questiontitle.mEditText.setText(title);
                        Questionbody.mEditText.setText(body);

                    }catch(JSONException e) {e.printStackTrace();}
                }
                super.onSuccess(statusCode, headers, response);
            }
        });

        //답변을 다 작성한 다음에 확인 버튼을 누르면 답변을 전송합니다.
        AcceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String AnswerTitleContent = Answertitle.mEditText.getText().toString();
                String AnswerBodyContent = Answerbody.mEditText.getText().toString();

                RequestParams params = new RequestParams();
                params.put("QuestionID", QuestionWas);
                params.put("AnswerTitle", AnswerTitleContent);
                params.put("AnswerBody", AnswerBodyContent);
                params.put("AnswerAuthor", userInfo.StudentID);

                client.post("/new_answer", params, new TextHttpResponseHandler()
                {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
                    {
                        Toast.makeText(getApplicationContext(), "서버 연결이 불안정합니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString)
                    {
                        Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();
                        Intent goback = new Intent(AnswerActivity.this, QuestionViewActivity.class);
                        goback.putExtra("QuestionWas", QuestionWas);
                        startActivity(goback); // 나중에 pendingIntent 라던지 우아한 방식으로 바꾸기 *?
                    }
                });
            }
        });




    }
}
