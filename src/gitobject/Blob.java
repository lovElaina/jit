package gitobject;


import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Blob extends GitObject {

    public Blob(){
        super();
        setType("Blob");
        setNum("100644");
    }

    /**
     * Constructing blob object from a file
     * @param file
     * @throws Exception
     */
    public Blob(File file) throws Exception {
        setType("Blob");
        setNum("100644");
        setValue(Utils.getContentFromFile(file));
        setKey(genKey());
        setPath(Utils.getObjectsPath());
    }

/*    public Blob(String addname) throws Exception {
        super(addname);
        setType("Blob");
        setNum("100644");
        setKey(genKey());
    }*/

    public Blob(String key,int flag) throws IOException {
        setPath(Utils.getObjectsPath());
        //this.path = path;
        setType("blob");
        //fmt = "blob";
        setNum("100644");
        //mode = "100644";
        try{
            File file = new File(getPath() + File.separator + key);
            if(file.exists()){
                setKey(key);
                //key = Id;
                FileInputStream is = new FileInputStream(file);
                byte[] output = ZLibUtils.decompress(is);
                setValue(new String(output));
                //value = new String(output);
            }
            else{
                throw new IOException();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String genKey() throws Exception {
        return SHA1.getHash(getValue());
    }

}
