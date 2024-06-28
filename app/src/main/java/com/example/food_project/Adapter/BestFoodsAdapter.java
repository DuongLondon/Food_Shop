package com.example.food_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.food_project.Activity.DetailActivity;
import com.example.food_project.Domain.Foods;
import com.example.food_project.R;

import java.util.ArrayList;

//Hiển thị danh sách các món ăn trong một RecycleView
public class BestFoodsAdapter extends RecyclerView.Adapter<BestFoodsAdapter.viewholder> {
    // Danh sách các đối tượng Foods đại diện cho các món ăn
    ArrayList<Foods> items;
    //Copntext của activity cha, cần thiết để khởi tạo các thành phần giao diện
    Context context;

    // hàm này nhận vào một danh sách các món ăn 'items' và gán nó cho biến thành viên của lớp
    public  BestFoodsAdapter(ArrayList<Foods> items){
        this.items = items;
    }

    @NonNull
    @Override
    // được gọi khi khi Recyclerview cần một viewholder mới
    public BestFoodsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // context đuơc gán bằng context của parent
        context = parent.getContext();
        // duoc su dung de tao mot view moi tu tep layout viewholder_best_deal
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_best_deal, parent, false);
        return new viewholder(inflate);
    }

    @Override
    // duoc goi de gan du lieu vao cac view torng viewholder cho mot vi tri cu the (position)
    public void onBindViewHolder(@NonNull BestFoodsAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("$" + items.get(position).getPrice());
        holder.timeTxt.setText(items.get(position).getTimeValue() + " min");
        holder.starTxt.setText(""+ items.get(position).getStar());

        // Su dung thu vien 'Glide' de tai hinh anh cua mon an tu URL vao ImageView 'pic' voi cac bien doi lam cat giua
        // va bo tron cac goc
        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        // Thiet lap mot OnclickListener de khi nguoi dung nhap vao mot muc, no se mo 'DetailActivity'
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                //putExtra thêm dữ liệu bổ sung vào Intent
                //Ở đây, items.get(position) là đối tượng Foods hiện tại được nhấp vào,
                // và nó được truyền sang DetailActivity với khóa "object".
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    // tra ve tong so muc trong danh sach 'items'
    @Override
    public int getItemCount() {
        return items.size();
    }

    // chua cac thong tin ve mon an
    public class viewholder extends RecyclerView.ViewHolder {

        TextView titleTxt, priceTxt, starTxt, timeTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            starTxt = itemView.findViewById(R.id.starTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
