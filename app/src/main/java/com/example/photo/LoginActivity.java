/*
功力不足，暂且将就本地登录
来日改成服务器保存登录数据罢
 */
package com.example.photo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photo.util.DbLogin;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private Boolean bPwdSwitch=false;
    private EditText etPwd;
    private TextView signUp;
    private EditText username;
    private TextView reset;
    private boolean isLoginSuccess=false;
    private boolean isRegisterSucceess=false;
    public static Activity mActivityInstance;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.et_account);
        etPwd=findViewById(R.id.et_pwd);
        signUp=findViewById(R.id.tv_sign_up);
        ImageView ivPwdSwitch=findViewById(R.id.iv_pwd_switch);
        checkBox=findViewById(R.id.cb_remember_pwd);
        MaterialButton login=findViewById(R.id.bt_login);
        reset=findViewById(R.id.Reset);
        SharedPreferences preferences=getSharedPreferences("login",MODE_PRIVATE);
        mActivityInstance=this;
        boolean skip=preferences.getBoolean("skip",false);

        if(skip){
            username.setText(preferences.getString("username",""));
            etPwd.setText(preferences.getString("password",""));
            Intent intent=new Intent(LoginActivity.this,PhotoshowActivity.class);
            intent.putExtra("username",username.getText().toString());
            startActivity(intent);
            finish();
        }else{
            username.setText(preferences.getString("username",""));
        }


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd=etPwd.getText().toString();
                String user=username.getText().toString();
                DbLogin.register(user, pwd, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String res=response.body().string();
                        if(response.isSuccessful()&&res.equals("true")){
                            isRegisterSucceess=true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(isRegisterSucceess)
                                    Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(LoginActivity.this,"已存在该用户名！",Toast.LENGTH_SHORT).show();
                                    isRegisterSucceess=false;
                                }
                            });
                        }
                    }
                });
            }
        });
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch=!bPwdSwitch;
                if(bPwdSwitch){
                    ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_24);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pwd=etPwd.getText().toString();
                String user=username.getText().toString();
                DbLogin.login(user, pwd, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String res=response.body().string();
                        if(response.isSuccessful()&&res.equals("true")){
                            isLoginSuccess=true;
                            SharedPreferences file=getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit=file.edit();
                            edit.putString("username",user);
                            if(checkBox.isChecked()){
                                edit.putString("password",pwd);
                                edit.putBoolean("skip",true);
                                edit.apply();
                            }else{
                                edit.putString("password","");
                                edit.putBoolean("skip",false);
                                edit.apply();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(isLoginSuccess){
                                        if(isLoginSuccess){
                                            Intent intent=new Intent(LoginActivity.this,PhotoshowActivity.class);
                                            intent.putExtra("username",username.getText().toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                        isLoginSuccess=false;
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ResetpasswordActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
            }
        });
    }
}