package cn.edu.bjtu.nourriture.fragments.profile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.bjtu.nourriture.Analytics;
import cn.edu.bjtu.nourriture.R;
import cn.edu.bjtu.nourriture.activities.MainActivity;
import cn.edu.bjtu.nourriture.models.Consumer;
import cn.edu.bjtu.nourriture.Constants;
import cn.edu.bjtu.nourriture.services.NourritureAPI;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ConsumerFragment extends Fragment implements AbsListView.OnItemClickListener {



    // --- PROPERTIES ---
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<HashMap> consumerDataToShow;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ConsumerAdapter mAdapter;



    // --- CONSTRUCTOR ---
    public static ConsumerFragment newInstance(int sectionNumber) {
        ConsumerFragment fragment = new ConsumerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ConsumerFragment() {
    }



    // --- FRAGMENT lifecycle methods ---
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        consumerDataToShow = new ArrayList<>();

        mAdapter = new ConsumerAdapter();

        // Get tracker.
        Tracker t = ((Analytics) getActivity().getApplication()).getTracker(Analytics.TrackerName.APP_TRACKER);
        // Set screen name.
        t.setScreenName(getString(R.string.ga_my_profile_screen));
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Always fetch consumer info when comes to the foreground
        fetchConsumerInfo();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;

            // Tell the main activity that fragment has been attached (will change the title)
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            HashMap hm = consumerDataToShow.get(position);
            mListener.onConsumerInteraction(hm.values().toString());
        }
    }



    // --- API calls ---
    private void fetchConsumerInfo() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.NOURRITURE_PLATFORM_ANDROID_URL)
                .build();

        SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES_CURRENT_PROFILE, 0); // 0 - for private mode
        String username = pref.getString(Consumer.CONSUMER_USERNAME, "");

        NourritureAPI api = restAdapter.create(NourritureAPI.class);
        //once response is received, in case of JSON api your data will be transformed to your model using Gson library
        api.getConsumer(username, new Callback<Consumer>() {
            @Override
            public void success(Consumer consumer, Response response) {

                consumerDataToShow.clear();

                consumerDataToShow.addAll(consumer.getConsumerInfoToDisplay());

                //BIG BUG: replaces customerDataToShow with a pointer to arraylist from getConsumerInfoToDisplay() -> adapter hooked with customerDataToShow pointer though!!!
                //consumerDataToShow = consumer.getConsumerInfoToDisplay();

                mAdapter.notifyDataSetChanged();    // Notifies the attached observers that the underlying data has been changed and any View reflecting
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.api_error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }



    // --- CUSTOM INNER CLASS of ArrayAdapter ---
    private class ConsumerAdapter extends ArrayAdapter {

        // takes CONTEXT, LAYOUT and DATA
        public ConsumerAdapter(){
            super(getActivity(), R.layout.row_consumer_title_and_value, consumerDataToShow); //will pass an array of Consumer available info to display
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();

            View rowView = null;

            HashMap consumerInfo = consumerDataToShow.get(position);

            // row with IMAGE
            if (consumerInfo.containsKey(Consumer.CONSUMER_PICTURE)){
                rowView = inflater.inflate(R.layout.row_consumer_image, parent, false);

                ImageView imgView = (ImageView) rowView.findViewById(R.id.consumerImageView);

                String url = consumerInfo.get(Consumer.CONSUMER_PICTURE).toString();
                Picasso.with(getContext())
                        .load(url)
                        .resize(160, 160)
                        .centerCrop()
                        .placeholder(R.drawable.user_placeholder)
                        .into(imgView);
            }
            // row with TITLE and VALUE
            else {
                rowView = inflater.inflate(R.layout.row_consumer_title_and_value, parent, false);

                TextView titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
                TextView valueTextView = (TextView) rowView.findViewById(R.id.valueTextView);

                if (consumerInfo.containsKey(Consumer.CONSUMER_USERNAME)){
                    titleTextView.setText(R.string.consumerUsername);
                    valueTextView.setText(consumerInfo.get(Consumer.CONSUMER_USERNAME).toString());
                }
                else if (consumerInfo.containsKey(Consumer.CONSUMER_NAME)){
                    titleTextView.setText(R.string.consumerName);
                    valueTextView.setText(consumerInfo.get(Consumer.CONSUMER_NAME).toString());
                }
                else if (consumerInfo.containsKey(Consumer.CONSUMER_OCCUPATION)){
                    titleTextView.setText(R.string.consumerOccupation);
                    valueTextView.setText(consumerInfo.get(Consumer.CONSUMER_OCCUPATION).toString());
                }
                else if (consumerInfo.containsKey(Consumer.CONSUMER_BIRTHDATE)){
                    titleTextView.setText(R.string.consumerBirthdate);
                    valueTextView.setText(consumerInfo.get(Consumer.CONSUMER_BIRTHDATE).toString());
                }
                else if (consumerInfo.containsKey(Consumer.CONSUMER_WEBSITE)){
                    titleTextView.setText(R.string.consumerWebsite);
                    valueTextView.setText(consumerInfo.get(Consumer.CONSUMER_WEBSITE).toString());
                }
                else if (consumerInfo.containsKey(Consumer.CONSUMER_BIO)){
                    titleTextView.setText(R.string.consumerBio);
                    valueTextView.setText(consumerInfo.get(Consumer.CONSUMER_BIO).toString());
                }
            }

            return rowView;
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onConsumerInteraction(String id);
    }
}