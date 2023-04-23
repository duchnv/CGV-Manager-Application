package com.example.cgv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhimAdapter extends RecyclerView.Adapter<PhimAdapter.PhimViewHolder> {

    private List<Phim> phimList;

    private IClickListener iClickListener;
    public interface IClickListener{
        void onClickUpdateItem(Phim phim);
        void onClickDeleteItem(Phim phim);
    }

    public PhimAdapter(List<Phim> phimList, IClickListener iClickListener) {
        this.phimList = phimList;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public PhimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phim, parent,false);
        return new PhimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhimViewHolder holder, int position) {
        Phim phim = phimList.get(position);
        if(phim==null){
            return;
        }
        holder.tvID.setText(String.valueOf(phim.getId()));
        holder.tvTen.setText(phim.getTen());
        holder.tvGia.setText(String.valueOf(phim.getGia()));
        holder.tvGio.setText(phim.getGio());
        holder.tvTheloai.setText(phim.getTheloai());
        holder.btUpdatePhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onClickUpdateItem(phim);
            }
        });
        holder.btDeletePhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onClickDeleteItem(phim);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(phimList!=null){
            return phimList.size();
        }
        return 0;
    }

    public class PhimViewHolder extends RecyclerView.ViewHolder{

        private TextView tvID;
        private TextView tvTen;
        private TextView tvGia;
        private TextView tvGio;
        private TextView tvTheloai;
        private Button btUpdatePhim;
        private Button btDeletePhim;
        //private ImageView iAnh;

        public PhimViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id);
            tvTen = itemView.findViewById(R.id.tv_ten);
            tvGia = itemView.findViewById(R.id.tv_giave);
            tvGio = itemView.findViewById(R.id.tv_gio);
            tvTheloai = itemView.findViewById(R.id.tv_theloai);
            btUpdatePhim = itemView.findViewById(R.id.bt_updatePhim);
            btDeletePhim = itemView.findViewById(R.id.bt_deletePhim);
            //iAnh = itemView.findViewById(R.id.img_anh);
        }
    }
}
