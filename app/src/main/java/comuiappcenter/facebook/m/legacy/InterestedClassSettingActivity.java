package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import comuiappcenter.facebook.m.legacy.customView.ClassList;

public class InterestedClassSettingActivity extends AppCompatActivity
{
    EditText SearchBar;
    ImageView SearchIcon;
    LinearLayout CurrentInterrestedClassLayout;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_class_setting);

        //검색바와 검색 아이콘을 구현합니다.
        SearchBar = (EditText) findViewById(R.id.search_bar_interested_class);
        SearchIcon = (ImageView) findViewById(R.id.image_view_interested_class);
        SearchIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) // 검색어를 가지고 검색 화면으로 intent합니다.
            {
                String SearchWord = SearchBar.getText().toString();
                if(SearchWord == null)  // 검색어가 있는지 없는지 확인합니다... 작동이 안되는데?
                {
                    Toast.makeText(InterestedClassSettingActivity.this, "검색어를 입력해 주세요 :)", Toast.LENGTH_SHORT).show();
                    return;
                }
                else // 검색어를 가지고 인텐트
                {
                    Intent intent = new Intent(InterestedClassSettingActivity.this, InterestedClassSearchActivity.class);
                    intent.putExtra("SearchWord", SearchWord);
                    startActivity(intent);
                }

            }
        });

        //현재 관심 수업 리스트를 가져옵니다.
        ClassList adapter = new ClassList(this, R.layout.interested_class_list, userInfo.InterestedClass); // 커스텀 리스트 어뎁터 생성!
        list = (ListView) findViewById(R.id.list_view); //액티비티.xml에서 선언했던 ListView container를 데려옴.
        list.setAdapter(adapter); //ListView에 어뎁터를 연결.
        list.setOnItemClickListener(adapter); //ListView에 리스너로도 등록.

    }
}
