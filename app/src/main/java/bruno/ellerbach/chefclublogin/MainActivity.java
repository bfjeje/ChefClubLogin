package bruno.ellerbach.chefclublogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bruno.ellerbach.chefclublogin.API.LoginAttempt;

public class MainActivity extends AppCompatActivity {

    Button buttonEnter;
    EditText editTextEmail, editTextPassword;
    TextView textViewForgotPassword, textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEnter = findViewById(R.id.buttonEnter);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);

        //Clicked on Forgot password button
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Forgot Password button clicked", Toast.LENGTH_LONG).show();
            }
        });

        //Clicked on Register button
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Register button clicked", Toast.LENGTH_LONG).show();
            }
        });

        //Clicked on Enter button
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfFieldsFilled()){
                    callTryToLogin(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                }
            }
        });

    }

    //method that checks if all fields are filled, and check if password is more than 8 characters
    private boolean checkIfFieldsFilled() {
        if(TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextEmail.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please, fill all fields", Toast.LENGTH_LONG).show();
            return false;
        }else if(editTextPassword.getText().length()<8){
            Toast.makeText(getApplicationContext(),"Password must have more than 8 characters", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    //starts the login task
    private void callTryToLogin(String email, String password){
        LoginAttempt la = new LoginAttempt(this);
        la.tryToLogIn(email, password);
    }
}
