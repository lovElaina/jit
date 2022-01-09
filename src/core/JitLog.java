package core;

import branch.Branch;
import gitobject.Commit;
import utils.Utils;
import java.io.File;
import java.io.IOException;

import static core.JitBranch.getBranch;

public class JitLog {
    public static String printLog() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Branch currentBranch = getBranch();
        String commitKey = currentBranch.getCommitKey();
        Commit curCommit = new Commit(commitKey);
        stringBuilder.append("--------------------------------------------------").
                append("\n").append(curCommit.getValue()).append("\n");
        System.out.println("--------------------------------------------------");
        System.out.println(curCommit.getValue());
        String parentKey = curCommit.getParent();

        while (!parentKey.equals("null")) {
            Commit commit = new Commit(parentKey);
            stringBuilder.append("--------------------------------------------------").append("\n")
                    .append(commit.getValue()).append("\n").append("--------------------------------------------------");
            System.out.println("--------------------------------------------------");
            System.out.println(commit.getValue());
            System.out.println("--------------------------------------------------");
            parentKey = commit.getParent();
        }
        String str = stringBuilder.toString();
        return str;
    }

        /*public void logCommitHistory() throws IOException {
            System.out.println(getValue());
            String parentId = getParent();
            while(parentId != null){
                Commit commit = new Commit(parentId);
                System.out.println(commit.getValue());
                parentId = commit.getParent();
            }
        }*/

}
