package utils;

import gitobject.Blob;
import gitobject.Tree;
import repo.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Deserialize {

    /**
     * 反序列化一个位于.jit/objects 文件夹下的 blob 文件，该文件应已经过SHA1及Zlib处理
     *
     * @param key 文件经过 hash(SHA1)生成的 40 位 16 进制数组成的字符串
     *
     * @注意 在 git 中，文件经过 hash 生成的 40 位 16 进制数，前两位作为文件夹名，
     * 后 38 位作为文件名存储于其对应的文件夹下，显然此处规定的函数参数与前者的逻辑存在差异。
     * 我不能确定此处是不是任务发布者有意为之，为了保险起见而没有选择重构。
     *
     * 例如：执行 git add 命令后，该文件相关信息，例如文件名、类型标识、hash值等，
     * 都会被存入.git/index文件中，同时会借助 blob对象，生成经由 hash 处理过的文件，
     * 这个文件位于.../.git/objects/xx(文件夹名是任意2位16进制 )文件夹下，
     * 而文件名为剩余的38位16进制数字。
     */
    public static Blob deserializeBlob(String key) throws IOException {
        Blob blob = new Blob();
        blob.setType("Blob");
        blob.setNum("100644");
        blob.setPath(Utils.getObjectsPath());
        FileInputStream fileInputStream = null;
        try {
            //path字符串表示路径：.../.jit/objects
            File file = new File(blob.getPath() + File.separator + key);
            if (!file.exists()) {
                throw new FileNotFoundException("文件不存在");
            }
            blob.setKey(key);
            fileInputStream = new FileInputStream(file);
            blob.setValue(new String(ZLibUtils.decompress(fileInputStream)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert fileInputStream != null;
            fileInputStream.close();
        }
        return blob;
    }

    /**
     * Deserialize a tree object with treeId and its path.
     * @param key
     * @throws IOException
     */
    public static Tree deserializeTree(String key) throws IOException {

        Tree tree = new Tree();
        tree.setTreeList(new ArrayList<>());
        tree.setType("tree");
        tree.setNum("040000");
        tree.setKey(key);
        try{
            File file = new File(Utils.getObjectsPath() + File.separator + key);
            if(!file.exists()){
                throw new IOException();
            }
            FileInputStream fileInputStream = new FileInputStream(file);

            tree.setValue(new String(ZLibUtils.decompress(fileInputStream)));
            fileInputStream.close();

            ArrayList<String> list = FileReader.readByBufferReader(tree.getValue());
            for (String s : list) {

                if (FileReader.readObjectFmt(s).equals("blob")) {
                    tree.treeList.add(Deserialize.deserializeBlob(FileReader.readObjectKey(s)));
                } else if (FileReader.readObjectFmt(s).equals("tree")){
                    tree.treeList.add(Deserialize.deserializeTree(FileReader.readObjectKey(s)));
                } else {
                    throw new FileNotFoundException();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tree;
    }
}
