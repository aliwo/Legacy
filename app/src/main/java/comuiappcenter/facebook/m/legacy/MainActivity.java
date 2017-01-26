package comuiappcenter.facebook.m.legacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import comuiappcenter.facebook.m.legacy.customView.MainRecyclerAdapter;
import comuiappcenter.facebook.m.legacy.customView.QuestionPreview;
import comuiappcenter.facebook.m.legacy.dataContainer.MyData;
import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    LinearLayout questionsWaitingMeLayout;
    RestClient client;
    TextView NavNickName;
    View HeaderLayout;
    TextView moreWaitingQuestions;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<MyData> MyDataset;
    MyData myQuestionData;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        client = new RestClient(this);

        //floating 버튼을 구현합니다. 질문하기 버튼입니다.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        //NavigationDrawer를 구현합니다.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        HeaderLayout = navigationView.getHeaderView(0);
        NavNickName = (TextView) HeaderLayout.findViewById(R.id.nav_nick_name);
        if(userInfo.NickName != null) NavNickName.setText(sayHi()+" "+userInfo.NickName+"님");
        HeaderLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, userInfo.class);
                startActivity(intent);
            }
        });

        //RecyclerView와 CardView를 구현합니다.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_my_questions);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MyDataset = new ArrayList<>();
        mAdapter = new MainRecyclerAdapter(this, MyDataset);
        mRecyclerView.setAdapter(mAdapter);

        //내가한 질문들을 구현합니다. (서버에서 불러옵니다.)
        MyDataset.add(new MyData("내 질문들", new ArrayList<QuestionPreview>()));
        //서버와의 통신
        RequestParams params = new RequestParams();
        params.put("StudentID", userInfo.StudentID);
        client.post("/my_question", params, new JsonHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse)
            {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MainActivity.this, "내 질문을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) //서버 통신에 성공하면 내 질문들을 불러와요.
            {
                Toast.makeText(MainActivity.this, "검색 결과"+response.length()+"개", Toast.LENGTH_SHORT).show();
                for (int i = 0; i< response.length(); i++)
                {
                    try
                    {
                        JSONObject result = response.getJSONObject(i);
                        String title = result.getString("title");
                        String body = result.getString("body");
                        //서버에서 가져온 정보를 바탕으로 QuestionPreview 뷰를 만듭니다.
                        QuestionPreview question = new QuestionPreview(MainActivity.this);
                        question.title.setText(title);
                        question.body.setText(body);
                        //preview를 cardview 안에 넣습니다.
                        MainRecyclerAdapter.ViewHolder Viewholder = (MainRecyclerAdapter.ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(0);
                        Viewholder.mLinearLayout.addView(question);
                    }catch(JSONException e) {e.printStackTrace();}
                }
                super.onSuccess(statusCode, headers, response);
            }
        });


        //내 답변을 기다리는 질문들을 구현합니다. (서버에서 불러옵니다.)
        MyDataset.add(new MyData("나의 답변을 기다리는 질문들", new ArrayList<QuestionPreview>()));
        RequestParams params2 = new RequestParams();
        String ClassSent = userInfo.randInterest();
        params2.put("Interest", ClassSent); // 랜덤한 관심수업을 전송합니다.

        client.post("/waiting_question", params2, new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse)
            {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MainActivity.this, "답변을 기다리는 질문을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) //서버 통신에 성공하면 내 질문들을 불러와요.
            {
                Toast.makeText(MainActivity.this, "날 기다리는 질문"+response.length()+"개", Toast.LENGTH_SHORT).show();
                for (int i = 0; i< response.length(); i++)
                {
                    try
                    {
                        JSONObject result = response.getJSONObject(i);
                        String title = result.getString("title");
                        String body = result.getString("body");
                        final String QuestionID = result.getString("_id"); // 이런식으로 id를 가져올 수 있나? string이 아니던데
                        QuestionPreview question = new QuestionPreview(MainActivity.this);
                        question.title.setText(title);
                        question.body.setText(body);
                        question.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(MainActivity.this, QuestionViewActivity.class);
                                intent.putExtra("QuestionWas", QuestionID);
                                startActivity(intent);
                                // 나중에 margin도 설정해야 할듯.
                            }
                        });
                        //preview를 cardview 안에 넣습니다.
                        MainRecyclerAdapter.ViewHolder Viewholder = (MainRecyclerAdapter.ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(1);
                        Viewholder.mLinearLayout.addView(question);

                    }catch(JSONException e) {e.printStackTrace();}
                }
                super.onSuccess(statusCode, headers, response);
            }
        });


        Menu menuNav = navigationView.getMenu();
        for(int i=0; i<userInfo.InterestedClass.size(); i++)
        {
            if(userInfo.InterestedClass.get(i) != null)
            {
                menuNav.add(R.id.interested_class_menu, Menu.NONE, Menu.NONE, userInfo.InterestedClass.get(i));
            } //일단 item id는
        }
    }

    @Override
    public void onBackPressed() // Back키를 눌렀을때 만약 drawer가 열려 있으면 drawer를 닫습니다.
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id != R.id.nav_interestd_class_setting && id != R.id.nav_setting && id != R.id.nav_logout) // 동적으로 생성된 아이템이 아니라면
        {
            Intent intent = new Intent(MainActivity.this, EclassActivity.class);
            intent.putExtra("ClassName",item.getTitle());
            startActivity(intent);
        }

        //동적으로 변하지 않는 Navigation Menu
        if (id == R.id.nav_interestd_class_setting)
        {
            Intent intent = new Intent(this, InterestedClassSettingActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_setting)
        {
            Intent intent = new Intent(this, OptionsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_logout)
        {
            //서버에 logout요청을 한 뒤 성공하면 preference를 모두 지우고, login 화면으로 이동합니다.
            client = new RestClient(MainActivity.this);
            client.get("/logout", null, new TextHttpResponseHandler()
            {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
                {
                    Toast.makeText(MainActivity.this, "서버에 요청 실패", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString)
                {
                    MainActivity.this.getSharedPreferences(SplashActivity.PREFS_NAME, 0).edit().clear().commit();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String sayHi()
    {
        String[] greeting = new String[5];
        int rand = new Random().nextInt(5);
        greeting[0] = "환영합니다";
        greeting[1] = "오늘 하루도 화이팅!";
        greeting[2] = "잠깐 쉬고 공부해도 괜찮아요";
        greeting[3] = "힘차게 공부해 볼까요? :)";
        greeting[4] = "맛있는 거 먹고 공부 시작할까요?";

        return greeting[rand];
    }

}
