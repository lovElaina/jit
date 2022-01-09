package core;

import utils.Utils;
import utils.ZLibUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JitOpr {
    public static void JitCat(String key) throws IOException {
        File file = new File(Utils.getObjectsPath() + File.separator + key);
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        byte[] output = ZLibUtils.decompress(is);
        //label4.setText(new String(output));
        String str = new String(output);
        //str = str.replaceAll("\n","<br/>");
        //str = "<html><body>"+str+"</body></html>";
        System.out.println(str);
        is.close();
    }

    public static String JitRec(String key) throws IOException {
        File file = new File(Utils.getObjectsPath() + File.separator + key);
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        byte[] output = ZLibUtils.decompress(is);
        String str = new String(output);
        is.close();
        return str;
    }

    public static String JitStage() throws IOException {
        File file = new File(Utils.getJitDir()+File.separator+"index");
        System.out.println(Utils.getContentFromFile(file));
        return Utils.getContentFromFile(file);
    }
}
