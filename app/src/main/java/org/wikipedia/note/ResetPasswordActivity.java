package org.wikipedia.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import org.wikipedia.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText resetMail;
    private Button btnResetPass;

    //Firebase auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth= FirebaseAuth.getInstance();


        resetMail=findViewById(R.id.emai_reset_pass);
        btnResetPass=findViewById(R.id.reset_Btn_pass);


        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=resetMail.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    resetMail.setError("Email Required..");
                    return;
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Please Check your maill", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(getApplicationContext(),"Failed..", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });



    }
}
