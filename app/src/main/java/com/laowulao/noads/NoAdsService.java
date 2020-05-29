package com.laowulao.noads;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.laowulao.noads.db.RecordDao;
import com.laowulao.noads.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class NoAdsService extends AccessibilityService {

    private static final String TAG = "NoAdsService";


    private static final String CLS_NAME = "android.widget.Button";
    private static final String CLS_TEXT_NAME = "android.widget.TextView";

    private static List<String> WHITE_LIST = new ArrayList<>();

    private static int DURATION = 10000;//屏蔽间隔
    private static long LastTime = 0;

    // 是否正在处理
    private static boolean isIntercepting = false;

    static {
        // init WHITE_LIST
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 服务已连接
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        ToastUtils.showToast(this,"广告屏蔽服务已开启");
    }

    // 接收事件
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo rootNode = this.getRootInActiveWindow();
        if(rootNode == null){
            return;
        }
        // 过滤掉桌面，白名单
        String pkgName = String.valueOf(rootNode.getPackageName());
        if(TextUtils.isEmpty(pkgName) || pkgName.indexOf("launcher") > -1){
            return;
        }
        // 系统进程
        if(pkgName.indexOf("system") > -1){
            return;
        }
        // 白名单
        if(WHITE_LIST.contains(pkgName)){
            return;
        }
        long d = System.currentTimeMillis() - LastTime;
        // 时间间隔在频率范围内不处理
        if(d < DURATION){
            return;
        }
        if(!isIntercepting){
            isIntercepting = true;
            AccessibilityNodeInfo node = findCurrentNode(rootNode);
            if(node != null){
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);// 点击跳过广告按钮
                LastTime = System.currentTimeMillis();
                RecordDao.saveOrUpdateByPkg(pkgName,this);
            }
        }
        isIntercepting = false;

    }

    // 递归查找包含跳过字样的Button/TextView
    private AccessibilityNodeInfo findCurrentNode(AccessibilityNodeInfo info){

        int count = info.getChildCount();
        if(count > 0){
            for(int i = 0; i < count; i++){
                AccessibilityNodeInfo subNode = info.getChild(i);
                if(subNode == null){
                    continue;
                }
                // 递归查找
                AccessibilityNodeInfo currentNode = findCurrentNode(subNode);
                if(currentNode != null){
                    return currentNode;
                }

            }
        } else {
            String text = String.valueOf(info.getText());
            if(CLS_NAME.equals(info.getClassName()) || CLS_TEXT_NAME.equals(info.getClassName())){
                // 包含跳过文本的才是目标node
                if(!TextUtils.isEmpty(text) && text.indexOf("跳过") > -1){
                    if (info.isClickable()) {
                        return info;
                    } else {
                        AccessibilityNodeInfo parent = info.getParent();
                        if (parent.isClickable()) {
                            return parent;
                        }
                    }
                }
            }
        }
        return null;
    }


    // 服务中断
    @Override
    public void onInterrupt() {
        //ToastUtils.showToast(this,"广告拦截服务已关闭");
    }
}
