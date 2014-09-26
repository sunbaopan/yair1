
package com.ya.yair.activity;

import com.ya.yair.Constant;
import com.ya.yair.EairApplaction;
import com.ya.yair.R;
import com.ya.yair.util.WebServiceUtil;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {

    private CheckBox remeberBox;

    private SharedPreferences storeSP;

    private EditText userNameE;

    private EditText passwordE;

    private TextView registerT;

    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EairApplaction.getInstance().addActivity(this);
        setContentView(R.layout.login_layout);
        remeberBox = (CheckBox) this.findViewById(R.id.remeber_password);
        userNameE = (EditText) this.findViewById(R.id.user_name);
        passwordE = (EditText) this.findViewById(R.id.password);
        btn_login = (Button) this.findViewById(R.id.btn_login);
        // ע�ᰴť
        registerT = (TextView) this.findViewById(R.id.register);
        storeSP = this.getSharedPreferences(Constant.STOREDB, 0);
        String isMemory = storeSP.getString("isMemory", "NO");
        // �������ʱ�����if�����ж�SharedPreferences����name��password��û�����ݣ��еĻ���ֱ�Ӵ���EditText����
        if (isMemory.equals("YES")) {
            String name = storeSP.getString("name", "");
            String password = storeSP.getString("password", "");
            userNameE.setText(name);
            passwordE.setText(password);
            remeberBox.setChecked(true);
        }

        registerT.setOnClickListener(this);

        btn_login.setOnClickListener(this);

        remeberBox.setOnCheckedChangeListener(new RemeberBoxCheckedChangeListener());

    }

    private class RemeberBoxCheckedChangeListener implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            if (remeberBox.isChecked()) {
                Editor edit = storeSP.edit();
                edit.putString("name", userNameE.getText().toString());
                edit.putString("password", passwordE.getText().toString());
                edit.putString("isMemory", "YES");
                edit.commit();
            } else if (!remeberBox.isChecked()) {
                Editor edit = storeSP.edit();
                edit.putString("isMemory", "NO");
                edit.commit();
            }

        }

    }

    @Override
    public void onClick(View v) {
        if (v == registerT) {
            Intent intent = new Intent(this, RegisterActivity.class);
            this.startActivity(intent);
            this.finish();
        } else if (v == btn_login) {
            String username = userNameE.getText().toString();
            String password = passwordE.getText().toString();
            if (username == null || "".equals(username)) {
                Toast.makeText(this, this.getResources().getString(R.string.notusername),
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (password == null || "".equals(password)) {
                Toast.makeText(this, this.getResources().getString(R.string.notpassword),
                        Toast.LENGTH_LONG).show();
                return;
            }
            WebServiceUtil wsu = new WebServiceUtil();
            String userId = wsu.login(username, password);
            if (userId == null) {// ���Է���2�Σ�ȷ����½�ɹ���
                try {
                    Thread.sleep(Constant.SLEEPTIME);
                    userId = wsu.login(username, password);
                    if (userId == null) {
                        Thread.sleep(Constant.SLEEPTIME);
                        userId = wsu.login(username, password);
                    }
                } catch (InterruptedException e) {
                   
                    e.printStackTrace();
                }
            }
            if (userId == null) {
                Toast.makeText(this, this.getResources().getString(R.string.loginfail),
                        Toast.LENGTH_LONG).show();
                return;
            }
            if ("0".equals(userId)) {// ��½ʧ��
                Toast.makeText(this, this.getResources().getString(R.string.notUser),
                        Toast.LENGTH_LONG).show();
                return;
            } else {// ��½�ɹ�
                String ss = wsu.querySbByUserId(userId);
                if (ss == null || "0".equals(ss)) {// û���豸������ҳ��ȥ����Ҫ���û���userId����ȥ�Ա���������
                    Intent intent = new Intent(this, ConfigActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", userId);
                    intent.putExtra("IDbundle", bundle);
                    this.finish();
                    this.startActivity(intent);
                } else {// ���б�ҳ��
                    Intent intent = new Intent(this, DeviceListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("sb", ss);
                    bundle.putString("userId", userId);
                    intent.putExtra("sbbundle", bundle);
                    this.finish();
                    this.startActivity(intent);
                }
            }

        }

    }

}
