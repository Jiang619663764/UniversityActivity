package com.example.huhu.universityactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBotton_Edit;
    private Button mBotton_Query;
    private Button mBotton_Update;

    private EditText mEdit_School;

    public static final String APP_ID="38a95ba1b238562b7bb4d8511ec38bcc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu);
        //初始化Bmob
        Bmob.initialize(getApplicationContext(),APP_ID);

        mBotton_Edit= (Button) findViewById(R.id.btn_edit);
        mBotton_Query= (Button) findViewById(R.id.btn_query);
        mBotton_Update= (Button) findViewById(R.id.btn_update);

        mEdit_School= (EditText) findViewById(R.id.edt_school_menu);

        mBotton_Query.setOnClickListener(this);
        mBotton_Edit.setOnClickListener(this);
        mBotton_Update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit:
                Intent intent=new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_query:
                Intent intent1=new Intent(MenuActivity.this,QueryActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_update:
                if(TextUtils.isEmpty(mEdit_School.getText().toString().trim())){
                    Toast.makeText(MenuActivity.this,"请输入学校名",Toast.LENGTH_LONG).show();

                }else{

                    Intent intent2=new Intent(MenuActivity.this,UpdateActivity.class);
                    intent2.putExtra("schoolName",mEdit_School.getText().toString());
                    startActivity(intent2);
                }

                break;
        }
    }
}
