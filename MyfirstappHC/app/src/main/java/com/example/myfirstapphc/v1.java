package com.example.myfirstapphc;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//public class v1 extends AppCompatActivity {
//    String otpget,c;
//    String check;
//    EditText n1, n2, n3, n4, n5, n6;
//    Button submit;
//@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_v1);
//        TextView t = findViewById(R.id.t);
//        Intent i = getIntent();
//        c=i.getStringExtra("numberph");
//        otpget = i.getStringExtra("otp12344321").toString();
//        t.setText("An OTP is sent to your number: +91" + c + ".");
//        submit = findViewById(R.id.submit);
//        n1 = findViewById(R.id.editTextNumber);
//        n2 = findViewById(R.id.editTextNumber2);
//        n3 = findViewById(R.id.editTextNumber3);
//        n4 = findViewById(R.id.editTextNumber4);
//        n5 = findViewById(R.id.editTextNumber5);
//        n6 = findViewById(R.id.editTextNumber6);
//       check= n1.getText().toString() +
//            n2.getText().toString() + n3.getText().toString() + n4.getText().toString() + n5.getText().toString() + n6.getText().toString();
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (check!= null)
//                {
//                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otpget, check);
//                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if(task.isSuccessful()) {
//                                        Intent intent = new Intent(v1.this, dashBoard.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                    }
//                                    else {
//                                        Toast.makeText(v1.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//                else {
//                     Toast.makeText(v1.this,"warn",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//        });
//    }
//}
//



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class v1 extends AppCompatActivity
{
    Button b2;
    String check;
    String n1, n2, n3, n4, n5, n6;
    String phonenumber;
    String otpid;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v1);
        n1 = findViewById(R.id.editTextNumber).toString();
        n2 = findViewById(R.id.editTextNumber2).toString();
        n3 = findViewById(R.id.editTextNumber3).toString();
        n4 = findViewById(R.id.editTextNumber4).toString();
        n5 = findViewById(R.id.editTextNumber5).toString();
        n6 = findViewById(R.id.editTextNumber6).toString();
        check =(String) n1+ (String)n2+(String) n3+(String) n4+(String) n5+(String) n6;


        phonenumber=getIntent().getStringExtra("Ve").toString();
        b2=(Button)findViewById(R.id.submit);
        mAuth=FirebaseAuth.getInstance();

        initiateotp();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check.isEmpty())
                    Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
                else if(check.length()!=6)
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,check);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }

    private void initiateotp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpid=s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                           Intent intent= new Intent(v1.this,DashB.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"Signin Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}