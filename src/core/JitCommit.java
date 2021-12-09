package core;

import gitobject.*;

import tmp.*;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JitCommit {
    /**
     * Creating commit and write it to repository.
     * @throws Exception
     */
    public void commit() throws Exception {
        Tree tree = buildTree();
        String author = System.getProperty("user.name") + " " + String.valueOf(new Date().getTime());
        String committer = System.getProperty("user.name");

        System.out.println("Please enter the commit message: ");
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();

        Commit commit = new Commit(tree, author, committer, message);
		Index index = new Index();
		//提交完成后，删除index文件
		index.clear();
        //updateBranch(commit);
        //sc.close();
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
