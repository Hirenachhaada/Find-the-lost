package com.dataflair.foundandlost.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dataflair.foundandlost.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddLostItemFragment extends Fragment {

    EditText lostItemNameEditTxt, lostItemColorEditTxt, lostItemLocationEditTxt, lostItemPlaceEditTxt, lostItemUserEditTxt, lostItemPhoneNumberEditTxt;
    Button addItemBtn;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddLostItemFragment() {
        // Required empty public constructor
    }

    public static AddLostItemFragment newInstance(String param1, String param2) {
        AddLostItemFragment fragment = new AddLostItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_lost_item, container, false);

        //Assigning all the Addresses of the Android Materials
        lostItemNameEditTxt = (EditText) view.findViewById(R.id.LostItemName);
        lostItemColorEditTxt = (EditText) view.findViewById(R.id.LostItemColor);
        lostItemLocationEditTxt = (EditText) view.findViewById(R.id.LostItemLocation);
        lostItemPlaceEditTxt = (EditText) view.findViewById(R.id.LostItemPlace);
        lostItemUserEditTxt = (EditText) view.findViewById(R.id.LostItemUserName);
        lostItemPhoneNumberEditTxt = (EditText) view.findViewById(R.id.LostItemPhoneNumber);

        addItemBtn = (Button) view.findViewById(R.id.AddLostItemBtn);

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting text from editText fields to add data to fireabase
                String ItemName = lostItemNameEditTxt.getText().toString();
                String ItemColor = lostItemColorEditTxt.getText().toString();
                String ItemLocation = lostItemLocationEditTxt.getText().toString();
                String ItemPlace = lostItemPlaceEditTxt.getText().toString();
                String UserName = lostItemUserEditTxt.getText().toString();
                String UserPhoneNumber = lostItemPhoneNumberEditTxt.getText().toString();

                //Checking for empty filed
                if (ItemName.isEmpty() || ItemColor.isEmpty() || ItemLocation.isEmpty() || ItemPlace.isEmpty() || UserName.isEmpty() || UserPhoneNumber.isEmpty()) {
                    Toast.makeText(getContext(), "Please,Enter All Details", Toast.LENGTH_SHORT).show();
                } else {
                    //Calling the method to add data to fireabase
                    addItem(ItemName, ItemColor, ItemLocation, ItemPlace, UserName, UserPhoneNumber);
                }
            }
        });
        return view;
    }

    private void addItem(String itemName, String itemColor, String itemLocation, String itemPlace, String userName, String userPhoneNumber) {

        //Hashmap to store Lost item details
        HashMap<String, Object> item_details = new HashMap<>();

        //Getting user id
        String userId = GoogleSignIn.getLastSignedInAccount(getContext()).getId();

        //Generating unique key
        String key = FirebaseDatabase.getInstance().getReference().child("lostItems").push().getKey();

        //Adding lost item details to hashmap
        item_details.put("itemName", itemName);
        item_details.put("itemColor", itemColor);
        item_details.put("itemLocation", itemLocation);
        item_details.put("itemPlace", itemPlace);
        item_details.put("userName", userName);
        item_details.put("userPhoneNumber", userPhoneNumber);
        item_details.put("userId", userId);


        //Adding lost item details to fireabase
        FirebaseDatabase.getInstance().getReference().child("lostItems")
                .child(key)
                .updateChildren(item_details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {

                if (task.isSuccessful()) {

                    //Setting the edit text to blank  after successfully adding the data to fireabse
                    Toast.makeText(getContext(), "Item Details Added Successfully", Toast.LENGTH_SHORT).show();
                    lostItemNameEditTxt.setText("");
                    lostItemColorEditTxt.setText("");
                    lostItemLocationEditTxt.setText("");
                    lostItemPlaceEditTxt.setText("");
                    lostItemUserEditTxt.setText("");
                    lostItemPhoneNumberEditTxt.setText("");

                }

            }
        });


    }
}