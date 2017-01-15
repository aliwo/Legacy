package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import comuiappcenter.facebook.m.legacy.customView.ClassList;
import comuiappcenter.facebook.m.legacy.customView.SearchList;

public class InterestedClassSearchActivity extends AppCompatActivity
{
    String Searchword = null;
    ListView listview;
    TextView mTextView;
    String[] className;
    String[] classProf;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_class_search);

        //검색어를 받아 옵니다.
        Intent intent =getIntent();
        if(intent !=null)
        {
            Searchword = intent.getStringExtra("SearchWord");
            Toast.makeText(this, Searchword + "로 검색한 결과입니다.", Toast.LENGTH_LONG).show();
        }

        //시험 삼아 모든 class를 불러 보겠습니다.
        className = new String[1000];
        classProf = new String[1000];
        INUClasses classes = new INUClasses();
        classes.saveClasses();

        for(int i = 0; i<classes.classes.length; i++)
        {
            className[i] = new String(classes.inuClasses[i].ClassName);
            //for문을 나가면 className이 증발한다...
        }

        for(int i =0; i<classes.profs.length; i++)
        {
            classProf[i] = classes.inuClasses[i].ProfName;
        }

        for(int i =0; i<classes.profs.length; i++)
        {
            Log.d("searchActivityClass", className[i]);
        }

        //ListView 생성
        SearchList adapter = new SearchList(this, R.layout.interested_class_list, className);
        listview = (ListView) findViewById(R.id.listview_search_class);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(adapter);

        //TextView에 넣어보기
        mTextView = (TextView) findViewById(R.id.textView4);
        mTextView.setText(className[3]);
    }


}
