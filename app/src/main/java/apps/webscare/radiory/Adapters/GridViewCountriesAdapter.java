package apps.webscare.radiory.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apps.webscare.radiory.Models.GetCountriesModel;
import apps.webscare.radiory.R;


public class GridViewCountriesAdapter extends RecyclerView.Adapter<GridViewCountriesAdapter.CountriesGridViewHolder> {

    ArrayList<GetCountriesModel> channelsList;
    GridClickInterface clickInterface;
    Context mContext ;

    public GridViewCountriesAdapter(ArrayList<GetCountriesModel> channelsList, GridClickInterface clickInterface, Context mContext) {
        this.channelsList = channelsList;
        this.clickInterface = clickInterface;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CountriesGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(apps.webscare.radiory.R.layout.item_grid_view_countries, parent, false);
        GridViewCountriesAdapter.CountriesGridViewHolder countriesViewHolder = new GridViewCountriesAdapter.CountriesGridViewHolder(view , clickInterface);
        return countriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesGridViewHolder holder, int position) {
        holder.countryNameTv.setText(channelsList.get(position).getName());
        if(channelsList.get(position).getTermId() != 1){
            Picasso.with(mContext).load(getImageUrl(channelsList.get(position).getDescription())).into(holder.circleImageView);
        }  }

    @Override
    public int getItemCount() {
        return channelsList.size();
    }

    public static class CountriesGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        de.hdodenhof.circleimageview.CircleImageView circleImageView;
        TextView countryNameTv;
        GridClickInterface clickInterface;

        public CountriesGridViewHolder(@NonNull View itemView , GridClickInterface clickInterface) {
            super(itemView);
            circleImageView = itemView.findViewById(apps.webscare.radiory.R.id.imageViewGridViewCountriesId);
            countryNameTv = itemView.findViewById(R.id.textViewGridViewCountriesId);
            this.clickInterface = clickInterface;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickInterface.onGridItemClicked(getAdapterPosition());
        }
    }

    public interface GridClickInterface{
         public void onGridItemClicked(int position);
    }
    public String getImageUrl(String longText){
        String captured = longText.substring(longText.indexOf(" src=\\\"") + 21);
        String [] splitOne = longText.split("src=\\\"");
        String[] realResult = splitOne[1].split("\" alt=");
        return realResult[0];
    }
}
