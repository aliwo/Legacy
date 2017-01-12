package comuiappcenter.facebook.m.legacy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

//test 라는 패스로 get 요청을 해서 서버 접속 여부를 확인합니다.

public class SplashActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView imageView;
    TextView textView;
    TextView bottomText;
    int randInt = 0;
    RestClient client;
    public static final String PREFS_NAME = "UserInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = (TextView) findViewById(R.id.text_splash);
        imageView = (ImageView) findViewById(R.id.imageView_splash);
        bottomText =  (TextView) findViewById(R.id.textView_splash_bottom);

        //스플래시 이미지 형성
        Random random = new Random();
        randInt = random.nextInt(4); // 0~4 까지 난수 생성.
        setImgages();

        //서버 연결 확인
        client = new RestClient(this); // RestClient 객체 생성.
        client.get("/test", null, new TextHttpResponseHandler()
        {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
                    {
                        Toast.makeText(getApplicationContext(), "서버 접속 실패"+client.BASE_URL, Toast.LENGTH_SHORT).show();
                        bottomText.setText("서버접속 실패"+client.BASE_URL);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString)
                    {
                        Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();
                        bottomText.setText("서버접속 성공"+client.BASE_URL);
                        userInfo.isOnline = true;
                    }
        });

        //저장된 로그인 정보가 있는지 확인합니다. 만약 없으면 LoginActivity로 intent 됩니다.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        userInfo.StudentID = settings.getString("StudentID", null);
        userInfo.Password = settings.getString("Password", null);
        userInfo.NickName = settings.getString("NickName", null);
        if(userInfo.StudentID == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (userInfo.StudentID != null) // 만약 학번이 존재하면 로그인을 시도합니다.
        {
            Toast.makeText(getApplicationContext(), userInfo.StudentID+"와"+"\n 비밀번호:"+userInfo.Password+"로 로그인을 시도합니다.", Toast.LENGTH_SHORT).show();
            RequestParams params = new RequestParams();
            params.put("StudentID", userInfo.StudentID);
            params.put("Password", userInfo.Password);
            client.post("/login", params, new TextHttpResponseHandler() // 로그인 요청 전송
            {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
                {
                    bottomText.setText("자동 로그인 실패");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString)
                {
                    Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();
                }
            });
        }

        //클릭하면 intent
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setImgages()
    {
        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.splash_quotes);
        // 문자열 배열을 가져오려면 Resources 객체도 필요하고, 참조할때 array로 시작해야 됨 string으로 시작하면 안됨.

        switch(randInt)
        {
            case 0: imageView.setImageResource(R.drawable.dark_history); textView.setText(quotes[0]); break;
            case 1: imageView.setImageResource(R.drawable.cozy_room1); textView.setText(quotes[1]); break;
            case 2: imageView.setImageResource(R.drawable.cozy_room2); textView.setText(quotes[2]); break;
            case 3: imageView.setImageResource(R.drawable.cozy_room3); textView.setText(quotes[3]); break;
            case 4: imageView.setImageResource(R.drawable.cozy_room4); textView.setText(quotes[4]); break;
        }
    }




}
