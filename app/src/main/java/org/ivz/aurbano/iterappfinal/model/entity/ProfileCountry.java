package org.ivz.aurbano.iterappfinal.model.entity;

import androidx.room.Embedded;

public class ProfileCountry {

    @Embedded
    public Profile profile;

    @Embedded(prefix = "country_")
    public Country country;
}
