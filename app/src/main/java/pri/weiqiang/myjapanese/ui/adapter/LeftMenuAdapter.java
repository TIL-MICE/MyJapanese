package pri.weiqiang.myjapanese.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.mvp.bean.Book;


public class LeftMenuAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Book> mBookList;
    private int mSelectedNum;
    private List<onItemSelectedListener> mSelectedListenerList;

    public interface onItemSelectedListener {
         void onLeftItemSelected(int postion, Book menu);
    }

    public void addItemSelectedListener(onItemSelectedListener listener) {
        if (mSelectedListenerList != null)
            mSelectedListenerList.add(listener);
    }

    public void removeItemSelectedListener(onItemSelectedListener listener) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty())
            mSelectedListenerList.remove(listener);
    }

    public LeftMenuAdapter(Context mContext, List<Book> mBookList) {
        this.mContext = mContext;
        this.mBookList = mBookList;
        this.mSelectedNum = -1;
        this.mSelectedListenerList = new ArrayList<>();
        if (mBookList.size() > 0)
            mSelectedNum = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_menu_item, parent, false);
        LeftMenuViewHolder viewHolder = new LeftMenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Book mBook = mBookList.get(position);
        LeftMenuViewHolder viewHolder = (LeftMenuViewHolder) holder;
        viewHolder.mTvName.setText(mBook.getName());
        if (mSelectedNum == position) {
            viewHolder.mLlLeftItem.setSelected(true);
        } else {
            viewHolder.mLlLeftItem.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public void setSelectedNum(int selectedNum) {
        if (selectedNum < getItemCount() && selectedNum >= 0) {
            this.mSelectedNum = selectedNum;
            notifyDataSetChanged();
        }
    }

    public int getSelectedNum() {
        return mSelectedNum;
    }

    private class LeftMenuViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;
        LinearLayout mLlLeftItem;

        public LeftMenuViewHolder(final View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
            mLlLeftItem = itemView.findViewById(R.id.ll_left_item);
            mLlLeftItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickPosition = getAdapterPosition();
//                    setSelectedNum(clickPosition);
                    notifyItemSelected(clickPosition);
                }
            });
        }
    }

    private void notifyItemSelected(int position) {
        if (mSelectedListenerList != null && !mSelectedListenerList.isEmpty()) {
            for (onItemSelectedListener listener : mSelectedListenerList) {
                listener.onLeftItemSelected(position, mBookList.get(position));
            }
        }
    }
}
