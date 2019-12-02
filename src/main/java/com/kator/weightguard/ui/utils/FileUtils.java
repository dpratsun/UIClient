package com.kator.weightguard.ui.utils;

import com.kator.weightguard.ui.strings.Message;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by prats on 4/21/18.
 */
public class FileUtils {
    public static String copy(File source, File destination) {
        FileChannel in = null;
        FileChannel out = null;
        String message;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(destination).getChannel();

            long size = in.size();
            long transferred = in.transferTo(0, size, out);

            while(transferred != size){
                transferred += in.transferTo(transferred, size - transferred, out);
            }
            message = Message.FILE_IS_COPIED;
        } catch (Exception e) {
            message = e.getMessage();
        } finally {
            close(in);
            close(out);
        }
        return message;
    }

    private static void close(Closeable closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
