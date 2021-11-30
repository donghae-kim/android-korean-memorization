package org.techtown.northkorean_memorization;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PreCreateDB {

    /**
     * 외부의 DB를 내부 DB로 복사하는 함수
     */
    public static void copyDB(Context context) {

        String dbName = "Words.db";
        try{
            String destPath ="/data/data/"+ context.getPackageName() + "/databases";

            File f = new File(destPath);
            if(!f.exists())
                f.mkdir();

            String dbPath = destPath + "/" + dbName;
            f = new File(dbPath);
            if (!f.exists())
                rawCopy(context.getAssets().open(dbName), new FileOutputStream(dbPath));

            Log.d("PreCreateDB", "External DB loading Completed");
        }catch (FileNotFoundException e){
            Log.e("PreCreateDB", "file Not Found");
            e.printStackTrace();
        }catch (IOException e){
            Log.e("PreCreateDB", "IOException");
            e.printStackTrace();
        }
    }

    public static void rawCopy(InputStream inputStream, OutputStream outputStream) throws IOException{
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) >0){
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}
