package com.example.photo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photo.util.DbLogin;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//重置密码 todo
public class ResetpasswordActivity extends AppCompatActivity {
    private ImageView passwordShow;
    private Boolean bPwdSwitch=false;
    private TextView passwordOnce;
    private TextView passwordSecond;
    private Button resetButton;
    private TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);
        passwordShow=findViewById(R.id.passwordShow);
        passwordOnce=findViewById(R.id.editTextTextPassword);
        passwordSecond=findViewById(R.id.editTextTextPassword2);
        user=findViewById(R.id.textView);

        user.setText(getIntent().getStringExtra("username"));
        passwordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch=!bPwdSwitch;
                if(bPwdSwitch){
                    passwordShow.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordOnce.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordSecond.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    passwordShow.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordOnce.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    passwordOnce.setTypeface(Typeface.DEFAULT);
                    passwordSecond.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    passwordSecond.setTypeface(Typeface.DEFAULT);
                }
            }
        });

        resetButton=findViewById(R.id.resetPassword_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password1=passwordOnce.getText().toString();
                String password2=passwordSecond.getText().toString();
                if(password1.equals(password2)){
                    DbLogin.reset(user.getText().toString(), password2, new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(response.isSuccessful()){
                                        Toast.makeText(ResetpasswordActivity.this,"重置成功",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(ResetpasswordActivity.this,"失败....",Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });
                        }
                    });
                }
            }
        });
    }
}