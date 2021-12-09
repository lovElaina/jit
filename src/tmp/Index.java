package tmp;

import utils.*;
import gitobject.Blob;
import repo.Repository;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
//import java.util.LinkedList;

public class Index {
	//ArrayList中的每一项表示一个记录集，描述一个文件的相关信息，例如：
	//100644 6b1c46cd9dcf9950099986ba03e5bfa77ffc3a6c <timestmp>  sq/ss.txt
	private ArrayList<String[]> index = new ArrayList<>();
	//.jit所在的文件夹路径
	//private final String path = Repository.getGitDir();
	private final String path = Utils.getJitDir();
	
    //如果index文件存在，读取index文件中的数据项，如果index文件不存在，创建index文件
	public Index() throws IOException {
		if(!new File(path + File.separator + "index").exists()) {
			//System.out.println("错误附近的path"+path);
			//在.jit文件夹内，创建名为index的文件
			FileCreation.createFile(path, "index", "");
		}else {
			//即表示已经存在，加载已经存在的index文件

			index = getExistData();
		}	
	}
	//get方法，返回创建完成的index对象
	public ArrayList<String[]> getIndexs() {
		return index;
	}
	
	//反序列化，将index文件中的每条数据读入ArrayList中。
	public ArrayList<String[]> getExistData() throws IOException {
		if(new File(path + File.separator + "index").exists()) {
			File indexFile = new File(path + File.separator + "index");
			FileInputStream is = new FileInputStream(indexFile);
			//以InputStreamReader的对象作为参数，创建BufferedReader的对象，
			//这样br这个对象就代表了一个具有缓冲功能的输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			//每一行的信息按空格分离存入str[]，然后再存入list中
			ArrayList<String[]> list =  new ArrayList<>();
	        try{
	            String line = br.readLine();
	            while (line != null){
	            	//split返回String数组，它根据匹配给定的正则表达式来拆分字符串。
	            	String[] arr = line.split(" ");
	                list.add(arr);
	                line = br.readLine();
	            }
	        }catch (IOException e){
	            e.printStackTrace();
	        }finally {
	            try {
	                br.close();
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return list;
		}else {
			throw new IOException("Index file doesn't exists.");
		}
	}
	
	//按照一行一行的形式保存index信息到.jit/index
	public void outputIndex() throws IOException {
		if(new File(path + File.separator + "index").exists()) {
			File indexFile = new File(path + File.separator + "index");
	        FileWriter fw = new FileWriter(indexFile);
			for (String[] tmp : index) {
				StringBuffer stringBuffer = new StringBuffer();
				for (String s : tmp) {
					stringBuffer.append(s).append(" ");
				}
				fw.write(stringBuffer.toString().substring(0, stringBuffer.length() - 1));
				fw.write("\n");
			}
	        fw.flush();
	        fw.close();
		}
	}
	
	/**
	 * Insert a file record into indexList.
	 * @param file
	 * @throws Exception
	 */
	public void addIndexs(File file) throws Exception {
		//Generate a new record
		String[] items = new String[4];
		Blob blob = new Blob(file);
		//String workDir = Repository.getWorkTree();
		String workDir = Utils.getWorkDir();
		String fileName = file.getPath().substring(workDir.length() + 1);
		

		String timeStr = String.valueOf(file.lastModified());
		//100644 6b1c46cd9dcf9950099986ba03e5bfa77ffc3a6c <timestmp>  sq/ss.txt
		items[0] = "10644";
		items[1] = blob.getKey();
			//System.out.println(blob.getKey());
		items[2] = fileName;
			//System.out.println(fileName);
		items[3] = timeStr;
		index.add(items);

		blob.compressWrite();
	}	
	
    //
	public int inIndex(File file) {
		String filePath = file.getPath().substring(Utils.getWorkDir().length() + 1);
		for(int i = 0; i < index.size(); i++) {
			if(index.get(i)[2].equals(filePath)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Remove a file record from the index
	 * @param file
	 */
	public void deleteItem(File file) {
		int idx = inIndex(file);
		if(idx != -1) {
			index.remove(idx);
		}
	}


	/**
	 * Clear the stage.
	 */
	public void clear() {
		File file = new File(path + File.separator + "index");
		FileDeletion.deleteContent(file);
	}

}
