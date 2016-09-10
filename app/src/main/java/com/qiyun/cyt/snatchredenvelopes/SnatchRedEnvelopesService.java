package com.qiyun.cyt.snatchredenvelopes;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

/**
 * Created by GuoChang on 2016/8/31.
 *
 * 自动抢红包服务
 */
public class SnatchRedEnvelopesService extends AccessibilityService {
    private boolean IS_SNATCHED = false;
    private long pre_click = 0;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            //第一步：监听通知栏消息
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        Log.i("微信消息", "内容:"+content);
                        if (content.contains("[微信红包]")) {
                            //模拟打开通知栏消息
                            if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    vibrate();
                                    Toast.makeText(SnatchRedEnvelopesService.this,"快拆红包~快快快~~",Toast.LENGTH_SHORT).show();
                                    pendingIntent.send();
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            //第二步：监听是否进入微信红包消息界面
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
//                    //开始抢红包
//                    Log.e("pre_enter","点击红包");
//                    getPacket();
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    //开始打开红包
                    Log.e("pre_open","開红包");
                    openPacket();
                }else if(className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")){
                    //红包详情页面
                    //“返回”按钮
//                    goBack();
                }
                break;


        }
    }
//
//    /**
//     * 每分钟执行一次
//     */
//    private void whileOneMin() {
//        if(SystemClock.currentThreadTimeMillis() - pre_click >= 1000 * 30){
//            getPacket();
//        }
//
//    }

    /**
     * 手机震动(需要权限)
     */
    public void vibrate(){
        Vibrator vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrate.vibrate(new long[]{0,50,20,50,20,50,20,50,20,50},-1);
//
//		 //等待一秒，震动两秒，再等待一秒，再震动三秒、参数：-1 表示不循环；可以填0（1、2），表示从数组第0（1、2）个位置循环执行
//		 vibrate.vibrate(new long[]{1000,2000,1000,3000}, -1);
//
//		 //停止震动
//		 vibrate.cancel();
    }

    /**
     * 红包详情页返回
     */
    @SuppressLint("NewApi")
    private void goBack() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycleDes(rootNode);
    }

    /**
     * 查找到红包，打开红包
     */
    @SuppressLint("NewApi")
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list_qun = nodeInfo
                    .findAccessibilityNodeInfosByText("发了一个红包，金额随机");
            List<AccessibilityNodeInfo> list_geren = nodeInfo
                    .findAccessibilityNodeInfosByText("给你发了一个红包");

            List<AccessibilityNodeInfo> list;
            if(list_qun.size() == 0){
                list = list_geren;
            }else {
                list = list_qun;
            }

            Log.e("发了一个红包，金额随机",list.size() + "");

            for (AccessibilityNodeInfo node:list) {
                //通过“发了一个红包”文字来找到“開”字按钮
                AccessibilityNodeInfo parent = node.getParent();
                if(parent != null){
                    Log.e("孩子数量",parent.getChildCount()+"");
                    //暴力点击
                    for(int j = 0;j<parent.getChildCount();j++){
                        AccessibilityNodeInfo child = parent.getChild(j);
                        if(child.isClickable()){
                            Log.e("暴力试探点击","位置：" + j);
                            child.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            Log.e("open","開红包");
                        }

                    }

                }
            }

        }

    }

    @SuppressLint("NewApi")
    private void getPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycle(rootNode);
    }

    /**
     * 打印一个节点的结构
     * @param info
     */
    @SuppressLint("NewApi")
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if(info.getText() != null){
                if(info.getText().toString().trim().contains("领取红包")){
                    //这里有一个问题需要注意，就是需要找到一个可以点击的View
                    Log.i("demo", "Click"+",isClick:"+info.isClickable());
                    if(info.isClickable()){
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }else{
                        AccessibilityNodeInfo parent = info.getParent();
                        while(parent != null){
                            Log.i("demo", "parent isClick:"+parent.isClickable());
                            if(parent.isClickable()){
                                Log.e("enter","点击进入红包");
                                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                break;
                            }
                            parent = parent.getParent();
                        }
                    }
                }
            }

        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    recycle(info.getChild(i));
                }
            }
        }
    }


    /**
     * 打印详情页一个节点的结构
     * @param info
     */
    @SuppressLint("NewApi")
    public void recycleDes(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if(info.getContentDescription().toString().trim() != null){
                if("返回".equals(info.getContentDescription().toString().trim())){
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }

        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    recycleDes(info.getChild(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
    }


}
