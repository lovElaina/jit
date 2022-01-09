package branch;
import gitobject.Commit;
import repo.Repository;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

//Git的分支，其实本质上仅仅是指向提交对象的可变指针
public class Branch {
    //分支名
    private String branchName;
    //commit的40位16进制数（key）
    private String commitKey;
    /////////////////////////
    //保存分支指向commit key的文件
    String path = Utils.getJitDir() + File.separator + "refs" + File.separator + "heads";
    /////////////////////////

    public String getBranchName(){
        return branchName;
    }
    public String getCommitKey(){
        return commitKey;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setCommitKey(String commitKey) {
        this.commitKey = commitKey;
    }

    //普通构造方法，分别传入分支名和commit key，在JitCommit中会使用到
    public Branch(String branchName, String commitKey){
        setBranchName(branchName);
        setCommitKey(commitKey);
        //this.branchName = branchName;
        //this.commitKey = commitKey;
    }


    //重载构造方法，只传入分支名，通过分支名查找refs/heads文件中保存的commit key
    public Branch(String branchName) throws IOException {
        //String path = Repository.getGitDir() + File.separator + "refs" + File.separator + "heads";
        //this.branchName = branchName;
        setBranchName(branchName);
        File currBranchFile = new File(path + File.separator + branchName);
        if(currBranchFile.exists()) {
            commitKey = Utils.getContentFromFile(currBranchFile);
        } else {
            throw new FileNotFoundException("分支不存在，请检查拼写错误");
        }
    }


    //重新设置commitKey，并且修改refs/heads中的commit key
    public void resetCommit(String commitKey) throws IOException {
        setCommitKey(commitKey);
        addBranch();
    }

    //确定位于refs/heads的分支文件是否存在
    public boolean branchExist(){
        //String path = Repository.getGitDir() + File.separator + "refs" + File.separator + "heads";
        File branchFile = new File(path + File.separator + branchName);
        return branchFile.exists();
    }

    //重载方法，确定位于refs/heads，且名为branchNames的分支文件是否存在
    //注意：最好不要频繁使用此方法，虽然方便，但会破坏代码逻辑！我本意是要把它放入Utils里
    public boolean branchExist(String branchNames){
        File branchFile = new File(path + File.separator + branchNames);
        return branchFile.exists();
    }


    //改变HEAD文件中的分支名，HEAD文件相当于指针，指向最近操作的分支名
    public void changeHead() throws IOException {
        File HEAD = new File(Utils.getJitDir() + File.separator + "HEAD");
        FileWriter fileWriter = new FileWriter(HEAD);
        fileWriter.write("ref: refs/heads/" + branchName);
        fileWriter.close();
    }


    //在refs/heads文件夹内添加或替换branch文件
    public void addBranch() throws IOException {
        //String path = Repository.getGitDir() + File.separator + "refs" + File.separator + "heads";
        File branchFile = new File(path + File.separator + branchName);
        FileWriter fileWriter = new FileWriter(branchFile);
        fileWriter.write(commitKey);
        fileWriter.close();
    }




}
