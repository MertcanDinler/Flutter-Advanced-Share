package in.mertcan.advancedshare;

import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {
    private final Registrar registrar;
    private final String authorities;
    private String url;
    private Uri uri;
    private String type;
    private String extension;

    public FileHelper(Registrar registrar, String url) {
        this.registrar = registrar;
        this.authorities = registrar.context().getPackageName() + ".adv_provider";
        this.url = url;
        this.uri = Uri.parse(this.url);
    }

    public FileHelper(Registrar registrar, String url, String type) {
        this.registrar = registrar;
        this.authorities = registrar.context().getPackageName() + ".adv_provider";
        this.url = url;
        this.uri = Uri.parse(url);
        this.type = type;
    }

    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public boolean isFile() {
        return isBase64File() || isLocalFile();
    }

    public boolean isBase64File() {
        String scheme = uri.getScheme();
        if (scheme != null && uri.getScheme().equals("data")) {
            type = uri.getSchemeSpecificPart().substring(0, uri.getSchemeSpecificPart().indexOf(";"));
            return true;
        }
        return false;
    }

    public boolean isLocalFile() {
        String scheme = uri.getScheme();
        if (scheme != null && (uri.getScheme().equals("content") || uri.getScheme().equals("file"))) {
            if (type != null) {
                return true;
            }
            type = getMimeType(uri.toString());
            if (type == null) {
                String realPath = getRealPath(uri);
                if (realPath == null) {
                    return false;
                } else {
                    type = getMimeType(realPath);
                }

                if (type == null) {
                    type = "*/*";
                }
            }
            return true;
        }
        return false;
    }

    public String getType() {
        if (type == null) {
            return "*/*";
        }
        return type;
    }

    private String getRealPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(registrar.context(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        String result = null;
        if (cursor != null && cursor.moveToFirst()) {
            int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(col_index);
            cursor.close();
        }
        return result;
    }

    public Uri getUri() {
        final MimeTypeMap mime = MimeTypeMap.getSingleton();
        extension = mime.getExtensionFromMimeType(getType());

        if (isBase64File()) {
            final String tempPath = registrar.context().getCacheDir().getPath();
            final String prefix = "" + System.currentTimeMillis() / 1000;
            String encodedFile = uri.getSchemeSpecificPart()
                    .substring(uri.getSchemeSpecificPart().indexOf(";base64,") + 8);
            try {
                File tempFile = new File(tempPath, prefix + "." + extension);
                final FileOutputStream stream = new FileOutputStream(tempFile);
                stream.write(Base64.decode(encodedFile, Base64.DEFAULT));
                stream.flush();
                stream.close();
                return FileProvider.getUriForFile(registrar.context(), authorities, tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isLocalFile()) {
            Uri uri = Uri.parse(this.url);
            Uri providerUri = FileProvider.getUriForFile(registrar.context(), authorities, new File(uri.getPath()));
            return providerUri;
        }
        return null;
    }
}