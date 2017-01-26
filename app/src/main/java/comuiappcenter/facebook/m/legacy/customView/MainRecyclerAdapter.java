package comuiappcenter.facebook.m.legacy.customView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comuiappcenter.facebook.m.legacy.R;
import comuiappcenter.facebook.m.legacy.dataContainer.MyData;

/**
 * Created by Administrator on 1/24/2017.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>
{
    private ArrayList<MyData> mDataset;
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public LinearLayout mLinearLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.textview_maincardview_my_question);
            mLinearLayout = (LinearLayout)itemView.findViewById(R.id.relativelayout_main_cardview);
        }
    }

    public MainRecyclerAdapter(Context context, ArrayList<MyData> myDataset)
    {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public MainRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //어뎁터가 생성된 이후 바로 뷰 홀더를 만드는데, 이떄 호출되는 메소드가 이거임 여기를 잘 바꿔야 내가 원하는 결과물이 나온다.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main_card, parent, false);
        ViewHolder VH = new ViewHolder(v);
        return VH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextView.setText(mDataset.get(position).text);
    }


    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }

}
