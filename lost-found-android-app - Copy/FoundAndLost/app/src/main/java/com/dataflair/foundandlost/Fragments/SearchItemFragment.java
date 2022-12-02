package com.dataflair.foundandlost.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dataflair.foundandlost.Adpaters.FoundItemAdapter;
import com.dataflair.foundandlost.Adpaters.LostItemAdapter;
import com.dataflair.foundandlost.Model.Model;
import com.dataflair.foundandlost.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.FirebaseDatabase;

public class SearchItemFragment extends Fragment {

    RecyclerView recyclerView;
    FoundItemAdapter adapter;
    EditText searchItem;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchItemFragment() {
        // Required empty public constructor
    }

    public static SearchItemFragment newInstance(String param1, String param2) {
        SearchItemFragment fragment = new SearchItemFragment();
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
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_search_item, container, false);

        //Assigning the Recyclerview to show all found items
        recyclerView = (RecyclerView) view.findViewById(R.id.LostItemRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Edit text to take input for searching
        searchItem=(EditText)view.findViewById(R.id.LostItemSearchEditTxt);

        //Search implementation
       searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt =searchItem.getText().toString().trim();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("foundItems").orderByChild("itemName").startAt(searchtxt).endAt(searchtxt + "\uf8ff"), Model.class)
                                .build();

                adapter = new FoundItemAdapter(options);
                adapter.startListening();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt = searchItem.getText().toString().trim();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("foundItems").orderByChild("itemName").startAt(searchtxt).endAt(searchtxt + "\uf8ff"), Model.class)
                                .build();

                adapter = new FoundItemAdapter(options);
                adapter.startListening();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charSequence = editable.toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("foundItems").orderByChild("itemName").startAt(charSequence).endAt(charSequence + "\uf8ff"), Model.class)
                                .build();

                adapter = new FoundItemAdapter(options);
                adapter.startListening();
                recyclerView.setAdapter(adapter);

            }
        });


        //Getting admin id from gmail
        String adminId = GoogleSignIn.getLastSignedInAccount(getContext()).getId();

        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foundItems"), Model.class)
                        .build();


        //Setting adapter to RecyclerView
        adapter = new FoundItemAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
        adapter.stopListening();
    }
}