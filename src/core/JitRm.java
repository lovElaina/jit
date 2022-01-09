package core;

import tmp.*;
import utils.FileDeletion;

import java.io.File;
import java.io.IOException;

public class JitRm {
	//在index文件中移除file对应的文件记录
	public static void remove(File file) throws IOException {
		Index idx = new Index();
		idx.deleteItem(file);
		idx.saveIndex();
	}

	//强制删除files，既删除index中对应的记录，又删除本地工作区文件
	public static void removeForce(File file) throws IOException {
		Index idx = new Index();
		idx.deleteItem(file);
		idx.saveIndex();
		FileDeletion.deleteFile(file);
	}
}
