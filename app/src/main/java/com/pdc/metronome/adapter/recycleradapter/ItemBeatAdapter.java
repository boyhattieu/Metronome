package com.pdc.metronome.adapter.recycleradapter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pdc.metronome.R;
import com.pdc.metronome.item.ItemBeat;

import java.util.List;

public class ItemBeatAdapter extends RecyclerView.Adapter<ItemBeatAdapter.ItemHolder> {

    private List<ItemBeat> itemBeats;
    private IOnClickItem onClickItem;

    public ItemBeatAdapter(List<ItemBeat> itemBeats) {
        this.itemBeats = itemBeats;
    }

    public void setOnClickItem(IOnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ItemBeatAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_beat_recycler, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemBeatAdapter.ItemHolder itemHolder, final int i) {
        String txtBeat = itemBeats.get(i).getTxtBeat();
        int imgChecked = itemBeats.get(i).getImgChecked();
        itemHolder.setData(txtBeat,imgChecked);
        itemHolder.txtBeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickInterface(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemBeats.size();
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
            Glide.with(itemView).load(imgChecked).into(this.imgChecked);
        }

        @SuppressLint("ResourceAsColor")
        public void beatChosen(boolean isChosen) {
            if (isChosen) {
                txtBeat.setTypeface(Typeface.DEFAULT_BOLD);
                txtBeat.setTextColor(R.color.color_beat_choosen);
                imgChecked.setVisibility(View.VISIBLE);
                imgChecked.setColorFilter(R.color.color_beat_choosen);
            } else {
                txtBeat.setTypeface(Typeface.DEFAULT);
                txtBeat.setTextColor(R.color.tempo_txt_tap);
                imgChecked.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface IOnClickItem{
        void onClickInterface(int position);
    }
}
