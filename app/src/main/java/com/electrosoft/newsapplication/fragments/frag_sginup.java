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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;


public class frag_sginup extends Fragment {
    public interface OnEmailCheckListener{
        void onSucess(boolean isRegistered);
    }
    private String TAG = "ABCTAG";
    private EditText userName,userEmail,userPass,userPass2;
    private Button signUp;


    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragMentView = inflater.inflate(R.layout.fragment_frag_sginup, container, false);

        mAuth = FirebaseAuth.getInstance();

        userName = fragMentView.findViewById(R.id.name);
        userEmail = fragMentView.findViewById(R.id.userEmail);
        userPass = fragMentView.findViewById(R.id.userPass);
        userPass2 = fragMentView.findViewById(R.id.userPass2);
        signUp = fragMentView.findViewById(R.id.mSignupBtn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( everyThingGood())
                {
                    isCheckEmail(userEmail.getText().toString(),new OnEmailCheckListener(){
                        @Override
                        public void onSucess(boolean isRegistered) {

                            if(isRegistered){
                                //The email was registered before
                                Toast.makeText(getContext(), "Email ALready Registered.", Toast.LENGTH_SHORT).show();
                            } else {
                                //The email not registered before
                                doSignUp();
                            }
                        }
                    });

                }
            }
        });

        return fragMentView;
    }

    private void doSignUp()
    {


        mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                           // Toast.makeText(getContext(), "Successfull", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName.getText().toString()).build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getActivity(), Dashboard.class));
                                        getActivity().finish();
                                        Log.d("Display name: ", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                    }
                                }
                            });;

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Something went wrong.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    private boolean everyThingGood() {
        boolean good =true;

        if(userName.getText().toString().equals(""))
        {
            good=false;
            userName.setError("Name Required");
        }
        if(userEmail.getText().toString().equals(""))
        {
            good=false;
            userEmail.setError("Email is Required");
        }
        else if(!userEmail.getText().toString().contains("@"))
        {
            good=false;
            userEmail.setError("Email is Invalid");
        }
        if(userPass.getText().toString().equals(""))
        {
            good=false;
            userPass.setError("Password is Required");
        }
        else if(userPass.getText().toString().length()<6)
        {
            good=false;
            userPass.setError("Password must be atleast 6 Characters");
        }
        else if(!userPass2.getText().toString().equals(userPass.getText().toString()))
        {
            good=false;
            userPass2.setError("Passwords do not match");
        }
        return good;
    }


    public void isCheckEmail(final String email,final OnEmailCheckListener listener){
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>()
        {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task)
            {
                boolean check = !task.getResult().getSignInMethods().isEmpty();

                listener.onSucess(check);

            }
        });

    }
}
