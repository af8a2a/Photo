package com.example.photo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
        //PhotoDB db=new PhotoDB(this,"USER.db",null,1);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoDB db=new PhotoDB(MainActivity.this,"USER.db",null,1);
                SQLiteDatabase userdb=db.getWritableDatabase();
                String pwd=etPwd.getText().toString();
                String user=username.getText().toString();
                String[] targetCol={"username"};
                Cursor c=userdb.query("USER",targetCol,user,null,null,null,null);
                if(c==null){
                    ContentValues val=new ContentValues();
                    val.put("username",user);
                    val.put("password",pwd);
                    userdb.insert("USER",null,val);
                }else{
                    Toast.makeText(MainActivity.this,"已存在账号"+user,Toast.LENGTH_SHORT).show();
                }
                if(c!=null) c.close();
                db.close();
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