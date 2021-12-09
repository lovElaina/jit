package repo;

import utils.FileCreation;

import java.io.File;
import java.io.IOException;

/**
 * Todo: Add your own code. JitInit.init("worktree") should be able to create a repository "worktree/.jit" ,
 *       which contains all the default files and other repositories inside. If the repository has
 *       already exists, delete the former one and create a new one. You're welcome to reconstruct the code,
 *       but don't change the code in the core directory.
 */
public class Repository {
    private static String workTree;	//working directory
    private static String gitDir;	//jit repository path

    /**
     * Constructor
     */
    public Repository() throws IOException {
        if(gitDir == ""){
            throw new IOException("The repository does not exist!");
        }
    }
    
    /**
     * Construct a new repository instance with certain path.
     * Constructor
     * @param path
     * @throws IOException
     */
    public Repository(String path) throws IOException {
        workTree = path;
        gitDir = path + File.separator + ".jit";
    }

    public static String getGitDir() {
        return gitDir;
    }

    public static String getWorkTree() {
        return workTree;
    }
    
    /**
     * Helper functions.
     * @return
     */
    public boolean exist(){ return new File(gitDir).exists(); }

    public boolean isFile(){ return new File(gitDir).isFile(); }

    public boolean isDirectory(){ return new File(gitDir).isDirectory(); }


    /**
     * Create the repository and files and directories inside.
     * @return boolean
     * @throws IOException
     */
    public void createRepo() throws IOException {
    /* Todo：Add your code here. */
        /**
         * 这是我期望得到的目录层级结构
         * ├── hooks
         * ├── info
         * ├── logs
         * │   └── refs
         * │       └── heads
         * ├── objects
         * ├── refs
         * │   ├── heads
         * │   └── tags
         * ├── config
         * ├── description
         * └── HEAD
         */
        File file = new File(gitDir);
        try {
            if(!file.exists()){
                //在gitDir路径下创建名为.jit的空文件夹
                file.mkdir();
            }
            //设置.jit文件默认为隐藏属性
            String s = " attrib +H "+file.getAbsolutePath();
            Runtime.getRuntime().exec(s);
        } catch (IOException e){
            e.printStackTrace();
        }
        //在.jit目录下创建名为hooks的空文件夹
        FileCreation.createDirectory(gitDir, "hooks");
        //在.jit目录下创建名为info的空文件夹
        FileCreation.createDirectory(gitDir, "info");
        //在.jit目录下创建名为objects的空文件夹
        FileCreation.createDirectory(gitDir, "objects");
        //在.jit目录下创建名为refs的空文件夹
        FileCreation.createDirectory(gitDir, "branches");
        /*在.jit目录下创建名为logs的空文件夹
        FileCreation.createDirectory(gitDir, "logs");*/
        //在.jit目录下的logs的文件夹下的名为refs的文件夹下创建heads文件夹
        FileCreation.createDirectory(gitDir, "logs", "refs", "heads");
        //在.jit目录下的名为refs的文件夹下创建heads文件夹
        FileCreation.createDirectory(gitDir, "refs", "heads");
        //在.jit目录下的名为refs的文件夹下创建tags文件夹
        FileCreation.createDirectory(gitDir, "refs", "tags");
        //在.jit目录下创建名为config的空文件,并写入字符串
        FileCreation.createFile(gitDir, "config","[core]\n" +
                "\trepositoryformatversion = 0\n" +
                "\tfilemode = false\n" +
                "\tbare = false\n" +
                "\tlogallrefupdates = true\n" +
                "\tsymlinks = false\n" +
                "\tignorecase = true");
        //在.jit目录下创建名为description的空文件，并写入字符串
        FileCreation.createFile(gitDir, "description", "Unnamed repository; edit this file 'description' to name the repository.");
        //在.jit目录下创建名为HEAD的空文件，并写入字符串"ref: refs/heads/master"
        FileCreation.createFile(gitDir, "HEAD", "ref: refs/heads/master");

    }

}
