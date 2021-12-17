package core;

import tmp.*;

import java.io.File;
import java.io.IOException;

public class JitRemove {
	//在index文件中移除file对应的文件记录
	public static void remove(File file) throws IOException {
		Index idx = new Index();
		idx.deleteItem(file);
		idx.outputIndex();
	}
}
