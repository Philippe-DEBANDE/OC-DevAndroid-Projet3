package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.detail_Iv_NeighbourAvatar) ImageView detailUserAvatar;
    @BindView(R.id.detail_Iv_BackArrow) ImageView detailIvBack;
    @BindView(R.id.detail_Fab_Favorite) FloatingActionButton detailFabFavorite;

    @BindView(R.id.detail_Tv_UserName) TextView detailTvUsername;
    @BindView(R.id.detail_Tv_Name) TextView detailTvName;
    @BindView(R.id.detail_Tv_Address) TextView detailTvAdress;
    @BindView(R.id.detail_Tv_Phone)TextView detailTvPhone;
    @BindView(R.id.detail_Tv_Web)TextView detailTvWeb;
    @BindView(R.id.detail_Tv_Aboutme)TextView detailTvAboutMe;

    Neighbour actualNeighbour;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        ButterKnife.bind(this);
        // Remove this comment and the line below : Hidding Action Bar ---> Find it was possible to define by xml style !!!
        //Objects.requireNonNull(getSupportActionBar()).hide();

        //Get The User Data From INTENT ---> All other extra get from the 'parent' activity were removed
        position = getIntent().getIntExtra("mPosition", position);

        // Getting the actual Neighbour
        actualNeighbour = DI.getNeighbourApiService().getNeighbours().get(position);

        //Get and put text in graphic fields :
        detailTvUsername.setText(actualNeighbour.getName());
        detailTvName.setText(actualNeighbour.getName());
        detailTvAdress.setText(actualNeighbour.getAddress());
        detailTvPhone.setText(actualNeighbour.getPhoneNumber());
        detailTvWeb.setText("www.facebook.fr/"+actualNeighbour.getName());
        detailTvAboutMe.setText(actualNeighbour.getAboutMe());

        // Get User Picture and put the neighbour avatar
        detailUserAvatar = findViewById(R.id.detail_Iv_NeighbourAvatar);
        Glide.with(detailUserAvatar.getContext())
                .load(actualNeighbour.getAvatarUrl())
                .centerCrop()
                .into(detailUserAvatar);

        // Favorite Button initializing
        setFavoriteButton();

        // Arrow Back Action
        detailIvBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                finish();
            }
        });

        //Favorite button and action
        detailFabFavorite.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Change Neighbour Favorite Status and update button
            actualNeighbour.setIsFavorite();
            setFavoriteButton();
        }
       });
    }

    private void setFavoriteButton() {
        detailFabFavorite.setImageDrawable(actualNeighbour.getIsFavorite() ? getDrawable(R.drawable.ic_star_white_24dp) : getDrawable(R.drawable.ic_star_border_white_24dp));
    }

}
