package com.example.huhu.universityactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.huhu.universityactivity.model.SchoolPushInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateActivity extends AppCompatActivity {

    private String schoolName;
    private String id;

    private TextView mEdt_school;
    private EditText mEdt_student;
    private EditText mEdt_student_new;
    private EditText mEdt_monster;
    private EditText mEdt_monster_phone;
    private EditText mEdt_manager;
    private EditText mEdt_manager_phone;
    private EditText mEdt_user;

    private TextView mEdt_progress;

    private Button mBtn_Update;

    private OptionsPickerView mPickerView;
    //progress集合
    ArrayList<String> pro = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update);
        initView();
        Intent intent = getIntent();
        schoolName = intent.getStringExtra("schoolName");
        queryData();
        getProgressData();
        progressPicker();
        mEdt_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickerView.show();
            }
        });


        mBtn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchoolPushInfo info=new SchoolPushInfo();
                info.setStudentCount(Integer.parseInt(mEdt_student.getText().toString()));
                info.setNewStudentCount(Integer.parseInt(mEdt_student_new.getText().toString()));
                info.setMonsterName(mEdt_monster.getText().toString());
                info.setMonsterPhone(mEdt_monster_phone.getText().toString());
                info.setManagerName(mEdt_manager.getText().toString());
                info.setManagerPhone(mEdt_manager_phone.getText().toString());
                info.setUserName(mEdt_user.getText().toString());
                info.setProgress(mEdt_progress.getText().toString());
                info.update(UpdateActivity.this, id, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText( UpdateActivity.this,"更新成功",Toast.LENGTH_LONG).show();
                        UpdateActivity.this.finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText( UpdateActivity.this,"更新失败",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

    /**
     * 获取数据
     */
    private void getProgressData() {
        pro.add("进入");
        pro.add("准入");
        pro.add("不反对");
        pro.add("反对");
        pro.add("其他");
    }

    /**
     * pickerview实例化
     */
    private void progressPicker() {
        mPickerView = new OptionsPickerView(this);
        mPickerView.setPicker(pro);
        mPickerView.setCyclic(false);
        mPickerView.setTitle("选择进度");
        mPickerView.setSelectOptions(0);
        mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String str = pro.get(options1);
                mEdt_progress.setText(str);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mEdt_school = (TextView) findViewById(R.id.edt_school_name_update);
        mEdt_student = (EditText) findViewById(R.id.edt_student_num_update);
        mEdt_student_new = (EditText) findViewById(R.id.edt_student_num_new_update);
        mEdt_monster = (EditText) findViewById(R.id.edt_monster_name_update);
        mEdt_monster_phone = (EditText) findViewById(R.id.edt_monster_phone_update);
        mEdt_manager = (EditText) findViewById(R.id.edt_manager_name_update);
        mEdt_manager_phone = (EditText) findViewById(R.id.edt_manager_phone_update);
        mEdt_user = (EditText) findViewById(R.id.edt_user_update);

        mEdt_progress= (TextView) findViewById(R.id.edt_progress_update);

        mBtn_Update= (Button) findViewById(R.id.btn_update_submit);
    }

    /**
     * 根据学校名查询数据
     */
    private void queryData() {
        BmobQuery<SchoolPushInfo> bmob = new BmobQuery<SchoolPushInfo>();
        bmob.addWhereEqualTo("schoolName", schoolName);
        bmob.findObjects(this, new FindListener<SchoolPushInfo>() {
            @Override
            public void onSuccess(List<SchoolPushInfo> list) {
                if(list.size()==0){
                    Toast.makeText(UpdateActivity.this, "没有相应数据", Toast.LENGTH_LONG).show();
                    UpdateActivity.this.finish();
                }else{
                    Toast.makeText(UpdateActivity.this, "查询成功", Toast.LENGTH_LONG).show();
                    for (SchoolPushInfo data : list) {
                        id = data.getObjectId();
                        mEdt_school.setText(data.getSchoolName());
                        mEdt_student.setText(data.getStudentCount()+"");
                        mEdt_student_new.setText(data.getNewStudentCount()+"");
                        mEdt_monster.setText(data.getMonsterName());
                        mEdt_monster_phone.setText(data.getMonsterPhone());
                        mEdt_manager.setText(data.getManagerName());
                        mEdt_manager_phone.setText(data.getManagerPhone());
                        mEdt_user.setText(data.getUserName());

                        mEdt_progress.setText(data.getProgress());

                    }
                }


            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(UpdateActivity.this, "查询失败,请输入已存在的学校名", Toast.LENGTH_LONG).show();
                UpdateActivity.this.finish();
            }
        });
    }

}
