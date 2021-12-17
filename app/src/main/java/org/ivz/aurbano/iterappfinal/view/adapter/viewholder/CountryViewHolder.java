package org.ivz.aurbano.iterappfinal.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.ivz.aurbano.iterappfinal.R;

public class CountryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCountry;

    public CountryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCountry = itemView.findViewById(R.id.tvCountry);
    }

    public TextView getCountryItemView(){
        return tvCountry;
    }
}
