package co.example.um2.aigle.alo.Common.Commerce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.GetCategoriesTask;
import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.GetItemsByCategorieTask;
import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.GetItemsByResearchTask;
import co.example.um2.aigle.alo.Common.Commerce.ItemsPersistence.GetItemsTask;
import co.example.um2.aigle.alo.Common.Commerce.ListItems.Item;
import co.example.um2.aigle.alo.Common.Commerce.ListItems.ItemAdapter;
import co.example.um2.aigle.alo.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    private Spinner categoriesSpinner;
    private Button searchButton;
    private EditText searchText;
    private Button favorisButton;
    private List<Item> items;
    static List<String> categories;

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

        searchText = (EditText) v.findViewById(R.id.researchText);
        searchButton = (Button) v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetItemsByResearchTask getItemsByResearchTask = new GetItemsByResearchTask(items, itemAdapter, v.getContext());
                List<Item> itemsResearch;
                try {
                    String str[] = categoriesSpinner.getSelectedItem().toString().split(" : ");

                    itemsResearch = getItemsByResearchTask.execute(searchText.getText().toString(), str[0]).get();
                    itemAdapter = new ItemAdapter(itemsResearch);
                    itemsRV.setAdapter(itemAdapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        items = new ArrayList<Item>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        itemsRV = (RecyclerView) v.findViewById(R.id.itemsRV);
        itemAdapter = new ItemAdapter(items);
        itemsRV.setLayoutManager(mLayoutManager);
        itemsRV.setAdapter(itemAdapter);
        GetItemsTask getItemsTask = new GetItemsTask(container.getContext(), items, itemAdapter);


        categoriesSpinner = (Spinner) v.findViewById(R.id.categoriesSpinner);
        categories = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, categories);
        categoriesSpinner.setAdapter(arrayAdapter);
        GetCategoriesTask getCategoriesTask = new GetCategoriesTask(container.getContext(),categoriesSpinner, itemsRV, categories, arrayAdapter, itemAdapter, items);
        /***************** Array Adapter pour les catégories ****************/





        getItemsTask.execute();
        getCategoriesTask.execute();

        favorisButton = (Button) v.findViewById(R.id.favorisButton);
        favorisButton.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sharedPreferences;
            SharedPreferences.Editor editor;
            Set<String> favoris;

            @Override
            public void onClick(View v) {
                sharedPreferences = v.getContext().getSharedPreferences("AloAloPreferences", Context.MODE_PRIVATE);
                this.editor = sharedPreferences.edit();

                /*** Il faut vérifier si le set des préférences est déjà créé sinon il faut le créer ***/
                favoris = sharedPreferences.getStringSet("favoris", null);
                if(favoris == null){
                    favoris = new HashSet<String>();
                }

                String fav = categoriesSpinner.getSelectedItem().toString() + " &bptkce& " + searchText.getText().toString();
                favoris.add(fav);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Vous allez ajouter cet élément de recherche dans votre favoris : \nCategorie : "
                + categoriesSpinner.getSelectedItem().toString() + "\nRecherche : " + searchText.getText().toString() + "\n"
                +"Confirmez cela entrainera la reception de notification. Vous pouvez supprimer la préférence dans la configuration");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putStringSet("favoris", favoris);
                        editor.commit();
                    }
                }).setNegativeButton("Annuler", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

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
