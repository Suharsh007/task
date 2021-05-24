package in.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, confPassword, etDob;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        confPassword = findViewById(R.id.confPassword);
        etDob = findViewById(R.id.etDob);
        btnSubmit = findViewById(R.id.btnSubmit);
         btnSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name = etName.getText().toString();
                 String email = etEmail.getText().toString();
                 String password = etPassword.getText().toString();
                 String dob = etDob.getText().toString();
                 String confPass = confPassword.getText().toString();
                 if(confPass.equals(password))
                 {
                 User user = new User();
                 user.setName(name);
                 user.setEmail(email);
                 user.setPassword(password);
                 user.setDateofBirth(dob);
                 if(validateUser(user))
                 {
                     UserDB userDB = UserDB.getUserDB(getApplicationContext());
                     UserDao userDao = userDB.userDao();
                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             userDao.registerUser(user);
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                     Intent in = new Intent(MainActivity.this, LoginActivity.class);
                                     startActivity(in);
                                     finish();

                                 }
                             });

                         }
                     }).start();
                 }
                 else
                 {
                     Toast.makeText(MainActivity.this, "Enter correct input", Toast.LENGTH_SHORT).show();
                 }
             }
                 else
                 {
                     Toast.makeText(MainActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                 }
             }

         });
    }
    private Boolean validateUser(User user)
    {
        if(user.getName().isEmpty() || user.getPassword().isEmpty() || user.getDateofBirth().isEmpty()
        || user.getEmail().isEmpty())
        {
            return false;
        }

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(user.getEmail()).matches();
    }
}