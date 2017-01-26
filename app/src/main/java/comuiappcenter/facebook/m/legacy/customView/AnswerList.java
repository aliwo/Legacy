package comuiappcenter.facebook.m.legacy.customView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

import org.w3c.dom.Text;

import comuiappcenter.facebook.m.legacy.R;

/**
 * Created by Administrator on 2017-01-19.
 */
public class AnswerList extends ArrayAdapter<String > implements AdapterView.OnItemClickListener
{
    //2016-12-11 완전히 독립적인 CustomList 생성 성공!

    String[] titles;
    String[] bodies;
    String[] authors;
    Context mcontext;
    TextView title;
    TextView body;
    TextView author;
    ImageView thumbsUp;

    public AnswerList(Context context, int resId, String[] items, String[] body, String[] author)
    {
        super(context, resId, items);
        mcontext = context;
        titles = items; // InterestedClass 들이 들어올 거임.
        bodies = body;
        authors = author;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        //if (titles[position] == null) {position == titles.length;}
        View v = convertView;
        if(v == null) // View가 없다면 만들어야된다.
        {
            LayoutInflater vi= LayoutInflater.from(getContext());
            //무조건 LayoutInflater 를 엄마 액티비티로 부터 데려와야 됨
            // 수정하지 말아라 이거 고치느라 엄청 애먹음 ㅋㅋ
            v = vi.inflate(R.layout.list_answer, null);
            //convertView = View.inflate(mcontext, R.layout.list_item, parent);
        }

        title = (TextView) v.findViewById(R.id.title_answer_list);
        body = (TextView) v.findViewById(R.id.body_answer_list);
        author = (TextView) v.findViewById(R.id.author_answer_list);
        thumbsUp = (ImageView) v.findViewById(R.id.answer_list_thumbs_up_button);

        title.setText(titles[position]);
        body.setText(bodies[position]);
        author.setText("작성자: "+authors[position]);

        //추천 버튼 구현
        thumbsUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mcontext.getApplicationContext(), "춫천", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast.makeText(mcontext.getApplicationContext(),
                titles[position],
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCount()
    {
        int count=0;
        for(int i=0; i < titles.length; i++)
        {
            if(titles[i] != null)
            {
                ++count;
            }
        }
        return count;
    }
}
