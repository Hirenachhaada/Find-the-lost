package com.dataflair.foundandlost.Adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dataflair.foundandlost.Model.Model;
import com.dataflair.foundandlost.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.HashMap;

public class LostItemAdapter extends FirebaseRecyclerAdapter<Model, LostItemAdapter.Viewholder> {

    public LostItemAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull LostItemAdapter.Viewholder holder, int position, @NonNull Model model) {

        Context context = holder.lostItemName.getContext();

        //for loading Lost items into recycler view
        holder.lostItemName.setText(model.getItemName());
        holder.lostItemColor.setText(model.getItemColor());
        holder.lostItemLocation.setText(model.getItemLocation());
        holder.lostItemPlace.setText(model.getItemPlace());
        holder.lostItemUserName.setText(model.getUserName());
        holder.lostItemUserPhoneNumber.setText(model.getUserPhoneNumber());

        holder.sendNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = model.getUserId();
                String key = FirebaseDatabase.getInstance().getReference().child("notification").push().getKey();
                HashMap<String, Object> sendDetails = new HashMap<>();

                //Getting user detials from GoogleSignin
                String userName = GoogleSignIn.getLastSignedInAccount(view.getContext()).getDisplayName();
                String gmail = GoogleSignIn.getLastSignedInAccount(view.getContext()).getEmail();

                sendDetails.put("userName", userName);
                sendDetails.put("gmailId", gmail);
                sendDetails.put("itemName", model.getItemName());
                sendDetails.put("itemLocation", model.getItemLocation());
                sendDetails.put("message", "i Found This item");

                //Adding data to fireabase
                FirebaseDatabase.getInstance().getReference().child("notifications").child(userId)
                        .child(key).updateChildren(sendDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast to show the message
                            Toast.makeText(context, "Notification Sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }


    @NonNull
    @Override
    public LostItemAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_file, parent, false);
        return new LostItemAdapter.Viewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {


        TextView lostItemName, lostItemColor, lostItemLocation, lostItemPlace, lostItemUserName, lostItemUserPhoneNumber;
        Button sendNotificationBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //assigning the address of the materials
            lostItemName = (TextView) itemView.findViewById(R.id.ItemNameTxt);
            lostItemColor = (TextView) itemView.findViewById(R.id.ItemColorTxt);
            lostItemLocation = (TextView) itemView.findViewById(R.id.ItemLocationTxt);
            lostItemPlace = (TextView) itemView.findViewById(R.id.ItemPlaceTxt);
            lostItemUserName = (TextView) itemView.findViewById(R.id.UserNameTxt);
            lostItemUserPhoneNumber = (TextView) itemView.findViewById(R.id.UserPhoneNumberTxt);

            sendNotificationBtn = (Button) itemView.findViewById(R.id.SendNotificationBtn);

        }
    }

}
