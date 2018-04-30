import 'dart:async';

import 'package:flutter/services.dart';

class AdvancedShare {
  static const MethodChannel _channel =
      const MethodChannel('github.com/mrtcndnlr/advanced_share');

  /// Required String msg or String url
  /// Default title: "Share"
  static Future<void> generic({String msg, String url, String title, String subject, String type}) {
    if (msg == null && url == null) {
      throw ArgumentError("Required msg or url");
    }
    final Map<String, dynamic> params = <String, dynamic>{
      'msg': msg,
      'url': url,
      'title': title,
      'subject': subject,
      'type': type,
    };
    return _channel.invokeMethod('share', params);
  }
}
