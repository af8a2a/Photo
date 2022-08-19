/*
功力不足，暂且将就本地登录
来日改成服务器保存登录数据罢
 */
package com.example.photo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photo.util.DbLogin;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
//todo
//也许应该叫LoginActivity
//做的差不多再改罢

public class MainActivity extends AppCompatActivity {
    private Boolean bPwdSwitch=false;
    private EditText etPwd;
    private TextView signUp;
    private EditText username;
    private TextView reset;
    private boolean isLoginSuccess=false;
    private boolean isRegisterSucceess=false;
    public static Activity mActivityInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivityInstance=this;
        signUp=findViewById(R.id.tv_sign_up);
        final ImageView ivPwdSwitch=findViewById(R.id.iv_pwd_switch);
        etPwd=findViewById(R.id.et_pwd);
        username=findViewById(R.id.et_account);
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
                                    Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(MainActivity.this,"已存在该用户名！",Toast.LENGTH_SHORT).show();
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

        MaterialButton login=findViewById(R.id.bt_login);
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(isLoginSuccess){
                                        if(isLoginSuccess){
                                            Intent intent=new Intent(MainActivity.this,PhotoshowActivity.class);
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

        reset=findViewById(R.id.Reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ResetpasswordActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
            }
        });
    }
}