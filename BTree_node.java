package bTree;

import java.util.ArrayList;
import java.util.List;

public class BTree_node {

	public boolean leaf;// �ж��Ƿ�Ϊ��Ҷ
	public List<Integer> key = new ArrayList<>();// �ؼ���
	public List<BTree_node> children = new ArrayList<>(); // ����
	public BTree_node parent;// ���ڵ�
	public int countKey;// ��¼key���ؼ��֣�������

	public BTree_node() {
		this.key = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	public BTree_node getParent() {
		return parent;
	}

	public int getCountKey() {
		return countKey;
	}

	/***
	 * �ж�ָ���ڵ㣨�Ĺؼ��֣��Ƿ��������ؼ��ָ���Ϊ2t-1������
	 * 
	 * @param node
	 * @return t:������ f:δ��
	 */
	public boolean isFull() {
		if (this.key.size() == (2 * BTree.t - 1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �жϵ�ǰ�ڵ��Ƿ�ΪҶ�ӽڵ�
	 * 
	 * @param node
	 * @return t����Ҷ�ӽڵ㣻 f:����Ҷ�ӽڵ�
	 */
	public boolean isLefe() {
		if (this.leaf) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * �жϵ�ǰ�ڵ��Ƿ�Ϊ���ڵ�
	 * 
	 * @return t:�ǣ� f:����
	 */
	public boolean isRoot() {
		if (this == BTree.root) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getMiddleLocation() {
		int size = this.key.size();
		int middle = size/2+1;
		return middle;
	}
}
