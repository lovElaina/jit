package gitobject;

import utils.SHA1;
import utils.Utils;
import utils.ZLibUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class GitObject {
    //public abstract void deserialize(String id,GitObject gitObject) throws IOException;
    private String type;  //  blob|tree|commit
    private String num;   //  100644|040000
    private String key;
    private String value;
    private String path;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public GitObject(){}
/*    public GitObject(String addname) throws Exception {
        //返回该文件名对应的文件的内容
        value = Utils.getContentFromPath(addname);
        //这里把生成key单独作为一个方法，因为tree的生成key步骤完全不同
        //key = SHA1.getHash(value);
        path = Utils.getObjectsPath();
    }*/

    public void compressWrite() throws Exception{
        byte[] data = ZLibUtils.compress(value.getBytes());
        FileOutputStream fos = new FileOutputStream(path + File.separator + key);
        fos.write(data);
        fos.close();
    }

    public abstract String genKey() throws Exception;

}
