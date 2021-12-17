package core;

import utils.Utils;
import java.io.File;
import java.io.IOException;

public class JitLog {
    public static void printLog() throws Exception {

        File file = new File(Utils.getWorkDir()+File.separator+".jit"+File.separator+"LOG");
        if(file.exists()){
            String res = Utils.getContentFromFile(file);
            System.out.println(res);
        }else {
            throw new IOException();
        }

    }
}
