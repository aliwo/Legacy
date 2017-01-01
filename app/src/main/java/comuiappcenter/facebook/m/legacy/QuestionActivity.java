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
        acceptButton.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            defaultTextBox titleBox = (defaultTextBox) findViewById(R.id.title_box);
            defaultTextBox bodyBox = (defaultTextBox) findViewById(R.id.body_box);

            if(titleBox != null)
            {
                String title = titleBox.mEditText.getText().toString();
                String body = bodyBox.mEditText.getText().toString();
                Toast.makeText(v.getContext(), "제목: "+title+"\n본문: "+body, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(v.getContext(), "객체가 없는뎁쇼?", Toast.LENGTH_LONG).show();
            }
        }
    }
}
