package com.stustirling.ribotviewerjava.ui.allribots;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.stustirling.ribotviewerjava.R;
import com.stustirling.ribotviewerjava.shared.model.RibotModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

class AllRibotAdapter extends RecyclerView.Adapter<AllRibotAdapter.ViewHolder>{

    private Listener listener;

    interface Listener {
        void ribotSelected(RibotModel ribotModel, View sharedView );
    }

    private List<RibotModel> items = new ArrayList<>();

    AllRibotAdapter(Listener listener) {
        this.listener = listener;
    }

    void setItems(List<RibotModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId().hashCode();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_all_ribot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private ImageView avatar;
        private TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.cl_alc_container);
            avatar = itemView.findViewById(R.id.civ_alc_avatar);
            name = itemView.findViewById(R.id.tv_alc_name);
        }

        void bind(final RibotModel ribotModel) {
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ribotSelected(items.get(getAdapterPosition()),avatar);
                }
            });

            ViewCompat.setTransitionName(avatar,ribotModel.getId());

            Drawable placeholder = container.getContext().getDrawable(R.drawable.small_ribot_logo);
            if ( placeholder != null ) {
                placeholder = placeholder.mutate();
                DrawableCompat.setTint(placeholder, Color.parseColor(ribotModel.getColor()));
            }

            Glide.with(avatar.getContext())
                    .load(ribotModel.getAvatar())
                    .apply(RequestOptions.fitCenterTransform()
                            .placeholder(placeholder))
                    .into(avatar);
            name.setText(avatar.getContext().getString(
                    R.string.full_name_format,
                    ribotModel.getFirstName(),ribotModel.getLastName()));
        }


    }
}
