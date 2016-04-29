package com.example.huhu.universityactivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.huhu.universityactivity.model.SchoolPushInfo;
import com.example.huhu.universityactivity.util.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdt_school_name;//学校名
    private EditText mEdt_student_name;//学生总数
    private EditText mEdt_student_name_new;//新生数
    private EditText mEdt_monster_name;//校长姓名
    private EditText mEdt_monster_phone;//校长电话
    private EditText mEdt_manager_name;//后勤姓名
    private EditText mEdt_manager_phone;//后勤电话
    private EditText mEdt_user_name;//使用人姓名

    private TextView mTxt_area;//省市区
    private TextView mTxt_progress;//完成进度

    private Button mBtn_submit;//提交

    private JSONObject mJsonData = null;

    private OptionsPickerView mPickerView;
    private OptionsPickerView mPickerView1;

    private String province;
    private String city;
    private String area;
    //省市区的集合
    ArrayList<ArrayList<ArrayList<String>>> a = new ArrayList<ArrayList<ArrayList<String>>>();
    ArrayList<ArrayList<String>> b = new ArrayList<ArrayList<String>>();
    ArrayList<String> c = new ArrayList<String>();
    //progress集合
    ArrayList<String> pro = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        getJsonData();
        parseJsonData();
        getProgressData();
        initView();
        areaPicker();
        progressPicker();
        mTxt_area.setOnClickListener(this);
        mTxt_progress.setOnClickListener(this);
        mBtn_submit.setOnClickListener(this);
    }

    /**
     * 省市区选择
     */
    private void areaPicker() {
        mPickerView = new OptionsPickerView(this);
        mPickerView.setPicker(c, b, a, true);
        mPickerView.setCyclic(false, false, false);
        mPickerView.setTitle("选择城市");
        //设置默认的三个级别的选中位置
        //监听确定选择按钮
        mPickerView.setSelectOptions(0, 0, 0);
        mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String str = c.get(options1) + b.get(options1).get(option2) +
                        a.get(options1).get(option2).get(options3);
                province = c.get(options1);
                city = b.get(options1).get(option2);
                area = a.get(options1).get(option2).get(options3);
                mTxt_area.setText(str);
            }
        });
    }

    private void progressPicker() {
        mPickerView1 = new OptionsPickerView(this);
        mPickerView1.setPicker(pro);
        mPickerView1.setCyclic(false);
        mPickerView1.setTitle("选择进度");
        mPickerView.setSelectOptions(0);
        mPickerView1.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String str = pro.get(options1);
                mTxt_progress.setText(str);
            }
        });
    }

    private void getProgressData() {
        pro.add("进入");
        pro.add("准入");
        pro.add("不反对");
        pro.add("反对");
        pro.add("其他");
    }

    private void initView() {
        mEdt_school_name = (EditText) this.findViewById(R.id.edt_school_name);
        mEdt_student_name = (EditText) this.findViewById(R.id.edt_student_num);
        mEdt_student_name_new = (EditText) this.findViewById(R.id.edt_student_num_new);
        mEdt_monster_name = (EditText) this.findViewById(R.id.edt_monster_name);
        mEdt_monster_phone = (EditText) this.findViewById(R.id.edt_monster_phone);
        mEdt_manager_name = (EditText) this.findViewById(R.id.edt_manager_name);
        mEdt_manager_phone = (EditText) this.findViewById(R.id.edt_manager_phone);
        mEdt_user_name= (EditText) this.findViewById(R.id.edt_user);

        mTxt_area = (TextView) this.findViewById(R.id.txt_address);
        mTxt_progress = (TextView) this.findViewById(R.id.txt_progress);

        mBtn_submit = (Button) this.findViewById(R.id.btn_submit);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_submit:
                if(NetUtil.checkNet(MainActivity.this)){
                    submitData();
                }else{
                    Toast.makeText(MainActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.txt_address:
                mPickerView.show();
                break;
            case R.id.txt_progress:
                mPickerView1.show();
                break;
        }
    }

    /**
     * 提交数据到服务器
     */
    private void submitData() {
        SchoolPushInfo info=new SchoolPushInfo();
        info.setProvince(province);
        info.setCity(city);
        info.setArea(area);
        info.setSchoolName(mEdt_school_name.getText().toString());
        info.setStudentCount(Integer.parseInt(mEdt_student_name.getText().toString()));
        info.setNewStudentCount(Integer.parseInt(mEdt_student_name_new.getText().toString()));
        info.setMonsterName(mEdt_monster_name.getText().toString());
        info.setMonsterPhone(mEdt_monster_phone.getText().toString());
        info.setManagerName(mEdt_manager_name.getText().toString());
        info.setManagerPhone(mEdt_manager_phone.getText().toString());
        info.setUserName(mEdt_user_name.getText().toString());
        info.setProgress(mTxt_progress.getText().toString());
        info.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"提交成功",Toast.LENGTH_LONG).show();
                initViewData();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this,"提交失败，请检查内容",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViewData() {
        mEdt_school_name.setText("");
        mEdt_student_name_new.setText("");
        mEdt_student_name.setText("");
        mEdt_manager_name.setText("");
        mEdt_manager_phone.setText("");
        mEdt_monster_name.setText("");
        mEdt_monster_phone.setText("");
        mEdt_user_name.setText("");
        mTxt_progress.setText("进度情况");
        mTxt_area.setText("选择省市区");

    }

    /**
     * 获取本地Json数据
     */
    private void getJsonData() {

        try {
            StringBuffer sbf = new StringBuffer();
            InputStream is = this.getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[is.available()];
            while ((len = is.read(buf)) != -1) {
                sbf.append(new String(buf, 0, len, "gbk"));
            }
            is.close();
            mJsonData = new JSONObject(sbf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析Json数据
     */
    private void parseJsonData() {

        try {

            ArrayList<String> subArea = null;
            ArrayList<String> subCity = null;

            JSONArray jsonArray = mJsonData.getJSONArray("citylist");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("p");// 省名字

                c.add(province);
                subCity = new ArrayList<String>();
                ArrayList<ArrayList<String>> subArea02 = new ArrayList<ArrayList<String>>();

                JSONArray jsonCs = null;
                jsonCs = jsonP.getJSONArray("c");

                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");// 市名字

                    subCity.add(city);
                    subArea = new ArrayList<String>();

                    JSONArray jsonAreas = null;
                    jsonAreas = jsonCity.getJSONArray("a");

                    for (int k = 0; k < jsonAreas.length(); k++) {

                        String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
                        subArea.add(area);

                    }
                    subArea02.add(subArea);
                }
                a.add(subArea02);
                b.add(subCity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonData = null;
    }
}
