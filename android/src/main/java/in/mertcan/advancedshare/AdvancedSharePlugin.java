package in.mertcan.advancedshare;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import java.util.Map;

import in.mertcan.advancedshare.shareintents.Generic;

/**
 * AdvancedSharePlugin
 */
public class AdvancedSharePlugin implements MethodCallHandler {
  private static final String CHANNEL = "github.com/mrtcndnlr/advanced_share";
  private final Registrar _registrar;

  private AdvancedSharePlugin(Registrar registrar) {
    _registrar = registrar;
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
    AdvancedSharePlugin instance = new AdvancedSharePlugin(registrar);
    channel.setMethodCallHandler(instance);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("share")) {
      if (!(call.arguments instanceof Map)) {
        throw new IllegalArgumentException();
      }

      Generic share = new Generic(_registrar);
      share.share((Map) call.arguments);
    }
  }
}
