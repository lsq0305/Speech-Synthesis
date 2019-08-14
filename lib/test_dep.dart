import 'dart:async';

import 'package:flutter/services.dart';

class TestDep {
  static const MethodChannel _channel =
      const MethodChannel('test_dep');
//    String a = '';
  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> hello(String a) async{
    final String hello_native = await _channel.invokeMethod("getName",<String,dynamic>{"message":a});

    print(hello_native);

    return hello_native;
  }
}
