package com.santiagogil.a100units.view.onboarding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.santiagogil.a100units.R;
import com.santiagogil.a100units.utils.DatabaseHelper;
import com.santiagogil.a100units.utils.FirebaseHelper;
import com.santiagogil.a100units.view.MainActivity;

public class RegisterFragment extends Fragment {

    private AutoCompleteTextView editTextNameField;
    private AutoCompleteTextView editTextEmailField;
    private EditText editTextPasswordField;
    private EditText editTextConfirmPasswordField;

    private Button buttonRegister;

    private FirebaseAuth fAuth;

    private ProgressDialog progressDialog;

    public static final String EMAIL = "email";


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTextNameField = (AutoCompleteTextView) view.findViewById(R.id.name);
        editTextEmailField = (AutoCompleteTextView) view.findViewById(R.id.email);
        editTextPasswordField = (EditText) view.findViewById(R.id.password);
        editTextConfirmPasswordField = (EditText) view.findViewById(R.id.confirmPassword);
        buttonRegister = (Button) view.findViewById(R.id.email_register_button);

        fAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getContext());

        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startRegister();

            }
        });

        Bundle bundle = getArguments();

        if(bundle != null){
            editTextEmailField.setText(bundle.getString(EMAIL));
        }

        return view;
    }
    private void startRegister() {

        final String name = editTextNameField.getText().toString().trim();
        String email = editTextEmailField.getText().toString().trim();
        String password = editTextPasswordField.getText().toString().trim();
        String confirmedPassword = editTextConfirmPasswordField.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmedPassword)){

            if (password.equals(confirmedPassword)){

                if(password.length()<6){

                    Toast.makeText(getContext(), getString(R.string.error_invalid_password) , Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog.setMessage("Signing Up...");
                    progressDialog.show();

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseHelper firebaseHelper = new FirebaseHelper();

                                firebaseHelper.getFirebaseDatabase().getReference().child(DatabaseHelper.TABLEUSERS).
                                        child(firebaseHelper.getCurrentUserID()).
                                        child("image").setValue("default");
                                firebaseHelper.getFirebaseDatabase().getReference().child(DatabaseHelper.TABLEUSERS)
                                        .child(firebaseHelper.getCurrentUserID())
                                        .child("name").setValue(name);

                                progressDialog.dismiss();
                                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);


                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Register Problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
            else{
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), "Fields are Empty", Toast.LENGTH_SHORT).show();
        }
    }

}
