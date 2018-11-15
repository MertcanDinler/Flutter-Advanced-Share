package in.mertcan.advancedshare.shareintents;

import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.ShareCompat;
import android.net.Uri;
import java.util.Map;
import java.util.List;
import in.mertcan.advancedshare.FileHelper;

public abstract class Base {
    protected final Registrar registrar;
    protected Map params;
    protected String title = "Share";
    protected FileHelper fileHelper;
    protected ShareCompat.IntentBuilder intentBuilder;

    public Base(Registrar registrar) {
        this.registrar = registrar;
        this.intentBuilder = ShareCompat.IntentBuilder.from(this.registrar.activity());
    }
    public int share(Map params) {
        this.params = params;
        fileHelper = getFileHelper(params);
        if (checkKey("title")) {
            title = (String) params.get("title");
            intentBuilder.setChooserTitle(title);
        }
        if (checkKey("msg")) {
            intentBuilder.setText((String) params.get("msg"));
        }
        if (checkKey("subject")) {
            intentBuilder.setSubject((String) params.get("subject"));
        }
        if (checkKey("url")) {
            if (fileHelper.isFile()) {
                final String SHARED_PROVIDER_AUTHORITY = registrar.context().getPackageName() + ".adv_provider";
                final Uri uri = (Uri) fileHelper.getUri();
                intentBuilder.setType(fileHelper.getType());
                intentBuilder.setStream(uri);

                List<ResolveInfo> resInfoList = this.registrar.context().getPackageManager().queryIntentActivities(intentBuilder.getIntent(), PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    this.registrar.context().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
        }
        return 0;
    }
    protected void openChooser() {
        final Intent chooser = intentBuilder.createChooserIntent();
        if (registrar.activity() == null) {
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            registrar.context().startActivity(chooser);
        } else {
            registrar.activity().startActivity(chooser);
        }
    }
    protected void openSingleApplication(String packageName) {
        final Intent chooser = intentBuilder.createChooserIntent();
        chooser.setPackage(packageName);
        if (registrar.activity() == null) {
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            registrar.context().startActivity(chooser);
        } else {
            registrar.activity().startActivity(chooser);
        }
    }
    protected FileHelper getFileHelper(Map params) {
        String url = "";
        if (checkKey("url")) {
            url = (String) params.get("url");
        }
        if (checkKey("type")) {
            return new FileHelper(registrar, (String) url, (String) params.get("type"));
        } else {
            return new FileHelper(registrar, (String) url);
        }
    }
    public boolean checkKey(String key) {
        if (params != null && !params.isEmpty()) {
            return params.get(key) != null;
        }
        return false;
    }
}