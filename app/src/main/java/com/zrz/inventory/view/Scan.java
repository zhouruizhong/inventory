package com.zrz.inventory.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.zrz.inventory.R;
import com.zrz.inventory.adapter.ViewPagerAdapter;
import com.zrz.inventory.bean.*;
import com.zrz.inventory.fragment.BaseTabFragmentActivity;
import com.zrz.inventory.fragment.KeyDwonFragment;
import com.zrz.inventory.presenter.ReceiptsDetailPresenter;
import com.zrz.inventory.presenter.UploadPresenter;
import com.zrz.inventory.tools.MD5Util;
import com.zrz.inventory.tools.StringUtils;
import com.zrz.inventory.tools.UIHelper;
import com.zrz.inventory.view.viewinter.UploadView;
import com.zrz.inventory.view.viewinter.ViewReceipts;
import com.zrz.inventory.widget.LoadListView;

import java.lang.reflect.Array;
import java.util.*;

public class Scan extends BaseTabFragmentActivity implements ViewReceipts, UploadView {

    private boolean loopFlag = false;
    private int inventoryFlag = 1;

    private ArrayList<HashMap<String, String>> tagList;
    private SimpleAdapter adapter;
    private TextView code;
    private TextView count;
    private TextView matched;
    private RadioGroup RgInventory;

    private Button scan;
    private Button upload;
    private ListView LvTags;
    private Scan mContext = this;
    private HashMap<String, String> map;
    private ReceiptsDetail receiptsDetail;
    private EditText editText;
    private ImageView back;
    private ListView listView;

    private List<ReceiptsDetail> receiptsDetails = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private ReceiptsDetailPresenter presenter = new ReceiptsDetailPresenter(this, Scan.this);
    private UploadPresenter uploadPresenter = new UploadPresenter(this, Scan.this);
    private Integer receiptsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);

        // 初始化页面
        initView();
        initSound();
        initUHF();
        initData();
        initViewPager();
        //initViewPageData();

        //事件
        event();
        // 程序崩溃时触发线程  以下用来捕获程序崩溃异常
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
    }

    /**
     * 创建服务用于捕获崩溃异常
     */
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            restartApp();//发生崩溃异常时,重启应用
        }
    };

    public void restartApp(){
        Intent intent = new Intent(this,Scan.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Receipts receipts = (Receipts) bundle.getSerializable("receipts");
        receiptsId = receipts.getId();
        code.setText(receipts.getNumber());
        count.setText("0");
        matched.setText(receipts.getMatched());

        //receiptsDetails.add(new ReceiptsDetail());
        //presenter.find(receiptsId, currentPage, pageSize);
    }

    @Override
    protected void initViewPageData() {

    }

    @Override
    protected void initViewPager() {
        //设置ListView的适配器
        viewPagerAdapter = new ViewPagerAdapter(this, receiptsDetails);
        listView.setAdapter(viewPagerAdapter);
        listView.setSelection(1);
        listView.setEmptyView(findViewById(R.id.no_data));
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
        upload = findViewById(R.id.upload);
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

        upload.setOnClickListener(new UploadClickListener());
    }

    @Override
    public void successHint(Map<String, Object> response, String tag) {
        if (tag.equals("find")) {
            List<ReceiptsDetail> receiptsDetailList = (List<ReceiptsDetail>) response.get("receiptsDetailList");
            if (receiptsDetailList.size() > 0) {
                receiptsDetails.addAll(receiptsDetailList);

                count.setText(receiptsDetailList.size() + "");
                viewPagerAdapter.notifyDataSetChanged();
            } else {
                if (currentPage > 1) {
                    Toast.makeText(this, "已经到底线了", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (tag.equals("refresh")) {
            List<ReceiptsDetail> receiptsDetailList = (List<ReceiptsDetail>) response.get("receiptsDetailList");
            if (receiptsDetailList.size() > 0) {
                receiptsDetails.addAll(receiptsDetailList);
                count.setText(receiptsDetailList.size() + "");

                receiptsDetails.clear();
                receiptsDetails.addAll(receiptsDetailList);
                viewPagerAdapter.notifyDataSetChanged();
            }
        }

        if (tag.equals("scan")) {
            Toast.makeText(this, (String) response.get("message"), Toast.LENGTH_SHORT).show();
            if (receiptsDetails.size() >= pageSize) {
                currentPage++;
            }
            presenter.refresh(receiptsId, currentPage, pageSize);
        }
    }

    @Override
    public void failHint(Map<String, Object> response, String tag) {
        if (tag.equals("find")) {
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(ResponseObject object) {

    }

    @Override
    public void onError(String result) {

    }

    public class UploadClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (tagList.size() > 0) {

                uploadPresenter.onCreate();
                uploadPresenter.attachView(uploadView);

                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                //(key,若无数据需要赋的值)
                String keyName = sharedPreferences.getString("keyName", null);
                String secretName = sharedPreferences.getString("secretName", null);
                String token = sharedPreferences.getString("token", null);
                String loginUuid = sharedPreferences.getString("loginUuid", null);

                StringBuilder stringBuilder = new StringBuilder();
                for (Map<String, String> map : tagList) {
                    String tagUii = map.get("tagUii");
                    stringBuilder.append(tagUii);
                    stringBuilder.append(",");
                }
                Map<String, Object> map = new HashMap<>(16);
                map.put("timestamp", System.currentTimeMillis());
                map.put("rfidData", stringBuilder.substring(0, stringBuilder.length() - 1));
                map.put("sign", createSign(map));
                map.put("jhKey", keyName);
                uploadPresenter.rfidAdd(token, map);
            }else {
                Toast.makeText(Scan.this, "请先扫描标签", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 参数有：timestamp，jhSecret（登陆时返回的secretName），rfidData
     * 签名说明：1.将参数名按升序形式排序，
     * 2.对参数拼接成 XXX=XXX&XXX=XXXX 字符串的形式，
     * 3.用MD5进行加密形成sign签名数据，最终将字符串转换为大写
     *
     * @return
     */
    public String createSign(Map<String, Object> map) {
        Object[] keyArr = sortMap(map);
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < keyArr.length; i++) {
            temp.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        String sign = temp.substring(0, temp.length() - 1);
        sign = MD5Util.MD5(sign);
        return sign.toUpperCase();
    }

    public Object[] sortMap(Map<String, Object> map) {
        Set<String> keysSet = map.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        return keys;
    }

    private UploadView uploadView = new UploadView() {
        @Override
        public void onSuccess(final ResponseObject object) {
            String code = object.getCode();
            if ("200".equals(code)) {
                Toast.makeText(Scan.this, object.getMessage(), Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //返回成功状态信息
                        Map<String, Object> response = new HashMap<>(16);
                        List<Response> responses = object.getList();
                        load(responses);
                        viewPagerAdapter.notifyDataSetChanged();
                    }
                }, 100);
            } else {
                Toast.makeText(Scan.this, object.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(String result) {
            Toast.makeText(Scan.this, result, Toast.LENGTH_LONG).show();
        }
    };

    public void load(List<Response> list) {
        List<ReceiptsDetail> receiptsDetailList = new ArrayList<>(10);
        for (ReceiptsDetail receiptsDetail : receiptsDetails) {
            String item4 = receiptsDetail.getItem4();
            for (Response response : list) {
                String code = response.getCode();
                String rfidData = response.getRfid_data();
                if ("200".equals(code) && rfidData.equals(item4)) {
                    receiptsDetail.setItem1(response.getPacket_num());
                    receiptsDetail.setItem2(response.getNew_area());
                    receiptsDetail.setItem3(response.getMatch_state());
                    receiptsDetailList.add(receiptsDetail);
                }
            }
        }
        if (receiptsDetailList.size() > 0){
            for (ReceiptsDetail receiptsDetail : receiptsDetailList){
                presenter.add(receiptsDetail);
            }
        }
    }

    public class ScanClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            readTag();
            /*ReceiptsDetail receiptsDetail = new ReceiptsDetail();
            receiptsDetail.setReceiptsId(receiptsId);
            receiptsDetail.setItem1("item1" + (int) (Math.random() * 10 + 1));
            receiptsDetail.setItem2("item2" + (int) (Math.random() * 10 + 2));
            receiptsDetail.setItem3("item3" + (int) (Math.random() * 10 + 3));
            receiptsDetail.setItem4(""+System.currentTimeMillis() +""+ (Math.random() * 1000));
            presenter.add(receiptsDetail);*/
        }
    }

    private void readTag() {
        // 识别标签
        if (scan.getText().equals(this.getString(R.string.scan))) {
            scan.setText("扫描中....");
            switch (inventoryFlag) {
                // 单步
                case 0: {
                    String strUII = this.mReader.inventorySingleTag();
                    if (!TextUtils.isEmpty(strUII)) {
                        String strEPC = this.mReader.convertUiiToEPC(strUII);
                        addEPCToList(strEPC, "N/A");
                        count.setText("" + viewPagerAdapter.getCount());
                    } else {
                        scan.setText(this.getString(R.string.scan));
                        UIHelper.toastMessage(this, R.string.uhf_msg_inventory_fail);
                    }
                }
                break;
                // 单标签循环
                case 1: {
                    if (this.mReader.startInventoryTag((byte) 0, (byte) 0)) {
                        scan.setText(this.getString(R.string.title_stop_scan));
                        loopFlag = true;
                        setViewEnabled(false);
                        new TagThread().start();
                    } else {
                        mContext.mReader.stopInventory();
                        scan.setText(this.getString(R.string.scan));
                        UIHelper.toastMessage(mContext, R.string.uhf_msg_inventory_open_fail);
                    }
                }
                break;
                default:
                    break;
            }
            //scan.setText(this.getString(R.string.scan));
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

            receiptsDetail = new ReceiptsDetail();
            // 包号
            receiptsDetail.setItem1("");
            // 面积
            receiptsDetail.setItem2("");
            // 状态
            receiptsDetail.setItem3("");
            // rfid
            receiptsDetail.setItem4(epc);

            if (index == -1) {
                receiptsDetails.add(receiptsDetail);
                tagList.add(map);
                listView.setAdapter(viewPagerAdapter);
                count.setText("" + viewPagerAdapter.getCount());
            } else {
                int tagcount = Integer.parseInt(tagList.get(index).get("tagCount"), 10) + 1;
                map.put("tagCount", String.valueOf(tagcount));
                tagList.set(index, map);
            }
            viewPagerAdapter.notifyDataSetChanged();
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
                scan.setText(this.getString(R.string.scan));
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
                        //strResult = "TID:" + strTid + "\n";
                        strResult = strTid;
                    } else {
                        strResult = "";
                    }
                    Message msg = handler.obtainMessage();
                    //msg.obj = strResult + "EPC:" + mContext.mReader.convertUiiToEPC(res[1]) + "@" + res[2];
                    msg.obj = strResult + mContext.mReader.convertUiiToEPC(res[1]) + "@" + res[2];
                    handler.sendMessage(msg);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 139 ||keyCode == 280) {
            if (event.getRepeatCount() == 0) {
                readTag();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 139 ||keyCode == 280) {
            if (event.getRepeatCount() == 0) {
                stopInventory();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
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
