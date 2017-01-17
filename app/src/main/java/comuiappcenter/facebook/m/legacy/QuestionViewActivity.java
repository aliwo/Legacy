package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comuiappcenter.facebook.m.legacy.customView.QuestionPreview;
import cz.msebera.android.httpclient.Header;

public class QuestionViewActivity extends AppCompatActivity
{
    defaultTextBox Questiontitle;
    defaultTextBox Questionbody;
    RelativeLayout AnswerButton;
    RelativeLayout middleLayout;
    RestClient client;
    String QuestionWas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_view);
        client = new RestClient(this);

        //어떤 질문을 클릭해서 들어왔는지 알아냅니다.
        Intent intent = getIntent();
        QuestionWas = intent.getStringExtra("QuestionWas");

        //View를 선언합니다.
        Questiontitle = (defaultTextBox) findViewById(R.id.title_question_view);
        Questionbody = (defaultTextBox) findViewById(R.id.body_question_view);
        AnswerButton = (RelativeLayout) findViewById(R.id.button_question_view);
        middleLayout = (RelativeLayout) findViewById(R.id.middle_relativelayout_question_view);

        //서버에 보낼 질문의 id를 param에 담습니다.
        RequestParams params = new RequestParams();
        params.put("QuestionID", QuestionWas);

        //질문 내용을 서버에서 가져옵니다.
        client.post("/find_a_question", params, new JsonHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse)
            {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(QuestionViewActivity.this, "질문 내용을 서버로 부터 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
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

        //현재까지 해당 질문에 달린 답변 list를 불러 옵니다.

        //client.post("/find_answers", params)

        /*ListView를 만들고, 커스텀 뷰를 한 단씩 추가하자.*/
    }
}
