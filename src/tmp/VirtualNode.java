package tmp;

import java.util.ArrayList;

public class VirtualNode {

        //节点值
        String val;
        //k叉树的子树列表，长度没有限制
        ArrayList<VirtualNode> childlist = new ArrayList<>();
        //构造方法，初始化节点值
        VirtualNode(String s) {
            val = s;
        }
        //设置某一节点的子节点值，实则分为两步
        // 1、用传入的x值初始化子节点
        // 2、把子节点添加到父节点的childlist列表中
        public VirtualNode setChild(String x) {
            this.childlist.add(new VirtualNode(x));
            return this;
        }
        //重载方法，传入的是一个节点，将它添加到父节点的childlist列表中
        public VirtualNode setChild(VirtualNode k) {
            this.childlist.add(k);
            return this;
        }
    }

