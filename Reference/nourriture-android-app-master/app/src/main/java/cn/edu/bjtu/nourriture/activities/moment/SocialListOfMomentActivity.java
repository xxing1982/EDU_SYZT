package cn.edu.bjtu.nourriture.activities.moment;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.models.Like;
import cn.edu.bjtu.nourriture.models.Moment;
import cn.edu.bjtu.nourriture.models.Comment;

enum LIST_TYPE{
    LIST_TYPE_LIKES,
    LIST_TYPE_COMMENTS
}

public class SocialListOfMomentActivity extends ActionBarActivity {



    // --- PROPERTIES ---
    private LIST_TYPE       listViewType;   //to distingush when to show "Likes" and "Comments"

    private Moment          momentToShowDetails;

    private ArrayAdapter    adapter;

    private ListView        listView;



    // --- ACTIVITY lifecycle methods ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_list_of_moment);

        // Get the moment and activity title from the intent
        Intent intent = getIntent();    //get the Intent that started your activity by calling getIntent() and retrieve the data contained within the intent
        momentToShowDetails = (Moment) intent.getSerializableExtra(DetailMomentActivity.DETAILED_MOMENT);

        String title = intent.getStringExtra(DetailMomentActivity.DETAILED_MOMENT_ACTIVITY_TITLE);
        setTitle(title);

        if (title.equals(getString(R.string.listTypeLikes))){
            listViewType = LIST_TYPE.LIST_TYPE_LIKES;

            adapter = new SocialAdapterLikes();
        }
        else if (title.equals(getString(R.string.listTypeComments))){
            listViewType = LIST_TYPE.LIST_TYPE_COMMENTS;

            adapter = new SocialAdapterComments();
        }

        // can findViewById, because View already populated by setContentView
        listView = (ListView) findViewById(R.id.momentSocialDetailsListView);
        listView.setAdapter(adapter);
    }



    // --- CUSTOM INNER CLASS of ArrayAdapter ---
    private class SocialAdapterLikes extends ArrayAdapter {

        // takes CONTEXT, LAYOUT and DATA
        public SocialAdapterLikes() {
            super(getBaseContext(), android.R.layout.simple_list_item_1, momentToShowDetails.getLikes());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            Like like = momentToShowDetails.getLikes().get(position);
            String likeAuthor = like.getName();

            TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
            textView.setText(likeAuthor);

            return rowView;
        }
    }

    private class SocialAdapterComments extends ArrayAdapter {

        // takes CONTEXT, LAYOUT and DATA
        public SocialAdapterComments() {
            super(getBaseContext(), android.R.layout.simple_list_item_2, momentToShowDetails.getComments());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            View rowView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

            Comment c = (Comment) momentToShowDetails.getComments().get(position);

            TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
            textView.setText(c.getText());

            TextView textView2 = (TextView) rowView.findViewById(android.R.id.text2);
            textView2.setText(c.getCreated());

            return rowView;
        }
    }
}
