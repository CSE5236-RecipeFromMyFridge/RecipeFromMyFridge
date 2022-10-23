package com.example.recipefrommyfridgeapp.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.User;
import com.example.recipefrommyfridgeapp.viewmodel.LoggedInViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userName, userEmail, userPassword;
    private Button resetPasswordButton;

    private LoggedInViewModel loggedInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        userName = findViewById(R.id.my_account_name);
        userEmail = findViewById(R.id.my_account_email);
        userPassword = findViewById(R.id.my_account_password);
        resetPasswordButton = findViewById(R.id.my_account_reset_password);
        resetPasswordButton.setOnClickListener(this);

        loggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);
        loggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    final FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference("Users").child(firebaseUser.getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            Log.d("checkpoint5", "the user retrieved");
                            userName.setText("User Name: " + user.getName());
                            userEmail.setText("User Email: " + user.getEmail());
                            userPassword.setText("User Password: " + user.getPassword());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseerror) {
                            Log.d("checkpoint5", "fail to retrieve the user");
                        }
                    });
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

    }

}
