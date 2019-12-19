package com.electrosoft.newsapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.activities.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;


public class frag_signin extends Fragment {

    private Button signIn;
    private EditText userEmail,userPass;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String TAG = "ABCTAG";

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragMentView = inflater.inflate(R.layout.fragment_frag_signin, container, false);


        mAuth = FirebaseAuth.getInstance();

        userEmail = fragMentView.findViewById(R.id.userEmail);
        userPass = fragMentView.findViewById(R.id.userPass);
        signIn = fragMentView.findViewById(R.id.mSigninBtn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(everyThingGood())
                gotoSignIn();
            }
        });

        return fragMentView;
    }

    private void gotoSignIn() {
        mAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(getActivity(), Dashboard.class));
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Invalid Email or Password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private boolean everyThingGood() {
        boolean good =true;


        if(userEmail.getText().toString().equals(""))
        {
            good=false;
            userEmail.setError("Email is Empty");
        }
        if(userPass.getText().toString().equals(""))
        {
            good=false;
            userPass.setError("Please Enter Password");
        }

        return good;
    }


}
