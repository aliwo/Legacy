package comuiappcenter.facebook.m.legacy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017-01-03.
 */
public class userInfo extends AppCompatActivity
{
    TextView StudentIDText;
    EditText NickNameText;
    TextView MajorText;
    EditText InterestedClassText;
    Button SaveButton;
    RestClient client;

    //프리퍼런스의 이름은 "UserInfo" 이고 내부적으로 이곳에 사용자 정보가 저장됩니다. SplashActivity에 정의되어 있습니다.
    //2017-01-11 이 클래스를 액티비티로 바꿔서, 사용자가 자기 정보를 확인할 수 있도록 만들자.
    public static String StudentID;
    public static String Password;
    public static String NickName;

    public static String Name;
    public static String Year; //junior, senior, sophomore, freshman...
    public static String Major;
    public static String SecondMajor;

    public static ArrayList<String> InterestedClass = new ArrayList<String>(); // 나중에 이름이 같고 교수가 다른 수업을 다른 객체로 구분하게 될 수도 있기 때문에 중복을 허용하는 ArrayList로 일단 해둠.
    public static int LengthInterestedClass;
    public static boolean isOnline = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_activity);

        StudentIDText = (TextView) findViewById(R.id.student_id_textview);
        NickNameText = (EditText) findViewById(R.id.nickname_edittext);
        MajorText = (TextView) findViewById(R.id.major_textview);
        SaveButton = (Button) findViewById(R.id.savebutton_userinfo);

        StudentIDText.setText("학번: "+StudentID);
        NickNameText.setText(NickName);
        MajorText.setText("전공: "+Major);
        SaveButton.setOnClickListener(new onSaveButtonClicked());
        client = new RestClient(this);
    }

    public class onSaveButtonClicked implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
              SharedPreferences settings = getSharedPreferences(SplashActivity.PREFS_NAME, 0);
              SharedPreferences.Editor editor = settings.edit();
            editor.putString("NickName", NickNameText.getText().toString()); // 닉네임을 수정합니다.
            NickName = NickNameText.getText().toString();
            editor.commit(); // commit을 해야 값이 들어갑니다.
            makeParticle();
            Toast.makeText(getApplicationContext(), "닉네임이 수정되었습니다.", Toast.LENGTH_SHORT).show();

        }
    }

    public void makeParticle()
    {
        new ParticleSystem(this, 100, R.drawable.yellow_particle, 800)
                .setSpeedRange(0.1f, 0.25f)
                .oneShot(SaveButton, 100);
    }


    public static String randInterest()
    {

        for(int i=0; i<InterestedClass.size(); i++)
        {
            if(userInfo.InterestedClass.get(i) == null)
            {
                userInfo.LengthInterestedClass = i;
                i = InterestedClass.size(); // for 문을 빠져 나갑니다.
            }
        }
        Random random = new Random();
        int randInt = 1;
        if(InterestedClass.size() != 0) {randInt  = random.nextInt(InterestedClass.size());}

        return InterestedClass.get(randInt);
    }

}
