package apps.webscare.radiory.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import apps.webscare.radiory.Models.CountriesModel;
import apps.webscare.radiory.Models.GetCountriesModel;
import apps.webscare.radiory.R;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {

    ArrayList<GetCountriesModel> countriesModelArrayList;
    Context mContext ;
    OnCountryClickedInterface onCountryClickedInterface;

    public CountriesAdapter(ArrayList<GetCountriesModel> countriesModelArrayList, Context mContext , OnCountryClickedInterface onCountryClickedInterface) {
        this.countriesModelArrayList = countriesModelArrayList;
        this.mContext = mContext;
        this.onCountryClickedInterface = onCountryClickedInterface;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linear_countries_model, parent, false);
        CountriesViewHolder countriesViewHolder = new CountriesViewHolder(view , onCountryClickedInterface);
        return countriesViewHolder;
    }

    //Todo We are implemeting the onbindView of HOme [DONE]
    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {

        holder.channelThumbnailImageView.setImageResource(R.drawable.pakistan);
        holder.channelNameTv.setText(countriesModelArrayList.get(position).getName());
        if(countriesModelArrayList.get(position).getTermId() != 1){
            Picasso.with(mContext).load(getImageUrl(countriesModelArrayList.get(position).getDescription())).into(holder.channelThumbnailImageView);
        }
//        holder.channelNumberTv.setText(countriesModelArrayList.get(position).getCount());

    }

    @Override
    public int getItemCount() {
        return countriesModelArrayList.size();
    }

    public static class CountriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView channelNameTv , channelNumberTv ;
        de.hdodenhof.circleimageview.CircleImageView channelThumbnailImageView;
        OnCountryClickedInterface onCountryClickedInterface;

    public CountriesViewHolder(@NonNull View itemView , OnCountryClickedInterface onCountryClickedInterface) {
        super(itemView);
        channelNameTv = itemView.findViewById(R.id.channelsNameTextViewId);
//        channelNumberTv = itemView.findViewById(R.id.channelCountId);
        channelThumbnailImageView = itemView.findViewById(R.id.imageViewChannelsImageId);
        this.onCountryClickedInterface = onCountryClickedInterface;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onCountryClickedInterface.onCountryClicked(getAdapterPosition());
    }
}

    public interface OnCountryClickedInterface {
        void onCountryClicked( int position);
    }

    public String getImageUrl(String longText){
        String captured = longText.substring(longText.indexOf(" src=\\\"") + 21);
        String [] splitOne = longText.split("src=\\\"");
        String[] realResult = splitOne[1].split("\" alt=");
        return realResult[0];
    }
}
