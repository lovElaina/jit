package gitobject;

import tmp.ObjectNode;
import utils.FileReader;
import utils.SHA1;
import utils.Utils;
import utils.ZLibUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Tree extends GitObject{


    public ArrayList<GitObject> treeList;	//GitObjects in tree

    public ArrayList<GitObject> getTreeList(){
        return treeList;
    }

    public void setTreeList(ArrayList<GitObject> treeList) {
        this.treeList = treeList;
    }

    public Tree(){}

    @Override
    public String genKey() throws Exception {
        return null;
    }

/*    public Tree(File file) throws Exception {
        treeList = new ArrayList<>();
        setType("tree");
        setNum("040000");
        setValue("");

        genKey(file);
        compressWrite();
    }*/

    /**
     * Constructing tree from index.
     * @param indexTree
     * @throws Exception
     */
    public Tree(TreeMap<String, ObjectNode> indexTree) throws Exception {
        setTreeList(new ArrayList<>());
        setType("tree");
        setNum("040000");
        setValue("");
        setKey(genKey(indexTree));
        setPath(Utils.getObjectsPath());
        //System.out.println("虽然错了，但居然算出了key"+genKey(indexTree)+getValue());
        compressWrite();
    }

    public Tree(String key) throws IOException {
        setTreeList(new ArrayList<>());
        setType("tree");
        setNum("040000");
        try{
            File file = new File(Utils.getObjectsPath() + File.separator + key);
            if(file.exists()){
                setKey(key);
                //key = Id;
                FileInputStream is = new FileInputStream(file);
                byte[] output = ZLibUtils.decompress(is);
                is.close();
                setValue(new String(output));
                genTreeList();
            }
            else{
                throw new IOException();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Generate treelist from the value of an existed tree object.
     * @throws IOException
     */
    public void genTreeList() throws IOException {
        ArrayList<String> list = FileReader.readByBufferReader(getValue());
        for(int i = 0; i < list.size(); i++){
            if(FileReader.readObjectFmt(list.get(i)).equals("blob")){
                Blob blob = new Blob(FileReader.readObjectKey(list.get(i)));
                treeList.add(blob);
            }
            else{
                Tree tree = new Tree(FileReader.readObjectKey(list.get(i)));
                treeList.add(tree);
            }
        }
    }
    /**
     * Generate the key of a tree object from index.
     * @param indexTree
     * @return String key
     * @throws Exception
     */
    public String genKey(TreeMap<String, ObjectNode> indexTree) throws Exception {
        for(Map.Entry<String, ObjectNode> pair: indexTree.entrySet()) {
            if(pair.getValue().indexTree != null) {
                Tree tree = new Tree(pair.getValue().indexTree);
                treeList.add(tree);
                tree.compressWrite();
                setValue(getValue()+"040000 tree " + tree.getKey() + "\t" + pair.getKey() + "\n");
            }else {
                //System.out.println("这里"+pair.getValue().blobKey);
                Blob blob = new Blob(pair.getValue().blobKey);
                treeList.add(blob);
                //不需要compresseWrite，因为在git add之后，即生成index时，已经对所有文件进行了compresseWrite压缩写入！
                setValue(getValue()+"100644 blob " + blob.getKey() + "\t" + pair.getKey() + "\n");
            }

        }
        //setKey(SHA1.getHash(getValue()));
        //key = SHA1.getHash(value);
        return SHA1.getHash(getValue());
    }


}
