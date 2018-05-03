package in.mertcan.advancedshare.shareintents;

import in.mertcan.advancedshare.shareintents.Base;

import io.flutter.plugin.common.PluginRegistry.Registrar;

import java.util.Map;

public class Generic extends Base {
    public Generic(Registrar registrar) {
        super(registrar);
    }

    @Override
    public int share(Map params) {
        try {
            super.share(params);
            openChooser();
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

}