package co.example.um2.aigle.alo.Common.Commerce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.GetItemsTask;
import co.example.um2.aigle.alo.Common.Commerce.ListItems.Item;
import co.example.um2.aigle.alo.Common.Commerce.ListItems.ItemAdapter;
import co.example.um2.aigle.alo.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Commerce_ByList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Commerce_ByList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Commerce_ByList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView itemsRV;
    private ItemAdapter itemAdapter;
    private Button vendreButton;
    private LocationManager locationManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Commerce_ByList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Commerce_ByList.
     */
    // TODO: Rename and change types and number of parameters
    public static Commerce_ByList newInstance(String param1, String param2) {
        Commerce_ByList fragment = new Commerce_ByList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_commerce__by_list, container, false);
        locationManager = (LocationManager) v.getContext().getSystemService(v.getContext().LOCATION_SERVICE);
        vendreButton = (Button) v.findViewById(R.id.vendreButton);
        vendreButton.setOnClickListener(new View.OnClickListener() {
            boolean gps_enabled = false;
            boolean network_enable = false;
            @Override
            public void onClick(View v) {
                try{
                    gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    network_enable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                }catch (Exception e){
                    Log.d("Error", e.getMessage());
                }
                if(!gps_enabled && !network_enable){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Erreur");
                    builder.setMessage("Erreur, Connexion ou GPS desactivé");
                    builder.setPositiveButton("ok", null);
                    AlertDialog a = builder.create();
                    a.show();
                }else {
                    Intent intent = new Intent(v.getContext(), VendreActivity.class);
                    startActivity(intent);
                }
            }
        });
        itemsRV = (RecyclerView) v.findViewById(R.id.itemsRV);

        GetItemsTask getItemsTask = new GetItemsTask(container.getContext());
        List<Item> items = new ArrayList<Item>();

        try {
            items = getItemsTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        itemsRV.setLayoutManager(new LinearLayoutManager(container.getContext()));
        itemAdapter = new ItemAdapter(items);
        itemsRV.setAdapter(itemAdapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
