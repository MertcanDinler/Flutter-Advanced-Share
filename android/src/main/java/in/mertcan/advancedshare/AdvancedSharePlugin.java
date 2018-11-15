package in.mertcan.advancedshare;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import java.util.Map;
import java.util.HashMap;

import in.mertcan.advancedshare.shareintents.Base;
import in.mertcan.advancedshare.shareintents.Generic;
import in.mertcan.advancedshare.shareintents.Gmail;
import in.mertcan.advancedshare.shareintents.Whatsapp;

/**
 * AdvancedSharePlugin
 */
public class AdvancedSharePlugin implements MethodCallHandler {
  private static final String CHANNEL = "github.com/mrtcndnlr/advanced_share";
  private final Registrar _registrar;
  private final HashMap<String, Base> options = new HashMap<String, Base>();

  private AdvancedSharePlugin(Registrar registrar) {
    _registrar = registrar;
    options.put("generic", new Generic(_registrar));
    options.put("gmail", new Gmail(_registrar));
    options.put("whatsapp", new Whatsapp(_registrar));
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
    AdvancedSharePlugin instance = new AdvancedSharePlugin(registrar);
    channel.setMethodCallHandler(instance);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    int res = 0;
    if (call.method.equals("share")) {
      if (!(call.arguments instanceof Map)) {
        throw new IllegalArgumentException();
      }
      Map args = (Map) call.arguments;
      try {
        if (call.argument("direct") == null) {
          res = options.get("generic").share(args);
        } else {
          String option = (String) call.argument("direct");
          res = options.get(option).share(args);
        }
        result.success(res);
      } catch (Exception e) {
        result.success(0);
      }
    }
  }
}
