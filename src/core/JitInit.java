package core;

import branch.Branch;
import utils.FileDeletion;
import repo.Repository;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JitInit {
	/**
	 * Init repository in your working area. The workTree should never be null.
	 * @param workTree
	 * @throws IOException
	 */
    public static void init(String workTree) throws IOException {
        Repository repo = new Repository(workTree);
        if(!repo.exist()){
            System.out.println("Jit仓库不存在，现在建立");
            repo.createRepo();
        }
        System.out.println("Jit仓库已经存在了.");
        //这里建立master分支文件
        File file = new File(Utils.getJitDir() + File.separator + "refs" + File.separator + "heads");
        if(Objects.requireNonNull(file.list()).length == 0) {
            Branch master = new Branch("master", "null");
            master.addBranch();
        }
    }
}
