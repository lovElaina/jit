import core.*;
import repo.Repository;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Client {

	/**
	 * Command 'jit init'
	 * @param args
	 * @throws IOException
	 */
	public static void jitInit(String[] args) throws IOException {
		String path = "";
		if(args.length <= 2) { //get default working path
			/**
			 * 项目所在的绝对目录
			 */
			path = new File(".").getCanonicalPath();
			Utils.setWorkDir(path);
			JitInit.init(path);
		}else if(args[2].equals("-help")){ //see help
			System.out.println("usage: jit init [<path>] [-help]\r\n" +
					"\r\n" +
					"jit init [<path>]:	Create an empty jit repository or reinitialize an existing one in the path or your default working directory.");
		}else {
			path = args[2];
			Utils.setWorkDir(path);
			if(!new File(path).isDirectory()) { //if the working path input is illegal
				System.out.println(path + "is not a legal directory. Please init your reposiroty again. See 'jit init -help'.");
			}else {
				JitInit.init(path);
			}
		}
	}

	public static void jitAdd(String[] args) throws IOException {
		System.out.println("请输入.jit所在目录,输入default表示在默认目录");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		if(str.equals("default")){
			Utils.setWorkDir(new File(".").getCanonicalPath());
		}else Utils.setWorkDir(str);

		new Repository(new File(".").getCanonicalPath());
		//String workDir = Repository.getWorkTree();
		//System.out.println(workDir);

		if(args.length <= 2 || args[2].equals("-help")) {
			System.out.println("""
					for example jit add abc.txt""");
		}else {
			for(int i = 2; i < args.length; i++) {
				String fileName = args[i];
				File file = new File(Utils.getWorkDir() + File.separator + fileName);

				try {
					JitAdd.add(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Command 'jit commit'. Commit files from stage to repository.
	 * @param args
	 * @throws Exception
	 */
	public static void jitCommit(String[] args) throws Exception {
		//new Repository(new File(".").getCanonicalPath());
		if(args.length <= 2) {
			JitCommit jitcommit = new JitCommit();
			jitcommit.commit();
		}else if(args[2].equals("-help")){
			System.out.println("usage: jit commit [-help]\r\n" +
					"\r\n" +
					"jit commit: Commit file(s) from stage to repository.");
		}
	}

	/**
	 * Command 'jit remove'. Remove file record(s) from stage.
	 * @param args
	 */
	public static void jitRemove(String[] args) throws IOException {
		//String workDir = Repository.getWorkTree();
		Utils.setWorkDir(new File(".").getCanonicalPath());
		String workDir = Utils.getWorkDir();

		if(args.length <= 2 || args[2].equals("-help")) {
			System.out.println("""
					usage: jit remove <file1> [<file2>...] [-help]\r
					\r
					jit remove <file1> [<file2>...]: Remove file(s) from stage.""");
		} else {
			for(int i = 2; i < args.length; i++) {
				String fileName = args[i];
				File file = new File(workDir + File.separator + fileName);
				try {
					JitRemove.remove(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void jitLog(String[] args) throws Exception {
		Utils.setWorkDir(new File(".").getCanonicalPath());
		String workDir = Utils.getWorkDir();
		if(args.length <= 2 || args[2].equals("-help")) {
			System.out.println("usage:jit log");
		} else {
			JitLog.printLog();
		}
	}
	

	/**
	 * Command 'jit help'.
	 */
	public static void jitHelp(){
		System.out.println("usage: jit [--version] [--help] [-C <path>] [-c name=value]\r\n" +
				"           [--exec-path[=<path>]] [--html-path] [--man-path] [--info-path]\r\n" +
				"           [-p | --paginate | --no-pager] [--no-replace-objects] [--bare]\r\n" +
				"           [--git-dir=<path>] [--work-tree=<path>] [--namespace=<name>]\r\n" +
				"           <command> [<args>]\r\n" +
				"\r\n" +
				"These are common Jit commands used in various situations:\r\n" +
				"\r\n" +
				"start a working area\r\n" +
				"   init       Create an empty Jit repository or reinitialize an existing one\r\n" +
				"\r\n" +
				"work on the current change\r\n" +
				"   add        Add file contents to the index\r\n" +
				"   reset      Reset current HEAD to the specified state\r\n" +
				"   rm         Remove files from the working tree and from the index\r\n" +
				"\r\n" +
				"examine the history and state\r\n" +
				"   log        Show commit logs\r\n" +
				"   status     Show the working tree status\r\n" +
				"\r\n" +
				"grow, mark and tweak your common history\r\n" +
				"   branch     List, create, or delete branches\r\n" +
				"   checkout   Switch branches or restore working tree files\r\n" +
				"   commit     Record changes to the repository\r\n" +
				"   diff       Show changes between commits, commit and working tree, etc\r\n" +
				"   merge      Join two or more development histories together\r\n" +
				"\r\n" +
				"'jit help -a' and 'jit help -g' list available subcommands and some\r\n" +
				"concept guides. See 'jit help <command>' or 'jit help <concept>'\r\n" +
				"to read about a specific subcommand or concept.");
	}
	
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("请输入命令：");
			String str = scanner.nextLine();
			String[] arr = str.split("\\s+");

			if(arr[0].equals("stop")){
				System.out.println("成功停止程序");
				break;
			}
			if (arr.length <= 1 || arr[1].equals("help")) {
				jitHelp();
				System.out.println(arr.length);
			} else {
				if (arr[1].equals("init")) {
					jitInit(arr);
				} else if (arr[1].equals("add")) {
					jitAdd(arr);
				} else if (arr[1].equals("commit")) {
					jitCommit(arr);
				} else if (arr[1].equals("remove")) {
					jitRemove(arr);
				} else if (arr[1].equals("log")){
					jitLog(arr);
				} else {
					System.out.println("jit: " + arr[1] + "is not a git command. See 'git help'.");
				}
			}
		}
/*		if(args.length <= 1 || args[1].equals("help")) {
			jitHelp();
		}else {
			if(args[1].equals("init")) {
				jitInit(args);
			} else if(args[1].equals("add")){
				jitAdd(args);
			} else if(args[1].equals("commit")) {
				jitCommit(args);
			} else{
				System.out.println("jit: " + args[1] + "is not a git command. See 'git help'.");
			}
		}*/
	}
}
