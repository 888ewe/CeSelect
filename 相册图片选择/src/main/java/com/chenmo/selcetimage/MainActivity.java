package com.chenmo.selcetimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.donkingliang.imageselector.utils.ImageSelectorUtils.openPhoto;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_CODE = 100;
    private static final int CE_CODE = 200;
    private Bitmap bitmap;
    private ImageView image;
    private String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        String path = Environment.getExternalStorageDirectory().getPath();
        picPath = path + "/" + "temp2.jpg";
    }

    public void camera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(picPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMERA_CODE);
    }

    public void ce(View view) {
        openPhoto(this, CE_CODE, false, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CODE) {//相机
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(picPath);
                    bitmap = BitmapFactory.decodeStream(fis);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                FileOutputStream fos=null;
                try{
                    fos=new FileOutputStream(picPath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,10,fos);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            if (requestCode == CE_CODE && data != null) {//相册
                //获取选择器返回的数据
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                String path = images.get(0);
                bitmap = BitmapFactory.decodeFile(path);
            }
            if (bitmap != null) image.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

