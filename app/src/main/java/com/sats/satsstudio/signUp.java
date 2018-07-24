package com.sats.satsstudio;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sats.satsstudio.Model.User;

public class signUp extends AppCompatActivity {

    EditText SatsPhone,SatsPassword,SatsName;
    Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SatsPhone = findViewById(R.id.SatsPhone);
        SatsName = findViewById(R.id.SatsName);
        SatsPassword = findViewById(R.id.SatsPassword);

        btnSignUp = findViewById(R.id.btnSignUp);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(signUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if already exist
                        if (dataSnapshot.child(SatsPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(signUp.this, "Phone Number already exist", Toast.LENGTH_SHORT).show();

                        }else {
                            mDialog.dismiss();
                            User user = new User(SatsName.getText().toString(),SatsPassword.getText().toString());
                            table_user.child(SatsPhone.getText().toString()).setValue(user);
                            Toast.makeText(signUp.this, "Sign Up successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
