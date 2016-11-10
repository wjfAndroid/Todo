package com.wjft.odo.view.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wjft.odo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DesignMenuActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.bt_cil1)
    Button btCil1;
    @Bind(R.id.bt_cil2)
    Button btCil2;
    @Bind(R.id.bt_cil3)
    Button btCil3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_menu);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btCil1.setOnClickListener(this);
        btCil2.setOnClickListener(this);
        btCil3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_cil1:
                startActivity(CoordinatorLayout1Activity.class);
                break;
            case R.id.bt_cil2:
                startActivity(CoordinatorLayout2Activity.class);
                break;
            case R.id.bt_cil3:
                startActivity(CoordinatorLayout3Activity.class);
                break;
        }

    }

    public void startActivity(Class c) {
        startActivity(new Intent(this, c));
    }
}
