# jit
### A lightweight git application developed using java

### please visit http://aemon.cn/2022/04/12/JIT-%E4%BD%BF%E7%94%A8JAVA%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84Git/ for more information.

Referring to the principle of git implementation, a version control system is implemented in Java and named jit. It can be seen as a simplified version of git. Compared with git, it retains the core feature set. Similar to the original release version of git, **we have not implemented functions such as git pull and git push that require network connection.**

- The project follows a unified programming style as well as an annotation style.
  
- Referring to the git implementation principle and file structure, use key-value object to implement the core storage structure of blob, tree, and commit.
  
- Implemented basic functions such as init, branch, checkout, add, rm, commit, etc. In addition, it also implemented other functions such as reset, log, restore, load, etc., making the entire project more complete and initially available.
  
- We also adopted two running modes, namely: running in command line window mode, and running in the form of a graphical interface, corresponding to two entry functions to meet the needs of users with different usage habits.
  
- At the same time, we also provide a complete help command, so that users can get sufficient and feasible prompt information in the case of typing errors.
  

The Java version that the project depends on is JDK1.8, and intellij idea is used as the development environment. Because it is a lightweight application and the project scale is small, no build tools such as Gradle are used, and no third-party libraries are used (the graphical interface uses the swing framework, which belongs to the built-in window toolkit of java). The final product is packaged by idea , use exe4j to convert from jar to exe executable file, support running windows xp and above versions of the operating system.

**HAVE FUN**
