package cn.edu.bjtu.nourriture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.models.Consumer;

/**
 * Created by Pavel Procházka on 05/01/15.
 */
public class ConsumersAdapter extends ArrayAdapter {

    Context context                 = null;
    ArrayList<Consumer> consumers   = null;
    LayoutInflater inflater         = null;

    // takes CONTEXT, LAYOUT and DATA
    public ConsumersAdapter(Context context, ArrayList<Consumer> consumerToShow)  {

        super(context, R.layout.row_consumer_overview, consumerToShow);

        this.context = context;

        this.consumers = consumerToShow;

        this.inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.row_consumer_overview, parent, false);

        Consumer consumer = consumers.get(position);

        // Consumer thumbnail
        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.consumerThumbnailImageView);
        String url = consumer.getPicture();
        Picasso.with(getContext())
                .load(url)
                .resize(50, 50)
                .centerCrop()
                .placeholder(R.drawable.user_placeholder)
                .into(thumbnail);

        // Consumer name
        TextView nameTextView = (TextView) rowView.findViewById(R.id.consumerNameTextView);
        nameTextView.setText(consumer.getName());

        // Consumer occupation
        TextView occupationTextView = (TextView) rowView.findViewById(R.id.consumerOccupationTextView);
        occupationTextView.setText(consumer.getOccupation());

        return rowView;
    }
}