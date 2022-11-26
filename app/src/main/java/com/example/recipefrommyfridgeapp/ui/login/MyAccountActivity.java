package com.example.recipefrommyfridgeapp.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Locale;

public class MyAccountActivity extends AppCompatActivity{

    private TextView userName, userEmail, userPassword;
    private Button resetPasswordButton;
    private Button backButton;

    private LoggedInViewModel loggedInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Locale.setDefault(new Locale("zh"));
        Log.i("checkpoint6", "onCreate: " + Locale.getDefault().toString() + ", ");
        userName = findViewById(R.id.my_account_name);
        userEmail = findViewById(R.id.my_account_email);
        userPassword = findViewById(R.id.my_account_password);
        resetPasswordButton = findViewById(R.id.my_account_reset_password);
        backButton = findViewById(R.id.my_account_back);
        backButton.setOnClickListener(view -> {
            if (getFragmentManager().getBackStackEntryCount() > 0){
                getFragmentManager().popBackStack();
                return;
            }
            super.onBackPressed();
        });

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
                            // TODO: Not sure if this is the right place or I should have two observers
                            resetPasswordButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final DialogPlus dialogPlus = DialogPlus.newDialog(MyAccountActivity.this)
                                            .setContentHolder(new ViewHolder(R.layout.password_popup))
                                            .setExpanded(true, 1200)
                                            .create();
                                    View popup = dialogPlus.getHolderView();
                                    TextView name = popup.findViewById(R.id.password_popup_name);
                                    TextView email = popup.findViewById(R.id.password_popup_email);
                                    EditText password = popup.findViewById(R.id.password_popup_password);
                                    Button reset = popup.findViewById(R.id.password_popup_reset);
                                    name.setText(user.getName());
                                    email.setText(user.getEmail());
                                    password.setText(user.getPassword());
                                    dialogPlus.show();
                                    reset.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            loggedInViewModel.resetPassword(user.getName(), user.getEmail(), user.getPassword(), password.getText().toString().trim());
                                            dialogPlus.dismiss();
                                        }
                                    });
                                }
                            });
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


}
