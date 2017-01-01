package comuiappcenter.facebook.m.legacy;

import android.app.ActionBar;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017-01-01.
 */
public class defaultTextBox extends RelativeLayout
{

    public defaultTextBox(Context context)
    {
        super(context);
        setDefault();
    }

    public defaultTextBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDefault();
    }

    private void setDefault()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        //왜 infService 문자열을 불러 들여서 getSystemService에 넣어야 되지? 왜 이렇게
        //만들어 놨지 *?
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infService);
        View v = inflater.inflate(R.layout.default_text_box_layout, this, false);
        //relativelayout을 상속하지 않으면 inflater 메소드가 인식이 안되고 불구가 됨;; 왜?
        addView(v);
    }

}
