package comuiappcenter.facebook.m.legacy.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import comuiappcenter.facebook.m.legacy.R;

/**
 * Created by Administrator on 2017-01-17.
 */
public class QuestionPreview extends RelativeLayout
{
    public TextView title;
    public TextView body;
    public TextView author;
    public TextView answersTextView;
    public TextView categoryTextView;

    public QuestionPreview(Context context)
    {
        super(context);
        setDefault();
    }

    public QuestionPreview(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private void setDefault()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infService);
        View v = inflater.inflate(R.layout.question_preview, this, false);
        addView(v);
        title = (TextView) findViewById(R.id.title_textview_question_preview);
        body = (TextView) findViewById(R.id.body_textview_question_preview);
        author = (TextView) findViewById(R.id.author_textview_preview);
        answersTextView = (TextView) findViewById(R.id.answers_num_textview_preview);
        categoryTextView = (TextView) findViewById(R.id.category_textview_question_preview);
    }

    private void getAttrs(AttributeSet attrs)
    {
        // 만약 xml에서 지정한 속성을 데려오고 싶다면 생성자에서 getAttrs를 부르고 인자로 attrs를 넣자.

    }

}
