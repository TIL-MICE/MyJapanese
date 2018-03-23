package pri.weiqiang.myjapanese.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import pri.weiqiang.myjapanese.R;
import pri.weiqiang.myjapanese.mvp.bean.Word;

public class WordsRecyclerAdapter extends RecyclerView.Adapter<WordsRecyclerAdapter.ViewHolder> {

    Context context;
    List<Word> list;

    OnItemClickListener onItemClickListener;

    public WordsRecyclerAdapter(Context context, List<Word> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.bean = list.get(position);
        holder.mTvWord.setText(holder.bean.getWord());
        holder.mTvPhonetic.setText(String.valueOf(holder.bean.getPhonetic()));
        holder.mTvTranslation.setText(holder.bean.getTranslation());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.bean);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView mTvWord;
        public final TextView mTvPhonetic;
        public final TextView mTvTranslation;

        public Word bean;

        public ViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;
            this.mTvWord = itemView.findViewById(R.id.tv_word);
            this.mTvPhonetic = itemView.findViewById(R.id.tv_phonetic);
            this.mTvTranslation = itemView.findViewById(R.id.tv_translation);


        }


    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(Word bean);

    }

}
