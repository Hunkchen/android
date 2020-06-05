package com.example.lbstest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    public static boolean flag = false;
    String[] mEnters = {"定位","初始化地图","地图定位","地图设置","地图绘画","检查权限"};



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest. permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest. permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest. permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList. size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.SEND_SMS);
        }
        else {

        }

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mEnters));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jumpTo(i);
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
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("请初始化地图");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(MapInit.class);
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void jumpTo(int position){
        switch (position){
            case 0:
                startActivity(LocationActivity.class);
                break;
            case 1:
                startActivity(MapInit.class);
                flag = true;
                break;
            case 2:
                if (flag)
                {
                    startActivity(MapLocationActivity.class);
                }
                else {
                    showNormalDialog();
                    flag = true;
                }
                break;
            case 3:
                if (flag)
                {
                    startActivity(MapViewSettingActivity.class);
                }
                else {
                    showNormalDialog();
                    flag = true;
                }
                break;
            case 4:
                if (flag)
                {
                    startActivity(DrawActivity.class);
                }
                else{
                    showNormalDialog();
                    flag = true;
                }
                break;
            case 5:
                checkPermission();
                break;
        }
    }

    private void startActivity(Class clz){
        startActivity(new Intent(this,clz));
    }



    private void checkPermission(){
        //1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            //2
//            int result = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            //PackageManager.PERMISSION_GRANTED  PackageManager.PERMISSION_DENIED
//            if (result != PackageManager.PERMISSION_GRANTED){
//                toast("没权限");
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},666);
//            }else {
//                toast("有权限");
//            }

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                toast("上次点了不在询问");
            }else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},666);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        toast(" "+requestCode+"  "+ Arrays.toString(permissions)+"  "+Arrays.toString(grantResults));
        Log.e("666"," "+requestCode+"  "+permissions+"  "+grantResults);
    }

    private void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}