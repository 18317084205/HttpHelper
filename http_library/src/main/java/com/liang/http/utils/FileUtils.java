package com.liang.http.utils;

import android.content.Context;
import android.os.Environment;

import java.io.*;

public class FileUtils {
    public static File getAlbumStorageFile(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.
                DIRECTORY_DOWNLOADS), fileName);//参数是文件名称
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File getAlbumStorageDir(String parent, String fileName) {
        File file = new File(getAlbumStorageFolder(parent), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File getAlbumStorageFolder(String parent) {
        File file = new File(parent);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String getStringFromAssets(Context context, String fileName) {
        InputStream is = getStreamFromAssets(context, fileName);
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            if (is != null) {
                inputReader = new InputStreamReader(is);
                bufReader = new BufferedReader(inputReader);
                String line;
                String Result = "";
                while ((line = bufReader.readLine()) != null)
                    Result += line;
                return Result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCloseable(bufReader);
            closeCloseable(inputReader);
            closeCloseable(is);
        }
        return null;
    }

    public static void closeCloseable(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static InputStream getStreamFromAssets(Context context, String fileName) {
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
