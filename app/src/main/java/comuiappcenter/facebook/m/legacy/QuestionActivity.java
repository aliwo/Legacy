package comuiappcenter.facebook.m.legacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity
{
    View acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        acceptButton = (RelativeLayout) findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionActivity.this, "버튼 눌렸습니다.", Toast.LENGTH_LONG).show();

            }
        });
    }
}
