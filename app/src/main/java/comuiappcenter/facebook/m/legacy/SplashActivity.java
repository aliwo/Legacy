package comuiappcenter.facebook.m.legacy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView imageView;
    TextView textView;
    int randInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = (TextView) findViewById(R.id.text_splash);
        imageView = (ImageView) findViewById(R.id.imageView_splash);

        //스플래시 이미지 형성
        Random random = new Random();
        randInt = random.nextInt(4); // 0~4 까지 난수 생성.
        setImgages();

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
