package core;

import branch.Branch;
import gitobject.Blob;
import gitobject.Commit;
import gitobject.GitObject;
import gitobject.Tree;
import tmp.Index;
import utils.FileCreation;
import utils.FileDeletion;
import utils.FileReader;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;


public class JitReset {

    //重置到最近一次提交，只重置暂存区
    public static void newReset() throws Exception {
        reset(JitBranch.getBranch().getCommitKey());
    }

    //重置到特定的提交（commitKey），只重置暂存区
    public static void reset(String commitKey) throws IOException {
        Index idx = new Index();
        idx.clear();
        Branch branch = JitBranch.getBranch();
        branch.resetCommit(commitKey);
        Commit commit = new Commit(commitKey);
        Tree tree = new Tree(commit.getTree());
        File indexFile = new File(Utils.getJitDir() + File.separator + "index");
        FileWriter fw = new FileWriter(indexFile);
        fw.write(tree.getValue());
        fw.flush();
        fw.close();
    }

    //重置到最近一次提交
    public static void newHardReset() throws Exception {
        hardReset(JitBranch.getBranch().getCommitKey());
    }

    //重置到特定的提交（commitKey），工作区和暂存区的文件也同步重置
    public static void hardReset(String commitKey) throws Exception {
        //在工作目录删除所有文件（除了.jit外）
        File[] worktree = new File(Utils.getWorkDir()).listFiles();
        for(int i = 0; i < worktree.length;i++){
            if(!worktree[i].getName().equals(".jit")){
                FileDeletion.deleteFile(worktree[i]);
            }
        }
        Branch branch = JitBranch.getBranch();
        branch.resetCommit(commitKey);
        Commit commit = new Commit(commitKey);
        Tree tree = new Tree(commit.getTree());

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
    }
}
