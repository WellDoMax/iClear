package com.laowulao.noads;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.laowulao.noads.widget.IconView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    IconView ivBack;

    TextView tvTitle, tvRightBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ivBack = findViewById(R.id.iv_back);
        if(ivBack != null){
            ivBack.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        tvTitle = findViewById(R.id.tv_title);
        tvRightBtn = findViewById(R.id.tv_menu);
    }

    public abstract int getLayoutId();

    public void setTitle(String title){
        if(tvTitle != null){
            tvTitle.setText(title);
        }
    }

    public void setTtile(int resId){
        if(tvTitle != null){
            tvTitle.setText(resId);
        }
    }

    public void setRightBtnText(String text){
        if(tvRightBtn != null){
            tvRightBtn.setText(text);
        }
    }

    public void setRightBtnText(int resId){
        if(tvRightBtn != null){
            tvRightBtn.setText(resId);
        }
    }

    public void setRightBtnVisible(int visible){
        if(tvRightBtn != null){
            tvRightBtn.setVisibility(visible);
        }
    }

    public void setRightBtnListener(View.OnClickListener l){
        if(tvRightBtn != null) {
            tvRightBtn.setOnClickListener(l);
        }
    }

    public void setBackVisible(int visible){
        if(ivBack != null){
            ivBack.setVisibility(visible);
        }
    }
}
