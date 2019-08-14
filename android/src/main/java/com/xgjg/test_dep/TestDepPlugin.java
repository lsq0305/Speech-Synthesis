package com.xgjg.test_dep;

import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** TestDepPlugin */
public class TestDepPlugin implements MethodCallHandler {

//    String s = "";
    private Registrar registrar;
    private Result result;

    private static String MOTION_SENSOR = "MOTION_SENSOR";

    private SpeechSynthesizer mTts;
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            //Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                //showTip("初始化失败,错误码："+code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    private TestDepPlugin(Registrar registrar) {


        this.registrar = registrar;
        SpeechUtility.createUtility(registrar.context(), SpeechConstant.APPID +"=5d4be7b6");


        mTts = SpeechSynthesizer.createSynthesizer(registrar.context(), mTtsInitListener);

        if (mTts == null){
            Log.d("11","mTts 初始化识别");
        }

    }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "test_dep");
    TestDepPlugin testDepPlugin = new TestDepPlugin(registrar);
    channel.setMethodCallHandler( testDepPlugin);

  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {

    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }else if(call.method.equals("getName")){
        String message = call.argument("message");
        TestName testName =new TestName(registrar.context());
      testName.pay(mTts,message);
      result.success("success");
    }

    else {
      result.notImplemented();
    }
  }
}
