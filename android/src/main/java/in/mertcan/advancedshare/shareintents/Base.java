package in.mertcan.advancedshare.shareintents;

import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.content.Intent;

import java.util.Map;

import in.mertcan.advancedshare.FileHelper;

public abstract class Base {
    protected final Registrar registrar;
    protected Map params;
    protected String title = "Share";
    protected FileHelper fileHelper;
    protected Intent intent;

    public Base(Registrar registrar) {
        this.registrar = registrar;
    }

    public int share(Map params) {
        this.intent = new Intent();
        this.intent.setAction(Intent.ACTION_SEND);
        this.intent.setType("text/plain");

        this.params = params;
        fileHelper = getFileHelper(params);

        if (checkKey("title")) {
            title = (String) params.get("title");
        }

        if (checkKey("msg")) {
            intent.putExtra(Intent.EXTRA_TEXT, (String) params.get("msg"));
        }

        if (checkKey("subject")) {
            intent.putExtra(Intent.EXTRA_SUBJECT, (String) params.get("subject"));
        }

        if (checkKey("url")) {
            if (fileHelper.isFile()) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_STREAM, fileHelper.getUri());
                intent.setType(fileHelper.getType());
            }
        }
        return 0;
    }

    protected void openChooser() {
        Intent chooser = Intent.createChooser(intent, title);
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