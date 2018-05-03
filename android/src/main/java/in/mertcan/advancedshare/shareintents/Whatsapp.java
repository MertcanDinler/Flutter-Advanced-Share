package in.mertcan.advancedshare.shareintents;

import in.mertcan.advancedshare.shareintents.SingleBase;

import io.flutter.plugin.common.PluginRegistry.Registrar;

import java.util.Map;

public class Whatsapp extends SingleBase {
    private final String packageName = "com.whatsapp";

    public Whatsapp(Registrar registrar) {
        super(registrar);
    }

    @Override
    protected String getPackage() {
        return packageName;
    }
}