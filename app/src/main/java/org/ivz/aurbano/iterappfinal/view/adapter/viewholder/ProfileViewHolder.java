package org.ivz.aurbano.iterappfinal.view.adapter.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.ivz.aurbano.iterappfinal.R;
import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;
import org.ivz.aurbano.iterappfinal.view.activity.CreateActivity;
import org.ivz.aurbano.iterappfinal.view.activity.EditActivity;

public class ProfileViewHolder extends RecyclerView.ViewHolder{

    public TextView tvName, tvCity, tvCountry, tvDate, tvUrl;
    public ImageView ivProfile;
    public Profile profile;

    public ProfileViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvName);
        tvCity = itemView.findViewById(R.id.tvCity);
        tvCountry = itemView.findViewById(R.id.tvCountry);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvUrl = itemView.findViewById(R.id.tvUrl);
        ivProfile = itemView.findViewById(R.id.ivProfile);

        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), EditActivity.class);
            intent.putExtra("profile", profile);
            itemView.getContext().startActivity(intent);
        });
    }
}
