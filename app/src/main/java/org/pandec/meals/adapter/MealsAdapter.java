package org.pandec.meals.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.pandec.meals.R;
import org.pandec.meals.model.CategoriesItem;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private Context context;
    private List<CategoriesItem> list;

    public List<CategoriesItem> getList() {
        return list;
    }

    public void setList(List<CategoriesItem> list) {
        this.list = list;
    }

    public MealsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_meals, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvName.setText(getList().get(i).getStrCategory());
        Glide.with(context).load(getList().get(i).getStrCategoryThumb())
                .into(viewHolder.ivImage);
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image_view);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
