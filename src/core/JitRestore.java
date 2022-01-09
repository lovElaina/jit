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

import java.io.File;
import java.util.ArrayList;

//git restore命令，会撤销文件的修改，使暂存区的文件恢复到工作区，即最近一次执行完jit add的状态
//注意：没有放入暂存区的文件会消失
public class JitRestore {
    public static void restore() throws Exception {
        //在工作目录删除所有文件（除了.jit外）
        File[] worktree = new File(Utils.getWorkDir()).listFiles();
        for(int i = 0; i < worktree.length;i++){
            if(!worktree[i].getName().equals(".jit")){
                FileDeletion.deleteFile(worktree[i]);
            }
        }
        Index idx = new Index();
        ArrayList<String[]> idxList = idx.getIndexs();
        for(int i=0;i<idxList.size();i++){
            String key = idxList.get(i)[1];
            String fdir = idxList.get(i)[2];
            String[] pa = fdir.split(Utils.SEP);
            String path = Utils.getWorkDir();
                for(int j = 0; j < pa.length-1; i++) {
                    path += File.separator + pa[i];
                }
                new File(path).mkdirs();
                FileCreation.createFile(path,pa[pa.length-1],JitOpr.JitRec(key));
            }
        }

}
