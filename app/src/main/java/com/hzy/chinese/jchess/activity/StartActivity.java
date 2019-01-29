package com.hzy.chinese.jchess.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import com.hzy.chinese.jchess.R;

import java.io.FileDescriptor;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

public class StartActivity extends AppCompatActivity {

    private int a=1;
    private Button button;
    private Button button2;
    private MediaPlayer mp;
    private AudioManager am;
    private int max;//最大音量
    private int current;//当前音量
    private int stepVolume;//一次增加的音量
    private GifImageView gifImageView;
    private GifDrawable gifFromAssets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bgMusic();
      //  gifImageButton = findViewById(R.id.gif);
        try {
            gifImageView=findViewById(R.id.gif);
            gifFromAssets = new GifDrawable( getAssets(), "jumpmusic.gif" );
            gifImageView.setImageDrawable(gifFromAssets);

//            final MediaController mediaController = new MediaController(this);
//            mediaController.setMediaPlayer((GifDrawable) gifImageView.getDrawable());
//            mediaController.setAnchorView(gifImageView);
            gifImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a==1) {
                        gifFromAssets.stop();
                        mp.pause();
                        a=0;
                    }else if(a==0){
                        gifFromAssets.start();
                        mp.start();
                        a=1;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        button=findViewById(R.id.start);
        button2=findViewById(R.id.exit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,SplashActivity.class);
                mp.stop();
                startActivity(intent);
                finish();
            }
        });
    }


    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(StartActivity.this);
        normalDialog.setIcon(R.mipmap.ic_launcher);
        normalDialog.setTitle("退出");
        normalDialog.setMessage("确定要退出吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
                       onDestroy();

                    }
                });
        normalDialog.setNegativeButton("算了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void bgMusic(){

        AssetManager asm = getAssets();
        AssetFileDescriptor afd = null;
        mp=new MediaPlayer();
        try {
            afd = asm.openFd("gsls.mp3");
            FileDescriptor fd = afd.getFileDescriptor();
            mp.setDataSource(fd, afd.getStartOffset(), afd.getLength());//设置路径
            mp.prepare();//缓冲
            mp.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //调用声音
//        am=(AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        max=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        stepVolume=max/8;
    }
    //退出时的时间
    private long mExitTime;

    //对返回键进行监听,屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}



