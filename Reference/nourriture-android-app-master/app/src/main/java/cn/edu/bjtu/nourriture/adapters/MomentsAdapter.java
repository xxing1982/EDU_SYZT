package cn.edu.bjtu.nourriture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.models.Moment;

/**
 * Created by Vincent on 02/01/2015.
 * Revised by Pavel Procházka 04/01/2015
 */
public class MomentsAdapter extends ArrayAdapter {

    Context context             = null;
    ArrayList<Moment> moments   = null;
    LayoutInflater inflater     = null;

    // takes CONTEXT, LAYOUT and DATA
    public MomentsAdapter(Context context, ArrayList<Moment> toShowMoments){

        super(context, R.layout.row_moment_overview, toShowMoments);

        this.context = context;

        this.moments = toShowMoments;

        this.inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View momentView = convertView;

        // Make sure we have a view to work with
        if (momentView == null){
            momentView = inflater.inflate(R.layout.row_moment_overview, parent, false);
        }

        Moment m = moments.get(position);

        // Set author name
        TextView author = (TextView) momentView.findViewById(R.id.momentAuthorTextView);
        author.setText(m.getAuthor().getName());

        // Set content text
        TextView content = (TextView) momentView.findViewById(R.id.momentContentTextView);
        content.setText(m.getText());

        // Set time elapsed
        TextView timeElapsed = (TextView) momentView.findViewById(R.id.momentTimeTextView);
        timeElapsed.setText(m.getCreated());

        // Set comments count
        TextView comments = (TextView) momentView.findViewById(R.id.momentCommentTextView);
        if (m.getCommentCount() == 1){
            comments.setText(m.getCommentCount() + " " + context.getString(R.string.momentComment));
        }
        else {
            comments.setText(m.getCommentCount() + " " + context.getString(R.string.momentComments));
        }

        // Set likes count
        TextView likes = (TextView) momentView.findViewById(R.id.momentLikesTextView);
        if (m.getLikeCount() == 1){
            likes.setText(m.getLikeCount() + " " + context.getString(R.string.momentLike));
        }
        else {
            likes.setText(m.getLikeCount() + " " + context.getString(R.string.momentLikes));
        }

        return momentView;
    }
}