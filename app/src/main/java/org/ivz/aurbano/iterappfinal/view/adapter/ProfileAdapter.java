package org.ivz.aurbano.iterappfinal.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.ivz.aurbano.iterappfinal.R;
import org.ivz.aurbano.iterappfinal.model.entity.Country;
import org.ivz.aurbano.iterappfinal.model.entity.Profile;
import org.ivz.aurbano.iterappfinal.model.entity.ProfileCountry;
import org.ivz.aurbano.iterappfinal.view.adapter.viewholder.ProfileViewHolder;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

    private List<ProfileCountry> profileList;
    private Context context;

    public ProfileAdapter(Context context){this.context = context;}

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        ProfileCountry profileCountry = profileList.get(position);
        Profile profile = profileCountry.profile;
        holder.profile = profile;
        Country country = profileCountry.country;

        holder.tvName.setText(profile.name);
        holder.tvCity.setText(profile.city);
        holder.tvCountry.setText(country.name);
        holder.tvDate.setText(profile.date);
        holder.tvUrl.setText(profile.url);

        Glide.with(context).load(profile.url).into(holder.ivProfile);
    }

    @Override
    public int getItemCount() {
        if(profileList == null){
            return 0;
        }
        return profileList.size();
    }

    public void setProfileList(List<ProfileCountry> profileList) {
        this.profileList = profileList;
        notifyDataSetChanged();
    }
}
