package com.pdc.metronome.adapter.recycleradapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pdc.metronome.R;
import com.pdc.metronome.item.BeatItems;

import java.util.List;

public class BeatRecyclerAdapter extends RecyclerView.Adapter<BeatRecyclerAdapter.ItemHolder> {

    private static final String TAG = "BeatRecyclerAdapter";

    private List<BeatItems> beatItems;
    private IOnClickItem onClickItem;

    public void setOnClickItem(IOnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public BeatRecyclerAdapter(List<BeatItems> beatItems) {
        this.beatItems = beatItems;
    }

    @NonNull
    @Override
    public BeatRecyclerAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_beat, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BeatRecyclerAdapter.ItemHolder itemHolder, final int i) {
        String txtBeat = beatItems.get(i).getTxtBeat();
        int imgChecked = beatItems.get(i).getImgChecked();
        itemHolder.setData(txtBeat, imgChecked);
        itemHolder.txtBeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickInterface(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beatItems.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private TextView txtBeat;
        private ImageView imgChecked;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            txtBeat = itemView.findViewById(R.id.txt_beat);
            imgChecked = itemView.findViewById(R.id.img_checked);
        }

        public void setData(String txtBeat, @DrawableRes int imgChecked){
            this.txtBeat.setText(txtBeat);
            if (imgChecked != 0){
                Glide.with(itemView).load(imgChecked).into(this.imgChecked);
            }
        }
    }

    public interface IOnClickItem {
        void onClickInterface(int position);
    }
}
