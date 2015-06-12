package dian.org.monitor.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import dian.org.monitor.Constant;
import dian.org.monitor.MainActivity;
import dian.org.monitor.R;
import dian.org.monitor.util.PreferenceManager;


public class LoginAty extends Activity {
    private static final String TAG = "loginAty****";

    /**
     * 提交按钮
     */
    private Button btnSubmit;
    /**
     * 用户名输入框
     */
    private EditText etUserName;
    /**
     * 密码输入框
     */
    private EditText etPassWord;
    /**
     * 服务器SPinner
     */
    private Spinner spServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否已经登陆
        if (PreferenceManager.getLoginState(this)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.login_aty);

        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        //找到控件
        btnSubmit = (Button) findViewById(R.id.id_btn_submit);
        etUserName = (EditText) findViewById(R.id.id_et_user_name);
        etPassWord = (EditText) findViewById(R.id.id_et_pass_word);
        spServer = (Spinner) findViewById(R.id.id_sp_server);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserName.getText().toString().equals("") ||
                        etPassWord.getText().toString().equals("")) {
                    Toast.makeText(LoginAty.this, "用户名和密码不可为空", Toast.LENGTH_SHORT).show();
                } else {
                    //判断密码是否正确
                    if (isPassed()) {
                        Intent intent = new Intent(LoginAty.this, MainActivity.class);
                        finish();       //可以先自杀---再开启Aty???
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginAty.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 判断密码是否正确
     *
     * @return
     */
    private boolean isPassed() {
        String userName = etUserName.getText().toString();
        String passWord = etPassWord.getText().toString();
        //判断密码***用户名
        if(userName.equals(Constant.USER_NAME) && passWord.equals(Constant.PASS_WORD)){
            //将登陆状态保存
            PreferenceManager.changeLoginState(this, true);
            //将用户名和密码保存
            PreferenceManager.changeUserName(this, userName);
            PreferenceManager.changePassword(this, passWord);
            //返回登陆成功
            return true;
        }
        return false;
    }
}
