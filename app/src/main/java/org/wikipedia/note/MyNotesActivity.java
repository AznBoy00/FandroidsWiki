package org.wikipedia.note;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.wikipedia.R;

public class MyNotesActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btnLogin;
    private TextView forgetPass;
    private TextView signUp;

    //Firebase

    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        mAuth= FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),NoteActivity.class));
        }

        mDialog=new ProgressDialog(this);

        LoginFunction();
    }

    private void LoginFunction(){

        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);
        forgetPass=findViewById(R.id.FORGETPASS_login);
        signUp=findViewById(R.id.signup_login);

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mEmail=email.getText().toString().trim();
                String mPassword=password.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    email.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(mPassword)){
                    password.setError("Required Field..");
                    return;
                }


                mDialog.setMessage("Processing..");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),NoteActivity.class));
                            Toast.makeText(getApplicationContext(),"Login Complete", Toast.LENGTH_SHORT).show();
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        });


    }

}
