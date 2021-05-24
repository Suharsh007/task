package in.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
EditText etEmail1, etPassword1;
TextView tvRegister;
Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        etEmail1 = findViewById(R.id.etEmail1);
        etPassword1 = findViewById(R.id.etPassword1);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail1.getText().toString();
                String password = etPassword1.getText().toString();
                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    UserDB userDB = UserDB.getUserDB(getApplicationContext());
                    UserDao userDao = userDB.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.login(email,password);
                            if(user == null)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                String name = user.getName();
                                String email = user.getEmail();
                                String dob = user.getDateofBirth();
                                Intent intent = new Intent(LoginActivity.this,DisplayPage.class);
                                intent.putExtra("Name",name);
                                intent.putExtra("Email",email);
                                intent.putExtra("DOB",dob);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).start();
                }
            }
        });
    }
}