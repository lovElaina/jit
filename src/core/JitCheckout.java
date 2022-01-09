package core;

import branch.Branch;
import gitobject.Commit;
import gitobject.Tree;
import tmp.Index;
import utils.FileCreation;
import utils.FileDeletion;
import utils.Utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JitCheckout {

    static String path = Utils.getJitDir() + File.separator + "refs" + File.separator + "heads";

    //确定位于refs/heads，且名为branchNames的分支文件是否存在
    //注意：最好不要频繁使用此方法，虽然方便，但会破坏代码逻辑！我本意是要把它放入Utils里
    public static boolean branchExist(String branchNames){
        File branchFile = new File(path + File.separator + branchNames);
        return branchFile.exists();
    }

    //切换到名为branchName的分支，它比changeHead方法多了一个条件判断
    public static void checkout(String branchName) throws Exception {
        if(branchExist(branchName)){
            //在工作目录删除所有文件（除了.jit外）
            System.out.println("HEAD当前指向"+branchName);
            System.out.println("删除的文件位于"+Utils.getWorkDir());
            File[] worktree = new File(Utils.getWorkDir()).listFiles();
            for (File file : worktree) {
                if (!file.getName().equals(".jit")) {
                    FileDeletion.deleteFile(file);
                    System.out.println("-"+file.getName());
                }
            }
            changeHead(branchName);
            Branch branch = JitBranch.getBranch();
            Commit commit = new Commit(branch.getCommitKey());
            Tree tree = new Tree(commit.getTree());
            System.out.println("树对象是"+commit.getTree());
            //通过树对象恢复所有的文件
            FileCreation.recoverWorkTree(tree, Utils.getWorkDir());
            //删除所有index文件
            Index index = new Index();
            index.clear();
            //把目前的所有文件记录添加到index中
            File[] workTree = new File(Utils.getWorkDir()).listFiles();
            for(int i = 0; i < workTree.length; i++){
                if(!workTree[i].getName().equals(".jit"))
                    JitAdd.add(workTree[i]);
            }
        } else{
            System.out.println(branchName + "不存在，无法切换");
        }
    }

    //改变HEAD文件中的分支名，HEAD文件相当于指针，指向最近操作的分支名
    public static void changeHead(String branchName) throws IOException {
        File HEAD = new File(Utils.getJitDir() + File.separator + "HEAD");
        FileWriter fileWriter = new FileWriter(HEAD);
        fileWriter.write("ref: refs/heads/" + branchName);
        fileWriter.close();
    }
}
