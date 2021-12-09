package gitobject;

import repo.Repository;
import utils.SHA1;
import utils.*;
import utils.ZLibUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

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


    /**
     * Restoring the commit object according to its key(commitId), and reading its content
     * @param commitId
     * @throws IOException
     */
    public Commit(String commitId) throws IOException {
        setType("commit");
        setKey(commitId);
        //String path = Repository.getGitDir() + File.separator + "objects";
        String path = Utils.getJitDir() + File.separator + "objects";
        File file = new File(path + File.separator + commitId);
        if(file.isFile()){

            FileInputStream is = new FileInputStream(file);
            byte[] output = ZLibUtils.decompress(is);
            is.close();
            setValue(new String(output));
            //value = new String(output);

            //tree = FileReader.readCommitTree(value);
            setTree(utils.FileReader.readCommitTree(getValue()));
            setParent(utils.FileReader.readCommitParent(getValue()));
            //parent = FileReader.readCommitParent(value);
            setAuthor(utils.FileReader.readCommitAuthor(getValue()));
            //author = FileReader.readCommitAuthor(value);
            setCommitter(utils.FileReader.readCommitter(getValue()));
            //committer = FileReader.readCommitter(value);
            setMessage(utils.FileReader.readCommitMsg(getValue()));
            //message = FileReader.readCommitMsg(value);
        }
    }




    /**
     * Construct a commit from a built tree.
     * @param
     * author, committer, message参数在git commit命令里创建
     * @throws Exception
     */
    public Commit(Tree tree, String author, String committer, String message) throws Exception {
        //this.fmt = "commit"; 	//type of object
        setType("commit");
        setTree(tree.getKey());
        //this.tree = t.getKey();
        setParent(getLastCommit() == null ? "" : getLastCommit());
        //this.parent = getLastCommit() == null ? "" : getLastCommit(); //null means there is no parent commit.
        setAuthor(author);
        setCommitter(committer);
        setMessage(message);
        setPath(Utils.getObjectsPath());
        /*Content of this commit, like this:
         *tree bd31831c26409eac7a79609592919e9dcd1a76f2
         *parent d62cf8ef977082319d8d8a0cf5150dfa1573c2b7
         *author xxx  1502331401 +0800
         *committer xxx  1502331401 +0800
         *修复增量bug
         * */
        setValue("tree " + getTree()+ "\n" +
                "parent " + getParent() + "\n" +
                "author " + getAuthor() + "\n" +
                "commiter " + getCommitter() + "\n" +
                getMessage());
        setKey(genKey());
        if(isValidCommit()) {
            compressWrite();
        }
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
    public boolean isValidCommit() throws IOException {
        Commit parentContent = new Commit(getParent());
        //String parentTreeKey = parentContent.tree;
        String parentTreeKey = parentContent.getTree();
        //fixme 注意是否＋感叹号
    	return (parentTreeKey == null || parentTreeKey.equals(getTree()));
    }

    /**
     * Get the parent commit from the HEAD file.
     * @return
     * @throws IOException
     */
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

