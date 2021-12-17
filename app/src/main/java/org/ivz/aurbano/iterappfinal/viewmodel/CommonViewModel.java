package org.ivz.aurbano.iterappfinal.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import org.ivz.aurbano.iterappfinal.model.repository.ProfileRepository;

public class CommonViewModel extends ViewModel {

    private Context context;
    private ProfileRepository repository;

    public CommonViewModel() {}

    public void setContext(Context context) {
        this.context = context;
    }
}
