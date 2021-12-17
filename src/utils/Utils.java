package utils;


import repo.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Utils {

    //工作路径，即包含.jit的文件夹
    public static String workDir;

    //全局初始化，暂时留置
    public static void init(){
    }

    //设置工作路径，即包含.jit的文件夹
    public static void setWorkDir(String workDir) {
        Utils.workDir = workDir;
    }
    //获取工作路径，即包含.jit的文件夹
    public static String getWorkDir() {
        return workDir;
    }
    //获取.jit所在的路径
    public static String getJitDir() {
        return workDir + File.separator + ".jit";
    }

    //得到文件直到objects文件夹的路径
    public static String getObjectsPath(){
        //return Repository.getGitDir() + File.separator + "objects";
        return workDir + File.separator + ".jit" + File.separator + "objects";
    }

    /**
     * 将文件的value压缩后放入Object/<key>,注意这个方法不依赖与任何实例化对象！
     * @param key  文件通过hash生成的 key
     * @param value  文件自身的内容
     * @param path  文件路径
     * @throws Exception 直接抛出异常，无所谓了
     */
    public static void compressWrite(String key,String value,String path) throws Exception{
        byte[] data = ZLibUtils.compress(value.getBytes());
        FileOutputStream fos = new FileOutputStream(path + File.separator + key);
        fos.write(data);
        fos.close();
    }


    /**
     * 这个方法，它只是读取文件里的文本信息，和压缩、哈希之类没有任何关系。
     * @param file  待读取信息的文件
     * @return  文件有什么，它返回什么，比如你在txt文件中写入”hello“，它就给你返回”hello“字符串
     */
    public static String getContentFromFile(File file) throws IOException {
        byte[] bytes = new byte[0];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
        }catch (FileNotFoundException e){
            System.out.println("文件没有找到1");
        }
        return new String(bytes);
    }
    /**
     * 这个方法，它也是只读取文件里的文本信息，和压缩、哈希之类没有任何关系。
     * @param addname  只是把上一个方法的文件换成了文件名，换汤不换药
     * @return  文件有什么，它返回什么，比如你在txt文件中写入”hello“，它就给你返回”hello“字符串
     */
    public static String getContentFromPath(String addname) throws IOException {
        File file = new File(Utils.getWorkDir()+File.separator+addname);
        byte[] bytes = new byte[0];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
        }catch (FileNotFoundException e){
            System.out.println("文件没有找到2");
        }
        return new String(bytes);
    }

    /**
     * 将字符串追加到文件已有内容后面
     *
     * @param fileFullPath 文件完整地址：D:/test.txt
     * @param content 需要写入的
     */
    public static void writeFile(String fileFullPath,String content) {
        FileOutputStream fos = null;
        try {
            //true不覆盖已有内容
            fos = new FileOutputStream(fileFullPath, true);
            //写入
            fos.write(content.getBytes());
            // 写入一个换行
            fos.write("\r\n".getBytes());
            fos.write("----------------------------------------------------------------".getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static final String SEP ="\\\\";
}
