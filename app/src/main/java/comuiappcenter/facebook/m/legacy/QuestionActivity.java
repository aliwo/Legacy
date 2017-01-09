package comuiappcenter.facebook.m.legacy;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/* 질문을 서버에 등록하는 액티비티입니다. 질문의 key 이름은 제목 -> QuestionTitle 본문 -> QuestionBody 입니다.
* */

public class QuestionActivity extends AppCompatActivity
{
    View acceptButton;
    RestClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        acceptButton = (RelativeLayout) findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            defaultTextBox titleBox = (defaultTextBox) findViewById(R.id.title_box);
            defaultTextBox bodyBox = (defaultTextBox) findViewById(R.id.body_box);

            if(titleBox != null) // 제목과 질문 내용을 변수에 담아 서버에 전송.
            {
                String title = titleBox.mEditText.getText().toString();
                String body = bodyBox.mEditText.getText().toString();
                Toast.makeText(v.getContext(), "제목: "+title+"\n본문: "+body, Toast.LENGTH_LONG).show();

                //파라미터 안에 내용을 담습니다.
                RequestParams params = new RequestParams();
                params.put("QuestionTitle", title);
                params.put("QuestionBody", body);
                params.put("UserStudentID", userInfo.StudentID);
                params.put("UserNickName", userInfo.NickName);
                params.put("submitTime", System.currentTimeMillis());

                //질문을 서버로 전송
                client.post("/question_submit", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                    {
                        Toast.makeText(QuestionActivity.this, "질문 등록 성공!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                    {
                        Toast.makeText(QuestionActivity.this, "질문 등록 실패", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {  Toast.makeText(v.getContext(), "객체가 없습니다.", Toast.LENGTH_LONG).show();}
        }
    }
}
