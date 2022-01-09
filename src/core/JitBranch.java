package core;

import branch.Branch;
import utils.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//这里定义对于分支的一系列操作
public class JitBranch {

    //得到HEAD中存放的branch路径，例如：ref: refs/heads/master，substring(16)就是master
    //并返回由它得到的branch对象
    public static Branch getBranch() throws IOException {
        File HEAD = new File(Utils.getJitDir() + File.separator + "HEAD");
        //String branchName = GitObject.getValue(HEAD).substring(16).replace("\n","");
        String branchName = Utils.getContentFromFile(HEAD).substring(16).replace("\n","");
        Branch branch = new Branch(branchName);
        return branch;
    }

    //判断分支名所对应的分支文件是否存在，若存在返回true
    public static boolean branchExist(String branchName)throws FileNotFoundException {

        File branch = new File(Utils.getJitDir() + File.separator + "refs"
                + File.separator + "heads" + File.separator + branchName);
        return branch.isFile();
    }

    //创建一个新的branch并且把这个branch文件添加到refs/heads文件夹
    public static void branchAdd(String branchname, String commitId) throws IOException {
        Branch branch = new Branch(branchname, commitId);
        branch.addBranch();
    }


    //列出所有现存的分支名
    public static String branch() throws IOException {
        StringBuilder str = new StringBuilder("");
        if(!branchExist("master")){
            throw new FileNotFoundException("master分支不存在，你应该至少先提交一次");
            //System.out.println("master分支不存在，你应该至少先提交一次");
            //return;
        }
        Branch currentBranch = getBranch();
        File[] branchList = new File(Utils.getJitDir() + File.separator + "refs"
                + File.separator + "heads").listFiles();
        for(int i = 0; i < branchList.length; i++){
            //从HEAD文件中读取的branch名和refs/heads中的branch文件名相同，表示是当前分支，前面加上*号
            if(currentBranch.getBranchName().equals(branchList[i].getName())){
                System.out.print("*");
                str.append("*");
            }
            else{
                System.out.print(" ");
                str.append(" ");
            }
            System.out.println(branchList[i].getName());
            str.append(branchList[i].getName()).append("\n");
        }
        return str.toString();
    }


    //在master分支处再创建一个分支
    public static void createBranchWithMaster(String branchName) throws IOException {
        if(!branchExist("master")){
            throw new FileNotFoundException("master分支不存在，你应该至少先提交一次");
            //System.out.println("master分支不存在，你应该至少先提交一次");
            //return;
        }
        //Branch master = new Branch("master");
        Branch cur = getBranch();
        Branch branch = new Branch(branchName, cur.getCommitKey());
        //这里可以这样理解：
        //...refs/heads文件夹下，有很多branch文件，我们添加了一个
        branch.addBranch();
    }


    //删除分支，在此之前请确保
    // 1、希望删除的分支名存在
    // 2、删除的分支不是当前分支
    public static void deleteBranch(String branchName) throws IOException {
        if(!branchExist(branchName)){
            System.out.println("错误：找不到名为"+"'"+branchName+"'"+"的分支");
        }
        if (branchName.equals(getBranch().getBranchName())){
            System.out.println("错误：不能删除当前所在的分支 ");
        }
        File branch = new File(Utils.getJitDir() + File.separator + "refs" + File.separator +
                "heads" + File.separator + branchName);
        if(branch.delete()){
            System.out.println("名为" + "'" + branchName + "'" +"的分支已经被删除");
        }else throw new IOException("删除失败，请检查");

    }



}
