package core;

import utils.FileDeletion;
import repo.Repository;

import java.io.IOException;

public class JitInit {
	/**
	 * Init repository in your working area. The workTree should never be null.
	 * @param workTree
	 * @throws IOException
	 */
    public static void init(String workTree) throws IOException {
        Repository repo = new Repository(workTree);
        if(!repo.exist()){
/*            if(repo.isDirectory()){
                FileDeletion.deleteFile(Repository.getGitDir());
            }
            else if(repo.isFile()){
                throw new IOException(".jit is a file, please check");
            }*/
            System.out.println(".jit仓库不存在，现在建立");
            repo.createRepo();
        }

        System.out.println("Jit repository has been initiated successfully.");
    }
}
