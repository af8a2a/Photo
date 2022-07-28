package com.example.photo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Boolean bPwdSwitch=false;
    private EditText etPwd;
    private TextView signUp;
    private EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp=findViewById(R.id.tv_sign_up);
        final ImageView ivPwdSwitch=findViewById(R.id.iv_pwd_switch);
        etPwd=findViewById(R.id.et_pwd);
        username=findViewById(R.id.et_account);
        //数据库创建
        PhotoDB db=new PhotoDB(this,"USER.db",null,1);

        signUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                SQLiteDatabase userdb=db.getWritableDatabase();
                //输入的账号密码
                String pwd=etPwd.getText().toString();
                String user=username.getText().toString();
                //select
                Cursor cursor=userdb.rawQuery("SELECT * FROM USER",null);
                //数据库里的账号密码
                String dbUser = null;
                String dbPwd=null;
                while(cursor.moveToNext()){
                    dbUser=cursor.getString(cursor.getColumnIndex("username"));
                    dbPwd=cursor.getString(cursor.getColumnIndex("password"));

                }
                //数据库里有号就提示不用注册，没号就注册
                if(dbUser.equals(user)!=true){
                    ContentValues val=new ContentValues();
                    val.put("username",user);
                    val.put("password",pwd);
                    userdb.insert("USER",null,val);
                    Toast.makeText(MainActivity.this,"注册账号"+user,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"已存在账号"+user,Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView ResetPassword=findViewById(R.id.Reset);
        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                SQLiteDatabase userdb=db.getWritableDatabase();
                //输入的账号密码
                String pwd=etPwd.getText().toString();
                String user=username.getText().toString();
                //select
                Cursor cursor=userdb.rawQuery("SELECT * FROM USER",null);
                //数据库里的账号密码
                String dbUser = null;
                String dbPwd=null;
                while(cursor.moveToNext()){
                    dbUser=cursor.getString(cursor.getColumnIndex("username"));
                    dbPwd=cursor.getString(cursor.getColumnIndex("password"));
                }
                Intent intent=new Intent(MainActivity.this,ResetpasswordActivity.class);
                startActivity(intent);
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
    }
}