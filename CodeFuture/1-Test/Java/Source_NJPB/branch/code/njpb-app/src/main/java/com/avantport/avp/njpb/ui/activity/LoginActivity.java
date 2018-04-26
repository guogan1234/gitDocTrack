package com.avantport.avp.njpb.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.Loginbean;
import com.avantport.avp.njpb.bean.UserInfobean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.SpUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;

import java.util.Map;

import okhttp3.Response;

import static com.avantport.avp.njpb.R.id.login;

public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private TextView mLoginVersion;
    private EditText mLoginName;
    private EditText mLoginPsw;
    private Button mLogin;
    private ImageView mLoginDelectName;
    private ImageView mLoginDelectPsw;
    private String mUsername;
    private String mUserpsw;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    protected void initView() {
        mLoginVersion = (TextView) findViewById(R.id.login_version);
        mLoginName = (EditText) findViewById(R.id.login_name);
        mLoginPsw = (EditText) findViewById(R.id.login_psw);
        mLogin = (Button) findViewById(login);
        mLoginDelectName = (ImageView) findViewById(R.id.login_delect_name);
        mLoginDelectPsw = (ImageView) findViewById(R.id.login_delect_psw);
    }

    @Override
    protected void initDatas() {
        mLoginDelectName.setOnClickListener(this);
        mLoginDelectPsw.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mLoginName.addTextChangedListener(this);
        mLoginPsw.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_delect_name:
                mLoginName.setText("");
                break;
            case R.id.login_delect_psw:
                mLoginPsw.setText("");
                break;
            case login:
                loginUser();
                break;

        }
    }

    private void loginUser() {//用户登录
        showProDialog(getString(R.string.logined));
        mUsername = mLoginName.getText().toString().trim();
        mUserpsw = mLoginPsw.getText().toString().trim();
        //进行网络请求登录
        params.clear();
        params.put("username", mUsername);
        params.put("password", mUserpsw);
        params.put("grant_type", "password");
        params.put("scope", "openid");

        getNetDatas(Constant.LOGIN, params);

    }

    private void getNetDatas(String url, Map<String, Object> param) {
        OkHttpUtils.getInstance().loginPost(url, param, new OkHttpBaseCallback<Loginbean>() {

            @Override
            public void onSuccess(Response response, Loginbean loginbean) {
                hideDialiog();
                String header = loginbean.getToken_type() + " " + loginbean.getAccess_token();
                SpUtil.saveValue("token", header);
                SpUtil.saveValue("userInfo", mUsername + "&" + mUserpsw);//登录成功后，保存用户名字用于回显

               //网络请求获取用户信息
                getUserInfo();
                //登录成功后进行用户信息查询，查询到用户信息，进行保存在其他地方回显
                // TODO: 2017/8/31
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                hideDialiog();
                if (code == 400) {
                    //提示密码错误，并把密码输入框清空
                    ToastUtil.show(LoginActivity.this, getString(R.string.error_password));
                    mLoginPsw.setText("");
                } else if (code == 401) {
                    ToastUtil.show(LoginActivity.this, getString(R.string.error_uesrname));
                    mLoginName.setText("");
                    mLoginPsw.setText("");
                }else{
                    ToastUtil.show(LoginActivity.this,"登录超时");
                }
            }
        });


    }

    private void getUserInfo() {
        //获取用户信息
        OkHttpUtils.getInstance().get(Constant.USERINFO, null, null, new OkHttpBaseCallback<UserInfobean>() {

            @Override
            public void onSuccess(Response response, UserInfobean userInfobean) {
                //将户信息序列化保存到本地
                SpUtil.saveObject("userInfobean",userInfobean);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));//登录成功后，保存数据然后跳转
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(LoginActivity.this,"获取用户信息失败！请再次登录！");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mLoginName.length() > 0) {
            mLoginDelectName.setVisibility(View.VISIBLE);
        } else {
            mLoginDelectName.setVisibility(View.INVISIBLE);
        }

        if (mLoginPsw.length() > 0) {
            mLoginDelectPsw.setVisibility(View.VISIBLE);
        } else {
            mLoginDelectPsw.setVisibility(View.INVISIBLE);
        }
        if (mLoginName.length() > 0 && mLoginPsw.length() > 0) {
            mLogin.setEnabled(true);
        } else {
            mLogin.setEnabled(false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //对登录的信息进行回显
        String userInfo = SpUtil.getValue("userInfo");
        if (userInfo.length() == 0) {
            mLoginName.setText("");
            mLoginPsw.setText("");
        } else {
            String[] split = userInfo.split("&");
            mLoginName.setText(split[0]);
           mLoginPsw.setText(split[1]);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
