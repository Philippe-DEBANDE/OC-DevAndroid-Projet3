package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;

    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items) { mNeighbours = items; }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context= v.getContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text="";
                if (!neighbour.getIsFavorite())    {
                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                    DI.getNeighbourApiService().deleteNeighbour(neighbour);
                    text = "Suppression du voisin "+neighbour.getName()+" effectuée";
                    Toast toast_Sup_Vois = Toast.makeText(context, text, duration);
                    toast_Sup_Vois.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast_Sup_Vois.show();
                }
                else
                {
                    // The command to delete the Favorite Neighbour, and the post Event ...
                    //neighbour.setIsFavorite();
                    //System.out.println("RecyclerView ---> mDeleteButton : L'event envoyé est suppression voisin FAVORI pour "+neighbour.getName());
                    //EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));
                    text = "Suppression impossible du voisin "+neighbour.getName()+" car elle/il est dans les favoris !!!";
                    Toast toast_Sup_Fav = Toast.makeText(context, text, duration);
                    toast_Sup_Fav.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast_Sup_Fav.show();
                }
            }
        });

        //Row recycler click control & intent
        holder.mRecycler_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DetailNeighbourActivity = new Intent(v.getContext(), DetailNeighbourActivity.class);
                // Add mPosition
                DetailNeighbourActivity.putExtra("mPosition", DI.getNeighbourApiService().getNeighbours().indexOf(neighbour));
                v.getContext().startActivity(DetailNeighbourActivity);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        //Add Reclycler view row
        @BindView(value = R.id.recycler_row)
        public ConstraintLayout mRecycler_item;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
