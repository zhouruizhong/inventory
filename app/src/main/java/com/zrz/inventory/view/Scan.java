package com.zrz.inventory.view;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.zrz.inventory.R;
import com.zrz.inventory.adapter.ViewPagerAdapter;
import com.zrz.inventory.bean.BarCode;
import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.ReceiptsDetail;
import com.zrz.inventory.fragment.BaseTabFragmentActivity;
import com.zrz.inventory.presenter.ReceiptsDetailPresenter;
import com.zrz.inventory.presenter.ReceiptsPresenter;
import com.zrz.inventory.tools.StringUtils;
import com.zrz.inventory.tools.UIHelper;
import com.zrz.inventory.view.viewinter.ViewReceipts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scan extends BaseTabFragmentActivity implements ViewReceipts {

    private boolean loopFlag = false;
    private int inventoryFlag = 0;

    private ArrayList<HashMap<String, String>> tagList;
    SimpleAdapter adapter;
    TextView code;
    TextView count;
    TextView matched;
    RadioGroup RgInventory;

    Button scan;
    ListView LvTags;
    private Scan mContext = this;
    private HashMap<String, String> map;
    private EditText editText;
    private ImageView back;
    private ListView listView;

    private List<ReceiptsDetail> receiptsDetails = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private Integer currentPage = 1;
    private Integer pageSize = 5;
    private ReceiptsDetailPresenter presenter;
    private Integer receiptsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);

        presenter = new ReceiptsDetailPresenter(this, Scan.this);

        // 初始化页面
        initView();
        initSound();
        //initUHF();
        initData();
        initViewPager();
        initViewPageData();

        //事件
        event();

    }

    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        LoginResp loginResp = (LoginResp) bundle.getSerializable("login");
        Receipts receipts = (Receipts) bundle.getSerializable("receipts");
        receiptsId = receipts.getId();
        //Toast.makeText(Scan.this, loginResp.getMessage(), Toast.LENGTH_LONG).show();
        presenter.find(receiptsId, currentPage, pageSize);

        code.setText(receipts.getNumber());
        count.setText(receipts.getCount());
        matched.setText(receipts.getMatched());
    }

    @Override
    protected void initViewPageData() {
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initViewPager() {

        //设置ListView的适配器
        viewPagerAdapter = new ViewPagerAdapter(this, receiptsDetails);
        listView.setAdapter(viewPagerAdapter);
        listView.setSelection(1);
    }

    private HashMap<Integer, Integer> soundMap = new HashMap<>(16);
    private SoundPool soundPool;
    private float volumnRatio;
    private AudioManager am;

    private void initSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(this, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(this, R.raw.serror, 1));
        // 实例化AudioManager对象
        am = (AudioManager) this.getSystemService(AUDIO_SERVICE);
    }

    /**
     * 播放提示音
     *
     * @param id 成功1，失败2
     */
    public void playSound(int id) {
        // 返回当前AudioManager对象的最大音量值
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 返回当前AudioManager对象的音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            // p2、左声道音量
            // p3、右声道音量
            // p4、优先级，0为最低
            // p5、循环次数，0无不循环，-1无永远循环
            // p6、回放速度 ，该值在0.5-2.0之间，1为正常速度
            soundPool.play(soundMap.get(id), volumnRatio, volumnRatio, 1, 0, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        editText = findViewById(R.id.result);
        back = findViewById(R.id.inventory_back);

        code = findViewById(R.id.code_num);
        count = findViewById(R.id.count_num);
        matched = findViewById(R.id.matched_num);

        scan = findViewById(R.id.scan);
        listView = findViewById(R.id.list_item);
        tagList = new ArrayList<>(10);

    }

    private void event() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scan.setOnClickListener(new ScanClickListener());
    }

    @Override
    public void successHint(Map<String, Object> response, String tag) {
        if (tag.equals("find")) {
            receiptsDetails.clear();
            receiptsDetails.addAll((List<ReceiptsDetail>) response.get("receiptsDetailList"));
            viewPagerAdapter.notifyDataSetChanged();
        }
        if (tag.equals("scan")){
            Toast.makeText(this, (String)response.get("message"), Toast.LENGTH_SHORT).show();
            presenter.find(receiptsId, currentPage, pageSize);
        }
    }

    @Override
    public void failHint(Map<String, Object> response, String tag) {
        if (tag.equals("find")) {
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    public class ScanClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //readTag();
            ReceiptsDetail receiptsDetail = new ReceiptsDetail();
            receiptsDetail.setReceiptsId(receiptsId);
            receiptsDetail.setItem1("item1" + (int) (Math.random() * 10 + 1));
            receiptsDetail.setItem2("item2" + (int) (Math.random() * 10 + 2));
            receiptsDetail.setItem3("item3" + (int) (Math.random() * 10 + 3));
            receiptsDetail.setItem4("item4" + (int) (Math.random() * 10 + 4));
            presenter.add(receiptsDetail);
        }
    }

    private void readTag() {
        // 识别标签
        if (scan.getText().equals(this.getString(R.string.scan))) {
            switch (inventoryFlag) {
                // 单步
                case 0: {
                    String strUII = this.mReader.inventorySingleTag();
                    if (!TextUtils.isEmpty(strUII)) {
                        String strEPC = this.mReader.convertUiiToEPC(strUII);
                        addEPCToList(strEPC, "N/A");
                        count.setText("" + adapter.getCount());
                    } else {
                        UIHelper.toastMessage(this, R.string.uhf_msg_inventory_fail);
                    }
                }
                break;
                // 单标签循环
                case 1: {
                    if (this.mReader.startInventoryTag((byte) 0, (byte) 0)) {
                        scan.setText(this.getString(R.string.title_stop_Inventory));
                        loopFlag = true;
                        setViewEnabled(false);
                        new TagThread().start();
                    } else {
                        mContext.mReader.stopInventory();
                        UIHelper.toastMessage(mContext, R.string.uhf_msg_inventory_open_fail);
                    }
                }
                break;
                default:
                    break;
            }
        } else {// 停止识别
            stopInventory();
        }
    }

    @Override
    public void onPause() {
        Log.i("MY", "UHFReadTagFragment.onPause");
        super.onPause();
        // 停止识别
        stopInventory();
    }

    /**
     * 添加EPC到列表中
     *
     * @param epc
     */
    private void addEPCToList(String epc, String rssi) {
        if (!TextUtils.isEmpty(epc)) {
            int index = checkIsExist(epc);

            map = new HashMap<String, String>();
            map.put("tagUii", epc);
            map.put("tagCount", String.valueOf(1));
            map.put("tagRssi", rssi);

            if (index == -1) {
                tagList.add(map);
                LvTags.setAdapter(adapter);
                count.setText("" + adapter.getCount());
            } else {
                int tagcount = Integer.parseInt(tagList.get(index).get("tagCount"), 10) + 1;
                map.put("tagCount", String.valueOf(tagcount));
                tagList.set(index, map);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void setViewEnabled(boolean enabled) {
        //btnFilter.setEnabled(enabled);
        //BtClear.setEnabled(enabled);
    }

    /**
     * 停止识别
     */
    private void stopInventory() {
        if (loopFlag) {
            loopFlag = false;
            setViewEnabled(true);
            if (this.mReader.stopInventory()) {
                scan.setText(this.getString(R.string.btInventory));
            } else {
                UIHelper.toastMessage(this, R.string.uhf_msg_inventory_stop_fail);
            }

        }
    }

    /**
     * 判断EPC是否在列表中
     *
     * @param strEPC 索引
     * @return
     */
    public int checkIsExist(String strEPC) {
        int existFlag = -1;
        if (StringUtils.isEmpty(strEPC)) {
            return existFlag;
        }
        String tempStr = "";
        for (int i = 0; i < tagList.size(); i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp = tagList.get(i);
            tempStr = temp.get("tagUii");
            if (strEPC.equals(tempStr)) {
                existFlag = i;
                break;
            }
        }
        return existFlag;
    }

    class TagThread extends Thread {
        @Override
        public void run() {
            String strTid;
            String strResult;
            String[] res = null;
            while (loopFlag) {
                res = mContext.mReader.readTagFromBuffer();
                if (res != null) {
                    strTid = res[0];
                    if (!strTid.equals("0000000000000000") && !strTid.equals("000000000000000000000000")) {
                        strResult = "TID:" + strTid + "\n";
                    } else {
                        strResult = "";
                    }
                    Message msg = handler.obtainMessage();
                    msg.obj = strResult + "EPC:" + mContext.mReader.convertUiiToEPC(res[1]) + "@" + res[2];
                    handler.sendMessage(msg);
                }
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            String[] strs = result.split("@");
            addEPCToList(strs[0], strs[1]);
            mContext.playSound(1);
        }
    };
}
