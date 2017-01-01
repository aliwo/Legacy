package comuiappcenter.facebook.m.legacy;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017-01-01.
 */
public class defaultTextBox extends RelativeLayout
{
    public EditText mEditText;

    public defaultTextBox(Context context)
    {
        super(context);
        setDefault();
    }

    public defaultTextBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDefault();
        getAttrs(attrs);
    }

    private void setDefault()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        //액티비티만 가지고 있는 inflater를 데려오기 위해서 SERVICE 상수를 액티비티로 부터 데려오고
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infService);
        //데려온 상수를 inflater를 생성할 때 넣는다.
        View v = inflater.inflate(R.layout.default_text_box_layout, this, false);
        addView(v);
        mEditText = (EditText) findViewById(R.id.default_textbox_editext);
    }


    private void getAttrs(AttributeSet attrs)
    {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.defaultTextBox);

        int minLine = typedArray.getResourceId(R.styleable.defaultTextBox_minLines, R.drawable.textbox_default);
        mEditText.setMinLines(minLine);

        String hint = typedArray.getString(R.styleable.defaultTextBox_hint);
        mEditText.setHint(hint);

        //int LeftPadding = typedArray.getResourceId(R.styleable.defaultTextBox_minLines, R.drawable.textbox_default);
        mEditText.setPadding(20, 4, 4, 4);

        typedArray.recycle();

    }
}
