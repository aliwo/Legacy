package comuiappcenter.facebook.m.legacy;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import comuiappcenter.facebook.m.legacy.customView.ClassList;
import comuiappcenter.facebook.m.legacy.customView.ClassSearchList;

import static comuiappcenter.facebook.m.legacy.SplashActivity.PREFS_NAME;

public class InterestedClassSettingActivity extends AppCompatActivity
{
    EditText SearchBar;
    ImageView SearchIcon;
    LinearLayout CurrentInterrestedClassLayout;
    ListView list;
    ListView searchList;
    SearchView searchView;
    ClassSearchList SearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_class_setting);
        setTitle("관심수업 검색하기");

        //현재 관심 수업 리스트를 가져옵니다.
        ClassList adapter = new ClassList(this, R.layout.interested_class_list, userInfo.InterestedClass); // 커스텀 리스트 어뎁터 생성!
        list = (ListView) findViewById(R.id.list_view); //액티비티.xml에서 선언했던 ListView container를 데려옴.
        list.setAdapter(adapter); //ListView에 어뎁터를 연결.
        list.setOnItemClickListener(adapter); //ListView에 리스너로도 등록.

        //검색 리스트를 선언합니다.
        SearchAdapter = new ClassSearchList(this, new INUClasses().classes);
        searchList = (ListView) findViewById(R.id.search_result_list);
        searchList.setAdapter(SearchAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_interested_bar, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("과목명을 검색해주세요");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String SearchWord = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) // suggestion 로직 구현
            {
                SearchAdapter.filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() // back키로 사용자가 돌아가면, SharedPreference에 관심수업 내용을 저장합니다.
    {
        super.onBackPressed();
        saveInterestedClass();
        Toast.makeText(InterestedClassSettingActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void saveInterestedClass()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        HashSet<String> InterrestedClassSet = new HashSet<String>();
        InterrestedClassSet.addAll(userInfo.InterestedClass);
        editor.putStringSet("InterestedClass", InterrestedClassSet).commit();
        /*Iterator<String> itr = InterrestedClassSet.iterator();
        while(itr.hasNext())
        {
            Log.d("kimchi_set", itr.next().toString());
        }*/
    }

}


