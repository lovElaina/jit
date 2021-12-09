package core;

import tmp.Index;

import java.io.File;
import java.io.IOException;

public class JitAdd {
    public static void add(File file) throws Exception {
    	if(!file.exists()) {
    		throw new IOException("文件不存在");
    	}
    	
    	Index idx = new Index();
    	if(file.isFile()){
    		//表示已经在index类找到了同名的记录
			if(idx.inIndex(file) != (-1)) {
				System.out.println("找到了同名记录，现在我们更新吧");
				idx.deleteItem(file);
			}
			idx.addIndexs(file);
			idx.outputIndex();
		}
    	else if(file.isDirectory()){
			File[] fs = file.listFiles();
			for(int i = 0; i < fs.length; i++){
				add(fs[i]);
			}
		}
    }
}
