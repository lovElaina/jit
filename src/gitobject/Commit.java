package gitobject;

import repo.Repository;
import utils.FileReader;
import utils.SHA1;
import utils.*;
import utils.ZLibUtils;

import java.io.*;

public class Commit extends GitObject{
    private String sha_tree; 		// the sha1 value of present committed tree
    private String sha_parent; 	// the sha1 value of the parent commit
    private String author; 	// the author's name and timestamp
    private String committer; // the committer's info
    private String message; 	// the commit message

    public void setTree(String sha_tree) {
        this.sha_tree = sha_tree;
    }

    public void setParent(String sha_parent) {
        this.sha_parent = sha_parent;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParent(){return sha_parent;}
    public String getTree(){return sha_tree;}
    public String getAuthor(){return author;}
    public String getCommitter(){return committer;}
    public String getMessage(){return message;}

    public Commit(){}


    //从序列化后的commitKey文件中生成commit对象
    public Commit(String commitKey) throws IOException {
        setType("commit");
        setKey(commitKey);
        //String path = Repository.getGitDir() + File.separator + "objects";
        String path = Utils.getJitDir() + File.separator + "objects";
        File file = new File(path + File.separator + commitKey);
        if(file.isFile()){
            FileInputStream is = new FileInputStream(file);
            byte[] output = ZLibUtils.decompress(is);
            is.close();
            setValue(new String(output));
            setTree(utils.FileReader.readCommitTree(getValue()));
            setParent(utils.FileReader.readCommitParent(getValue()));
            setAuthor(utils.FileReader.readCommitAuthor(getValue()));
            setCommitter(utils.FileReader.readCommitter(getValue()));
            setMessage(utils.FileReader.readCommitMsg(getValue()));
        }
    }




    /**
     * Construct a commit from a built tree.
     * @param
     * author, committer, message参数在git commit命令里创建
     * @throws Exception
     */
    public Commit(Tree tree, String author, String committer, String message) throws Exception {
        setType("commit");
        setTree(tree.getKey());
        if(getLastCommit() == null){
            setParent("");
        }else {
            setParent(getLastCommit());
        }
        //setParent(getLastCommit() == null ? "" : getLastCommit());
        setAuthor(author);
        setCommitter(committer);
        setMessage(message);
        setPath(Utils.getObjectsPath());
        setValue("tree " + getTree()+ "\n" +
                "parent " + getParent() + "\n" +
                "author " + getAuthor() + "\n" +
                "commiter " + getCommitter() + "\n" +
                getMessage());
        setKey(genKey());
        /*if(isValidCommit()) {*/
            compressWrite();
            compressWriteHistory();
        //}
    }

    private void compressWriteHistory() throws IOException {
        /*byte[] data = ZLibUtils.compress(getValue().getBytes());
        FileOutputStream fos = new FileOutputStream(Utils.getWorkDir() + File.separator + getKey());
        fos.write(data);
        fos.close();*/
        Utils.writeFile(Utils.getWorkDir()+File.separator+".jit"+File.separator+"LOG",getValue());
    }


    /**
     * Generate the hash value of this commit.
     * @return key
     * */
    public String genKey() throws Exception {
        return SHA1.getHash(getValue());
    }


    /**
     * Determine if committed content is changed. If not, the commit is illegal.
     * @return boolean tree != parent
     * @throws IOException 
     */
/*    public boolean isValidCommit() throws IOException {
        Commit parentContent = new Commit(getParent());
        //String parentTreeKey = parentContent.tree;
        String parentTreeKey = parentContent.getTree();
        //fixme 注意是否＋感叹号
        System.out.println("---------"+ parentTreeKey);
        System.out.println("---------"+ getTree());
    	return (parentTreeKey == null || parentTreeKey.equals(getTree()));
    }*/


    //从HEAD文件中得到parent commit key
    public static String getLastCommit() throws IOException {

        //File HEAD = new File(Repository.getGitDir() + File.separator + "HEAD");
        File HEAD = new File(Utils.getJitDir() + File.separator + "HEAD");
        String path = Utils.getContentFromFile(HEAD).substring(5).replace("\n","");
        //String path = getValue(HEAD).substring(5).replace("\n","");
        File branchFile = new File(Utils.getJitDir() + File.separator + path);

        if(branchFile.isFile()) {
            return Utils.getContentFromFile(branchFile);
        }
        else {
            return null;
        }
    }

}

