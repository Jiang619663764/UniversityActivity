package com.example.huhu.universityactivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huhu.universityactivity.model.SchoolPushInfo;
import com.example.huhu.universityactivity.util.NetUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class QueryActivity extends AppCompatActivity {

    private EditText mEdt_School_Query;
    private Button mBtn_Submit_Query;
    private TextView mTxt_Detail_Query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_query);
        initView();
        mBtn_Submit_Query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.checkNet(QueryActivity.this)) {
                    queryData();
                } else {
                    Toast.makeText(QueryActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });
        mTxt_Detail_Query.setMovementMethod(ScrollingMovementMethod.getInstance());

    }

    /**
     * 根据学校名查询数据
     */
    private void queryData(){
        BmobQuery<SchoolPushInfo> bmobQuery=new BmobQuery<SchoolPushInfo>();
        bmobQuery.addWhereContains("schoolName",mEdt_School_Query.getText().toString());
        bmobQuery.findObjects(this, new FindListener<SchoolPushInfo>() {
            @Override
            public void onSuccess(List<SchoolPushInfo> list) {
                Toast.makeText(QueryActivity.this, "查询成功", Toast.LENGTH_LONG).show();
                StringBuffer str=new StringBuffer();
                for (SchoolPushInfo data:list){
                    str.append("学校："+data.getSchoolName()+"\n");
                    str.append("区域："+data.getArea()+"\n");
                    str.append("学生数："+data.getStudentCount()+"\n");
                    str.append("新生数："+data.getNewStudentCount()+"\n");
                    str.append("校长："+data.getMonsterName()+"---"+data.getMonsterPhone()+"\n");
                    str.append("管理："+data.getManagerName()+"---"+data.getManagerPhone()+"\n");
                    str.append("学生数："+data.getArea()+"\n");
                    str.append("进度："+data.getProgress()+"\n");
                    str.append("------------------------\n");
                }
                mTxt_Detail_Query.setText(str);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(QueryActivity.this, "查询失败", Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * 初始化控间
     */
    private void initView() {

        mEdt_School_Query= (EditText) findViewById(R.id.edt_school_query);
        mBtn_Submit_Query= (Button) findViewById(R.id.btn_submit_query);
        mTxt_Detail_Query= (TextView) findViewById(R.id.txt_detail_query);
    }

}
