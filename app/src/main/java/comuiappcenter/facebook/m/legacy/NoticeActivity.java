package comuiappcenter.facebook.m.legacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class NoticeActivity extends AppCompatActivity
{
    RestClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        client = new RestClient(this);

        //공지사항을 서버에서 불러오자.
        //client.get(

        //slidingButton

    }
}
