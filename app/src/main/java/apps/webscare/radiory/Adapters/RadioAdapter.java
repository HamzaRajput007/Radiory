package apps.webscare.radiory.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apps.webscare.radiory.Models.RadioModel;
import apps.webscare.radiory.R;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder>{

    ArrayList<RadioModel> radiosList ;
    Context mContext;
    RadioClickInterface radioClickInterface;

    public RadioAdapter(ArrayList<RadioModel> radiosList, Context mContext , RadioClickInterface radioClickInterface) {
        this.radiosList = radiosList;
        this.radioClickInterface = radioClickInterface;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_radios, parent, false);
        RadioViewHolder radioViewHolder = new RadioViewHolder(view , radioClickInterface);
        return radioViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RadioViewHolder holder, int position) {

        holder.channelName.setText(radiosList.get(position).getTitle());
        Picasso.with(mContext).load(radiosList.get(position).getThumbnailUrl().get(0)).into(holder.thumbnailImageView);

    }

    @Override
    public int getItemCount() {
        return radiosList.size();
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView channelName , authorName ;
        RadioClickInterface radioClickInterface;
        de.hdodenhof.circleimageview.CircleImageView thumbnailImageView;

        public RadioViewHolder(@NonNull View itemView , RadioClickInterface radioClickInterface) {
            super(itemView);
            channelName = itemView.findViewById(R.id.radioNameTvId);
            authorName = itemView.findViewById(R.id.authorNameTvId);
            thumbnailImageView = itemView.findViewById(R.id.iamgeViewRadioItemID);
            this.radioClickInterface = radioClickInterface;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            radioClickInterface.onRadioClicked(getAdapterPosition());
        }
    }

    public interface RadioClickInterface{
        void onRadioClicked(int position);
    }

}
