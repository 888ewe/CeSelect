package com.chenmo.selcetimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;

import java.util.ArrayList;

import static com.donkingliang.imageselector.utils.ImageSelectorUtils.openPhoto;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_CODE = 100;
    private static final int CE_CODE = 200;
    private Bitmap bitmap;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView)findViewById(R.id.image);
    }
    public void camera(View  view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_CODE);
    }
    public void ce(View view){
        openPhoto(this, CE_CODE, false, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(requestCode==CAMERA_CODE && data != null) {//相机
                Bundle bundle=data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
            }
            if(requestCode==CE_CODE && data != null) {//相册
                //获取选择器返回的数据
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                String path = images.get(0);
                bitmap=BitmapFactory.decodeFile(path);
            }
            if(bitmap!=null) image.setImageBitmap(bitmap);
        }
    }
}
