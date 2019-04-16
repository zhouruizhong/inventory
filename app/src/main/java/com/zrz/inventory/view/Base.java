package com.zrz.inventory.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.Toast;
import com.rscja.deviceapi.RFIDWithUHF;
import com.rscja.utility.StringUtility;
import com.zrz.inventory.adapter.ViewPagerAdapter;
import com.zrz.inventory.fragment.BaseTabFragmentActivity;
import com.zrz.inventory.fragment.KeyDwonFragment;
import com.zrz.inventory.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/4/16 19:38
 */
public class Base extends Activity {

    private final int offscreenPage = 2;

    protected ActionBar mActionBar;

    protected NoScrollViewPager mViewPager;
    protected ViewPagerAdapter mViewPagerAdapter;


    protected List<KeyDwonFragment> lstFrg = new ArrayList<KeyDwonFragment>();
    protected List<String> lstTitles = new ArrayList<String>();

    public RFIDWithUHF mReader;
    private int index = 0;

    private DisplayMetrics metrics;
    private AlertDialog dialog;
    private long[] timeArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initUHF() {
            try {
                mReader = RFIDWithUHF.getInstance();
            } catch (Exception ex) {
                toastMessage(ex.getMessage());
                return;
            }
            if (mReader != null) {
                new InitTask().execute();
            }
    }

    protected void initViewPageData() {

    }

    protected void initViewPager() {
		/*mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), lstFrg, lstTitles);

		mViewPager = findViewById(R.id.pager);
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOffscreenPageLimit(offscreenPage);*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 139 || keyCode == 280) {
            if (event.getRepeatCount() == 0) {
                if (mViewPager != null) {
                    KeyDwonFragment sf = (KeyDwonFragment) mViewPagerAdapter.getItem(mViewPager.getCurrentItem());
                    sf.myOnKeyDwon();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void gotoActivity(Intent it) {
        startActivity(it);
    }

    public void gotoActivity(Class<? extends BaseTabFragmentActivity> clz) {
        Intent it = new Intent(this, clz);
        gotoActivity(it);
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toastMessage(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * �豸�ϵ��첽��
     *
     * @author liuruifeng
     */
    public class InitTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                Toast.makeText(Base.this, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    @Override
    protected void onDestroy() {
        if (mReader != null) {
            mReader.free();
        }
        super.onDestroy();
    }

    /**
     * @param str
     * @return
     */
    public boolean vailHexInput(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        // ���ȱ�����ż��
        if (str.length() % 2 == 0) {
            return StringUtility.isHexNumberRex(str);
        }
        return false;
    }

    public void getUHFVersion() {
        if (mReader != null) {
            String rfidVer = mReader.getHardwareType();
            //UIHelper.alert(this, R.string.action_uhf_ver, rfidVer, R.drawable.webtext);
        }
    }
}
