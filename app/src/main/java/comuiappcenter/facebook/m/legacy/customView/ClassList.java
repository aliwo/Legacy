package comuiappcenter.facebook.m.legacy.customView;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import comuiappcenter.facebook.m.legacy.userInfo;

import java.util.List;

import comuiappcenter.facebook.m.legacy.R;

/**
 * Created by Administrator on 2017-01-13.
 */
public class ClassList extends ArrayAdapter<String > implements AdapterView.OnItemClickListener
{
    //2016-12-11 완전히 독립적인 CustomList 생성 성공!

        List<String> titles;
        Context mcontext;
        TextView title;
        ImageView icon;

        public ClassList(Context context, int resId, List<String> items)
        {
            super(context, resId, items);
            mcontext = context;
            titles = items; // InterestedClass 들이 들어올 거임.
        }

        @Override
        public View getView (final int position, View convertView, ViewGroup parent)
        {
            //if (titles[position] == null) {position == titles.length;}
            View v = convertView;
            if(v == null) // View가 없다면 만들어야된다.
            {
                LayoutInflater vi= LayoutInflater.from(getContext());
                //무조건 LayoutInflater 를 엄마 액티비티로 부터 데려와야 됨
                // 수정하지 말아라 이거 고치느라 엄청 애먹음 ㅋㅋ
                v = vi.inflate(R.layout.interested_class_list, null);
                //convertView = View.inflate(mcontext, R.layout.list_item, parent);
            }

            title = (TextView) v.findViewById(R.id.textview_interested_class_list);
            icon = (ImageView) v.findViewById(R.id.image_view_interested_class_list);
            title.setText(titles.get(position));
            setappearFromLeftAnimation(v);
            v.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    userInfo.InterestedClass.remove(position);
                    notifyDataSetChanged();
                }
            });

            return v;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Toast.makeText(mcontext.getApplicationContext(),
                    titles.get(position),
                    Toast.LENGTH_SHORT).show();
        }

    @Override
    public int getCount()
    {
        int count=0;
        for(int i=0; i < titles.size(); i++)
        {
            if(titles.get(i) != null)
            {
                ++count;
            }
        }
        return count;
    }

    public void setappearFromLeftAnimation(View v)
    {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f); //서서히 색이 어두워지라는 뜻
        animation.setDuration(300);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -2.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(300);
        v.setAnimation(animation);
    }

}


