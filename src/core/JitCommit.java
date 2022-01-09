package core;

import branch.Branch;
import gitobject.*;

import tmp.*;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JitCommit {

    //创建commit对象并保存到repository，并且把commit的key写入branch文件
    public void commit() throws Exception {
        Tree tree = buildTree();
        String author = System.getProperty("user.name") + " " + String.valueOf(new Date().toString());
        String committer = System.getProperty("user.name");

        System.out.println("Please enter the commit message: ");
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();

        Commit commit = new Commit(tree, author, committer, message);
		//Index index = new Index();
		//index.clear();

        updateBranch(commit);
    }

    //SwingJIT专用的方法，传入自定义的提交信息，其余步骤和上一个方法一致
	public void commit(String message) throws Exception {
		Tree tree = buildTree();
		String author = System.getProperty("user.name") + " " + String.valueOf(new Date().toString());
		String committer = System.getProperty("user.name");
		Commit commit = new Commit(tree, author, committer, message);
		//Index index = new Index();
		//index.clear();
		updateBranch(commit);
	}

	//更新branch，把commit key信息写入refs/heads中
	public void updateBranch(Commit commit) throws IOException {
		String commitKey = commit.getKey();
		//得到当前的branch，该路径位于.jit文件夹中的HEAD文件中
		Branch branch = JitBranch.getBranch();
		//分为两步，第一步修改branch对象的commitkey，第二步把commitkey写入到branch文件中
		branch.resetCommit(commitKey);
	}


    //从现有的index文件中创建indexTree对象
    public TreeMap<String, ObjectNode> buildIndexTree() throws IOException {
		TreeMap<String, ObjectNode> root = new TreeMap<>();
    	Index idxObject = new Index();
    	ArrayList<String[]> idxList = idxObject.getIndexs();
    	
    	for(String[] record : idxList) {
    		String dir = record[2];
    		//String separator = File.separator;
    		List<String> splitedPath = Arrays.asList(dir.split(Utils.SEP));
			TreeMap<String, ObjectNode> cur = root;
    		for(int i = 0; i < splitedPath.size() - 1; i++) {
    			String str = splitedPath.get(i);
    			if(cur.containsKey(str)) {
    				cur = cur.get(str).indexTree;
    			}else {
					TreeMap<String, ObjectNode> t = new TreeMap<>();
    				cur.put(str, new ObjectNode(str, "", t));
    				cur = t;
    			}
    		}
    		String fileName = splitedPath.get(splitedPath.size() - 1);
    		cur.put(fileName, new ObjectNode(fileName, record[1]));
    	}
    	
    	return root;
    }
    
    /**
     * Creates a tree object using the current index. 
     * @throws Exception 
     */
    public Tree buildTree() throws Exception {
		TreeMap<String, ObjectNode> indexTree = buildIndexTree();
		return new Tree(indexTree);
    }

}
