package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comuiappcenter.facebook.m.legacy.customView.AnswerList;
import comuiappcenter.facebook.m.legacy.customView.ClassList;
import comuiappcenter.facebook.m.legacy.customView.QuestionPreview;
import cz.msebera.android.httpclient.Header;

public class QuestionViewActivity extends AppCompatActivity
{
    defaultTextBox Questiontitle;
    TextView Questionbody;
    RelativeLayout AnswerButton;
    RelativeLayout middleLayout;
    ListView mAnswerList;
    RestClient client;
    String QuestionWas;
    JSONArray AnswerJSONArray;
    String[] Answertitle;
    String[] Answerbody;
    String[] AnswerAuthor;

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
        Questionbody = (TextView) findViewById(R.id.body_question_view);
        AnswerButton = (RelativeLayout) findViewById(R.id.button_question_view);
        middleLayout = (RelativeLayout) findViewById(R.id.middle_relativelayout_question_view);
        mAnswerList = (ListView) findViewById(R.id.listview_question_view);
        Answertitle = new String[100]; // 댓글 수 제한 100개
        Answerbody = new String[100];
        AnswerAuthor = new String[100];

        //서버에 보낼 질문의 id를 param에 담습니다.
        RequestParams params = new RequestParams();
        params.put("QuestionID", QuestionWas);

        //질문과 해당 질문에 현재까지 달린 답변들을 서버에서 가져옵니다.
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
                    try
                    {
                        JSONObject result = response.getJSONObject(0); // 어짜피 가져오는 JSON객체는 하나임.
                        String title = result.getString("title"); // 질문 제목
                        String body = result.getString("body"); // 질문 내용
                        Questiontitle.mEditText.setText(title);
                        Questionbody.setText(body);
                        AnswerJSONArray = result.getJSONArray("answer");//답변 내용을 가져옵니다.

                        //서버에서 받은 답변을 가지고 list를 구현합니다.
                        for(int i =0; i<AnswerJSONArray.length(); i++)
                        {
                            try
                            {
                                JSONObject Answer = AnswerJSONArray.getJSONObject(i);
                                Answertitle[i] = Answer.getString("title");
                                Answerbody[i] = Answer.getString("contents");
                                AnswerAuthor[i] = Answer.getString("writer");
                            } catch (JSONException e){e.printStackTrace(); }
                        }

                        AnswerList adapter = new AnswerList(QuestionViewActivity.this, R.layout.list_answer, Answertitle, Answerbody, AnswerAuthor); // 커스텀 리스트 어뎁터 생성!
                        mAnswerList.setAdapter(adapter); //ListView에 어뎁터를 연결.
                        mAnswerList.setOnItemClickListener(adapter); //ListView에 리스너로도 등록.
                        mAnswerList.setVisibility(View.VISIBLE);

                    }catch(JSONException e) {e.printStackTrace();}
                super.onSuccess(statusCode, headers, response);
            }
        });



        //Answer버튼 이벤트 처리. 버튼을 누르면 답변 화면으로 넘어갑니다.
        AnswerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent goAnswer = new Intent(QuestionViewActivity.this, AnswerActivity.class);
                goAnswer.putExtra("QuestionWas", QuestionWas);
                startActivity(goAnswer);
            }
        });




    }
}
