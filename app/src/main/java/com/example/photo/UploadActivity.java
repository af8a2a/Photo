package com.example.photo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.photo.Entity.JsonUtil.ImageJson;
import com.example.photo.Entity.UserImage;
import com.example.photo.util.ImageServerUtil;
import com.example.photo.util.ImageUploader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class UploadActivity extends AppCompatActivity {
    private MaterialButton selectImage;
    private MaterialButton commit;
    private ImageView thumail;
    private EditText Title;
    private String username;
    private int icon=-1;
    private String filePath;
    private ShapeableImageView temp;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            // 从相册返回的数据
            //Log.e(this.getClass().getName(), "Result:" + data.toString());
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();

                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(
                        uri,
                        filePathColumns,
                        null,
                        null,
                        null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
// 获取图片的存储路径

                filePath = cursor.getString(columnIndex);
                Glide.with(this).load(uri).into(thumail);

// 获取数据完毕后, 关闭游标
                cursor.close();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        thumail=findViewById(R.id.thumbnail);
        selectImage=findViewById(R.id.select_image);
        Title=findViewById(R.id.editTextTextPersonName);
        commit=findViewById(R.id.commit);
        username=getIntent().getStringExtra("username");
        icon=getIntent().getIntExtra("icon",-1);
        temp=findViewById(R.id.nav_icon_image);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 3);
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageJson imageJson=new ImageJson();
                imageJson.setAuthor(username);
                imageJson.setStar(0);
                imageJson.setTitle(Title.getText().toString());
                UserImage userImage=new UserImage();
                userImage.setUsername(username);
                ImageUploader.upload(filePath,imageJson);
                userImage.setUser_img(imageJson.getPic_url());
                if(icon!=-1){
                    ImageServerUtil.UpdateUserAvatar(userImage);
                    Glide.with(getApplicationContext()).load(userImage.getUser_img()).into(temp);
                }
                finish();
            }
        });
    }
}