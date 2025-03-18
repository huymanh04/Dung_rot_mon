package com.example.dung_rot_mon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.example.dung_rot_mon.admin.Frg_baner;



import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private List<Frg_baner.Banner> bannerList;
    // Declare Context
    private Context context;
    private DatabaseManager dbManager;
    DatabaseHelper db;
    // Constructor with Context
    public BannerAdapter(List<Frg_baner.Banner> bannerList, Context context) {
        this.bannerList = bannerList;
        dbManager = new DatabaseManager(context);
        db= new DatabaseHelper(context);
        // Assign context
        this.context = context;
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
        return new BannerViewHolder(itemView);
    }
TextView id;
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
        Frg_baner.Banner banner = bannerList.get(position);
        holder.bannerTitle.setText(banner.getTitle());
        String a= String.valueOf(banner.getID());
        holder.bannerDescription.setText("ID : "+a);
        id=holder.bannerDescription;
        Bitmap bitmap = BitmapFactory.decodeByteArray(banner.getImageUrl(), 0, banner.getImageUrl().length);
        holder.bannerImage.setImageBitmap(bitmap);

        // Handle Edit and Delete clicks
        holder.editBannerButton.setOnClickListener(v -> editBanner(position));
        holder.deleteBannerButton.setOnClickListener(v -> deleteBanner(position));
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    // Edit banner logic
    private void editBanner(int position) {
        // Edit banner logic
        Toast.makeText(context, "Edit Banner: " + position, Toast.LENGTH_SHORT).show();
    }

    // Delete banner logic
    private void deleteBanner(int position) {
        bannerList.remove(position);

        String[] parts = id.getText().toString().replace(" ","").split(":");
        int ida = Integer.parseInt(parts[1].trim());
        deleteById(ida, "baner");
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bannerList.size());

        Toast.makeText(context, "Deleted Banner: " + position, Toast.LENGTH_SHORT).show();
    }



    public void deleteById(int id, String name) {
        SQLiteDatabase dba = db.openDatabase();

        String whereClause = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};  // ID của bản ghi cần xóa
        dba.delete(name, whereClause, whereArgs);  // Xóa bản ghi có ID này
        dba.close();
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        TextView bannerTitle, bannerDescription;
        Button editBannerButton, deleteBannerButton;
        ImageView bannerImage;

        BannerViewHolder(View itemView) {
            super(itemView);
            bannerTitle = itemView.findViewById(R.id.bannerTitle);
            bannerImage = itemView.findViewById(R.id.bannerImage);
            bannerDescription = itemView.findViewById(R.id.id);
            editBannerButton = itemView.findViewById(R.id.editBannerButton);
            deleteBannerButton = itemView.findViewById(R.id.deleteBannerButton);
        }
    }
}
