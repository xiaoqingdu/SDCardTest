package com.example.xiaoqingdu.sdcardtest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_write=(Button)findViewById(R.id.btn_write);
        Button btn_read=(Button)findViewById(R.id.btn_read);

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream out=null;
                try{
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File file=Environment.getExternalStorageDirectory();
                        File myfile=new File(file.getCanonicalPath()+"/"+"myfileName");
                        FileOutputStream fileOutputStream=new FileOutputStream(myfile);
                        out=new BufferedOutputStream(fileOutputStream);
                        EditText editText=(EditText)findViewById(R.id.textView);
                        String content=editText.getText().toString();
                        try {
                            out.write(content.getBytes(StandardCharsets.UTF_8));
                        }finally {
                            if(out!=null)
                                out.close();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream in=null;
                try{
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File file=Environment.getExternalStorageDirectory();
                        File myfile=new File(file.getCanonicalPath()+"/"+"myfileName");
                        FileInputStream fileInputStream=new FileInputStream(myfile);
                        in=new BufferedInputStream(fileInputStream);
                        int c;
                        StringBuilder stringBuilder=new StringBuilder("");
                        try{
                            while ((c=in.read())!=-1){
                                stringBuilder.append((char)c);
                            }
                            Toast.makeText(MainActivity.this, stringBuilder.toString(),Toast.LENGTH_LONG)
                                    .show();
                        }finally {
                            if(in!=null){
                                in.close();
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public boolean isExternalStorageWritable(){
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable(){
        String state=Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

}
