package in.mertcan.advancedshare.shareintents;

import in.mertcan.advancedshare.shareintents.Base;

import io.flutter.plugin.common.PluginRegistry.Registrar;

import java.util.Map;

import android.content.pm.PackageManager;
import android.content.Context;

public abstract class SingleBase extends Base {
    public SingleBase(Registrar registrar) {
        super(registrar);
    }

    @Override
    public int share(Map params) {
        super.share(params);
        if (getPackage() != null) {
            if (isPackageInstalled(getPackage(), registrar.context())) {
                this.intent.setPackage(getPackage());
                openChooser();
                return 1;
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }

    boolean isPackageInstalled(String packagename, Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    abstract String getPackage();

}