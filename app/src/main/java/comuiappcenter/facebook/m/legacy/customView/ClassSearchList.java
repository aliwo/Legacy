package comuiappcenter.facebook.m.legacy.customView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import comuiappcenter.facebook.m.legacy.InterestedClassSettingActivity;
import comuiappcenter.facebook.m.legacy.userInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import comuiappcenter.facebook.m.legacy.R;

/**
 * Created by Administrator on 1/24/2017.
 */

public class ClassSearchList extends BaseAdapter
{
    Context mcontext;
    LayoutInflater inflater;
    private ArrayList<String> classnames;
    private ArrayList<String> arrayList; // ClassSearchList 안에서 데이터 저장, 검색을 담당합니다.


    public ClassSearchList(Context context, String[] arrays)
    {
        mcontext = context;
        inflater = LayoutInflater.from(context);
        classnames = new ArrayList<String>();
        for (int i=0; i<arrays.length; i++)
        {
            this.classnames.add(arrays[i]);
        }
        this.arrayList = new ArrayList<String>();
        this.arrayList.addAll(classnames);
    }

    public class ViewHolder
    {
        TextView TextViewClassName;
    }

    @Override
    public Object getItem(int position)
    {
        return arrayList.get(position);
    }

    @Override
    public int getCount()
    {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.interested_class_list, null);
            holder.TextViewClassName = (TextView) convertView.findViewById(R.id.textview_interested_class_list);
            holder.TextViewClassName.setText(arrayList.get(position));
            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    userInfo.InterestedClass.add(arrayList.get(position)); // 관심 수업에 해당과목 추가.
                    setDisappearToRightAnimation(v); // 추가할때 과목 view가 오른쪽으로 휙! 날라갑니다.
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }


    public void filter(String charText) // 검색 기능을 구현하는 메소드. ClassSearchList는 생성될때 모든 수업 List를 가지고 있다가, filter를 거친후 검색어와 연관된 List만 보여준다.
    {
        charText = charText.toLowerCase(Locale.getDefault()); // 대소문자 구별 없이 검색하기 위해 전부 소문자로 바꾼다.
        arrayList.clear();
        if(charText.length() == 0)
        {
            for(int i=0; i<classnames.size(); i++)
            {
                arrayList.add(classnames.get(i));
            }
        }
        else // 검색어 charText와 classname[i]를 비교해보면서 만약 둘이 같으면 arrayList에 classname을 추가.
        {
            for (String string : classnames) // clear 되지 않았으면서, 수업명을 저장하고 있는 배열, 이걸 string에 하나씩 넣어본다.
            {
                if (string.toLowerCase().contains(charText)) // classnames -> string 으로 옮겨간 수업 이름이 검색어와 같다면
                {
                    arrayList.add(string); // string 값을 list에 추가.
                }
            }
        }
        notifyDataSetChanged(); // data가 변경되었음을 알려줍니다.
    }


    public void setDisappearToRightAnimation(View v)
    {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(1.0f, 0.0f); //서서히 색이 어두워지라는 뜻
        animation.setDuration(300);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 2.0f, //자신 0.0 에서 시작해서 X값을 2만큼 더해라
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(300);
        v.setAnimation(animation);
    }
}
