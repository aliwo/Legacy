package comuiappcenter.facebook.m.legacy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-01-01.
 */
public class defaultButton extends RelativeLayout implements View.OnClickListener
{

    public defaultButton(Context context)
    {
        super(context);
        initialize();
    }

    public defaultButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();
    }

    public void initialize()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infService);
        View v = inflater.inflate(R.layout.default_button_layout, this, false);
        this.setOnClickListener(this);
        addView(v);
    }

    @Override
    public void onClick(View v)
    {
        Toast.makeText(v.getContext(), "레이아웃이 눌렸습니다.", Toast.LENGTH_LONG).show();
    }


}
