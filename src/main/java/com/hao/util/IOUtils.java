package com.hao.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
    public static void copyFully(InputStream is, OutputStream os) {
        byte[] buf = new byte[1024];
        int len;
        try {
            while ((len = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
