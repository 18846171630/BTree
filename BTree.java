package bTree;

import java.util.Collections;
import java.util.List;

import javax.tools.DocumentationTool.Location;

/***
 * B��
 * 
 * @author LaZY����־һ��
 * 
 *         ���ֹ������⣺
 * 
 *         1.���룺��Ϊ�ݹ������ѭ�������ִ����ݹ��һ��Ҫ�ڵݹ����������break������ѭ����
 *
 *         ���⣡��Ϊwhileѭ���л���һ��forѭ����������forѭ������Ȼ��ѭ���С�Ϊ����������㣬���ӱ�ǣ��ڲ������ݺ󣬸ı��ǵ�ֵ�������в�����������󣬻ָ���ǡ�
 * 
 *         2.��������и��ڵ㺢�ӵ��ƶ������ڵ�λ�ڸ��ڵ��ĩβ��ֻ��Ҫ��ĩβ���ݣ�����Ҫ�ƶ���
 * 
 * 
 *         �����ƣ�
 * 
 *         1.���벿��:�жϵ�ǰ�ڵ����һ��Ҫ���ʵ�λ���Ƿ�δ����������ѡ�����Ҫ���жϵ�ǰ�ڵ��Ƿ�Ϊ���ṹҪ������д�������㣬����⡣
 */
public class BTree {
	public final static int t = 3;// B������С��

	public static BTree_node root;// B���ĸ��ڵ�

	private static boolean sign = false;// ��ǣ�

	/***
	 * B���ĳ�ʼ��
	 */
	public void init() {
		BTree_node node = new BTree_node();
		node.leaf = true;// ��ǰ�ڵ���ΪҶ�ӽڵ�
		node.countKey = 0;
		this.root = node;
	}

	/**
	 * �ж�B���Ƿ�Ϊ������B���Ĺؼ��ָ���Ϊ0����Ϊ������
	 * 
	 * @return t:�ǿ����� f:���ǿ���
	 */
	public boolean isEmpty() {
		if (this.root.countKey == 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * ���һƬҶ�ӽڵ㣨���ܴ����ģ�
	 * 
	 * @return Ҷ��
	 */
	public BTree_node getLeftNode() {
		BTree_node node = new BTree_node();
		node.leaf = true;
		return node;
	}

	/***
	 * ���һ���ڲ�(����Ҷ)�ڵ㣨�´����ģ�
	 * 
	 * @return �ڲ����
	 */
	public BTree_node getRNode() {
		BTree_node node = new BTree_node();
		node.leaf = false;
		return node;
	}

	/***
	 * ��B���в���ؼ���
	 * 
	 * @param k
	 *            Ҫ����Ĺؼ���
	 */
	public void insert(int k) {
		insert(k, this.root);
		sign = false;// ��ɲ����󣬱�ǻָ�
	}

	/***
	 * ��ָ���ڵ��в���ؼ���
	 * 
	 * @param k
	 *            Ҫ����Ĺؼ���
	 * @param node
	 */
	public void insert(int k, BTree_node node) {
		while (!(node.isLefe())) {// ����ֻ����Ҷ�ӽڵ���У�������Ҷ�ӽڵ��һֱ���ҡ�
			// ���϶��·������ڵ�
			if (node.isFull()) {// ������̷������ڵ㣬�ͽ������
				if (node.isRoot()) {// ���ڵ�Ϊ���ڵ�
					BTree_node newRoot = splitRoot(node);// ���Ѹ��ڵ�
					this.root = newRoot;
					insert(k, this.root);// ���½ڵ����²���
					break;
				} else {// ���ڵ�Ϊ�ڲ��ڵ�
					BTree_node parent = splitInternalNode(node);// ���ڵ���Ѻ���ԭ���ĸ��ڵ������²���
					insert(k, parent);
					break;
				}
				// ���²���
			} else {
				int keySize = node.key.size();
				for (int i = 0; i < keySize; i++) {
					Integer key = node.key.get(i);
					if (k < key) {// ��������ݱȵ�ǰ�ؼ���С�����ڹؼ��ֵ�ǰһ���Ӻ�����Ѱ��
						insert(k, node.children.get(i));
						break;
					} else if (k == key) {
						System.out.println("�Ѵ���");
						break;
					}
				}
				if (sign) {// ����������˳�ѭ��������㣩
					break;
				} else {

					if (k > node.key.get(keySize - 1)) {// ��������ݱ����йؼ��ִ������һ��������Ѱ��
						insert(k, node.children.get(keySize));
						break;
					}
					if (sign) {
						break;
					}
				}
			}
		}
		// ��Ҷ�ӽڵ��в���ؼ���
		if (node.isLefe()) {// ���ܽ��˾�ʡ�ԣ���Ϊû�д˾�ݹ�������ִ��
			if (node.isRoot()) {// ����ڵ����
				insertInRoot(k);
			} else {
				insertInLefe(k, node);
			}
			sign = true;// ����������˳�ѭ��������㣩
		}

	}

	/***
	 * ��������д�����ڵ�ķ���
	 * 
	 * @param k
	 *            Ҫ����Ĺؼ���
	 */
	public void insertInRoot(int k) {
		BTree_node r = this.root;
		if (this.isEmpty()) {// ���������ڵ��޹ؼ��֣�
			r.key.add(k);// ֱ�Ӳ���
			r.countKey = 1;// ���ӹؼ�������
		} else if (r.isFull()) {// ����������/ע�⣺��������������ȷ����ٲ��룬������Ӳ�����
			BTree_node newRoot = splitRoot(r);// ���Ѹ��ڵ㣬�������¸�
			this.root = newRoot;// B���ĸ��ڵ�ָ���¸�
			insert(k, this.root);// �Ӹ��ڵ��ٴβ���
		} else {
			r.key.add(k);// ֱ�Ӳ���
			Collections.sort(r.key);// ��������򣨵�����
			r.countKey++;// ���ӹؼ�������//
		}
	}

	/***
	 * ��Ҷ�ӽڵ��в���ؼ���
	 * 
	 * @param k
	 *            Ҫ����Ĺؼ���
	 * @param node
	 *            Ҷ�ӽڵ�
	 */
	public void insertInLefe(int k, BTree_node node) {
		if (node.isFull()) {// ����������/ע�⣺��������������ȷ����ٲ��룬������Ӳ�����
			BTree_node parent = splitlefeNode(node);// ��Ҷ�ӽڵ���ѣ������丸�ڵ����²���
			insert(k, parent);
			// BTree_node newRoot = splitRoot(node);// ���Ѹ��ڵ㣬�������¸�
			// this.root = newRoot;// B���ĸ��ڵ�ָ���¸�
			// insert(k);// �Ӹ��ڵ��ٴβ���
		} else {
			node.key.add(k);// ֱ�Ӳ���
			Collections.sort(node.key);// ��������򣨵�����
			node.countKey++;// ���ӹؼ�������
		}
	}

	/***
	 * ���Ѹ��ڵ㣬�������¸�
	 * 
	 * @param root
	 *            ���ڵ�
	 * @return �µĸ��ڵ�
	 */
	public BTree_node splitRoot(BTree_node root) {
		int location = root.getMiddleLocation();// �ؼ��ֵ�����λ��
		int middleKey = root.key.get(location - 1);// Ҫ�����Ĺؼ���
		BTree_node newNode = makeNewNodeKey(location, root);// ���ɷ��ѽڵ㣬��ֵ���޸Ĺؼ�������
		if (!(root.isLefe())) {
			newNode = makeNewNodeChildren(location, root, newNode);// �ѷֳ��Ķ��Ӹ��������ɵĽڵ�
			root = removeChildren(location, root);// ɾ�����ѳ��Ķ���
		}
		root = removeKeys(location, root);// ɾ�����ѳ��Ĺؼ��ֲ��޸Ĺؼ�������
		BTree_node newRoot = getRNode();// �����ɵĸ��ڵ�
		newRoot.children.add(root);// �µĸ��ڵ㣨���ڵ㣩�Ķ���ָ��ָ����������
		newRoot.children.add(newNode);
		root.parent = newRoot;// ����ָ��ĸ���ָ��ָ���¸���
		newNode.parent = newRoot;
		newRoot.key.add(middleKey);// Ϊ�µĸ��ڵ㸳�ؼ���
		newRoot.countKey = 1;// �޸��µĹؼ��ֵ�����
		return newRoot;
	}

	/***
	 * ������ͨҶ�ӽڵ�
	 * 
	 * @param root
	 *            �����ѵĽڵ�
	 * @return �����ѽڵ�ĸ���
	 */
	public BTree_node splitlefeNode(BTree_node root) {
		BTree_node parent = root.getParent();// ��ǰ�ڵ㣨Ҫ�����ѵĽڵ㣩�ĸ��ڵ�
		int nodeIndex = getChildLocation(root);// ��ǰ�ڵ����丸�ڵ�ĵڼ������ӣ�������
		int location = root.getMiddleLocation();// �ؼ��ֵ�����λ��
		int middleKey = root.key.get(location - 1);// Ҫ�����Ĺؼ���
		BTree_node newNode = makeNewNodeKey(location, root);// ���ɷ��ѽڵ㣬��ֵ���޸Ĺؼ�������
		root = removeKeys(location, root);// ɾ�����ѳ��Ĺؼ��ֲ��޸Ĺؼ�������
		parent = moveChildren(nodeIndex, parent);// ���ڵ�ĺ�������ƶ�
		parent.children.set(nodeIndex + 1, newNode);// ���ڵ�ĺ���ָ��ָ���·��ѳ��Ľڵ�
		newNode.parent = parent;// ���ѳ��Ľڵ��˫��ָ��ָ���µĸ��ڵ�
		parent.key.add(middleKey);// ���ڵ��в��������Ĺؼ���
		Collections.sort(parent.key);// Ϊ���ڵ�����Ĺؼ�������
		parent.countKey = 1;// �޸ĸ��ڵ�Ĺؼ��ֵ�����
		return parent;
	}

	/***
	 * �����ڲ����
	 * 
	 * @param root
	 *            Ҫ�����ѵĽڵ�
	 * @return �����ѽڵ�ĸ���
	 */
	public BTree_node splitInternalNode(BTree_node root) {
		BTree_node parent = root.getParent();// ��ǰ�ڵ㣨Ҫ�����ѵĽڵ㣩�ĸ��ڵ�
		int nodeIndex = getChildLocation(root);// ��ǰ�ڵ����丸�ڵ�ĵڼ������ӣ�������
		int location = root.getMiddleLocation();// �ؼ��ֵ�����λ��
		int middleKey = root.key.get(location - 1);// Ҫ�����Ĺؼ���
		BTree_node newNode = makeNewNodeKey(location, root);// ���ɷ��ѽڵ㣬��ֵ���޸Ĺؼ�������

		newNode = makeNewNodeChildren(location, root, newNode);// �ѷֳ��Ķ��Ӹ��������ɵĽڵ�
		root = removeChildren(location, root);// ɾ�����ѳ��Ķ���

		root = removeKeys(location, root);// ɾ�����ѳ��Ĺؼ��ֲ��޸Ĺؼ�������
		parent = moveChildren(nodeIndex, parent);// ���ڵ�ĺ�������ƶ�
		parent.children.set(nodeIndex + 1, newNode);// ���ڵ�ĺ���ָ��ָ���·��ѳ��Ľڵ�
		newNode.parent = parent;// ���ѳ��Ľڵ��˫��ָ��ָ���µĸ��ڵ�

		parent.key.add(middleKey);// ���ڵ��в��������Ĺؼ���
		Collections.sort(parent.key);// Ϊ���ڵ�����Ĺؼ�������
		parent.countKey = 1;// �޸ĸ��ڵ�Ĺؼ��ֵ�����
		return parent;
	}

	/**
	 * ��ǰ�ڵ����丸�ڵ�ĵڼ������ӣ�������
	 * 
	 * @param node
	 *            ��ǰ�ڵ�
	 * @return �ڸ��ڵ��е�����
	 */
	public int getChildLocation(BTree_node node) {
		int index = 0;
		BTree_node parent = node.getParent();
		for (int i = 0; i < parent.children.size(); i++) {
			if (parent.children.get(i) == node) {
				index = i;
			} else {
			}
		}
		return index;
	}

	/***
	 * �ƶ������ѽڵ����ĺ��ӣ�����ƶ���
	 * 
	 * @param index
	 *            �����ѽڵ������е�����
	 * @param parent
	 *            �����ѽڵ�ĸ��ڵ�
	 * @return ���ڵ�
	 */
	public BTree_node moveChildren(int index, BTree_node parent) {
		int oldsize = parent.children.size();
		if (index != oldsize - 1) {// �����ѵĽڵ㲻��ĩβ
			BTree_node lastchild = parent.children.get(oldsize - 1);
			parent.children.add(lastchild);// x
			for (int i = oldsize - 1; i >= index; i--) {
				BTree_node c = parent.children.get(i);
				parent.children.set(i + 1, c);
			}
		} else {
			parent.children.add(parent);// ��ĩβ���ݣ�1����λ��
		}
		return parent;
	}

	/***
	 * �����·��ѳ��Ľڵ㣬�������ѳ��Ĺؼ��ָ����½ڵ㣬�޸Ĺؼ���������
	 * 
	 * @param middle
	 *            Ҫ�����Ĺؼ���
	 * @param node
	 *            �����ѵĽڵ�
	 * @return ���ѳ��Ľڵ�
	 */
	public BTree_node makeNewNodeKey(int middle, BTree_node node) {
		int size = node.key.size();
		int newKeyCount = size - middle;// Ҫ�����Ĺؼ��ֺ���Ĺؼ�������
		BTree_node leftNode = getLeftNode();// �����µı����ѳ��Ľڵ㣨һ����Ҷ�ӽڵ㣩
		for (int i = middle - 1 + 1; i < size; i++) {// (middle-1+1���������е�Ԫ�ش�0��ʼ��middle-1��Ҫ�����Ĺؼ��֣�middle-1+1��Ҫ�����ؼ��ֺ�һ���ؼ���
			int key = node.key.get(i);// ��Ҫ�����Ĺؼ��ֺ���Ĺؼ��ָ������ѳ��Ľڵ㣨�մ����ģ�
			leftNode.key.add(key);
		}
		leftNode.countKey = newKeyCount;
		return leftNode;
	}

	/***
	 * Ϊ�·��ѳ��Ľڵ㸳�躢��
	 * 
	 * @param location
	 *            ����ǰ�Ľڵ���Ҫ�����ؼ��ֵ�λ��
	 * @param node
	 *            �����ѵĽڵ�
	 * @param newNode
	 *            ���ѳ����½ڵ�
	 * @return ���ѳ����½ڵ�
	 */
	public BTree_node makeNewNodeChildren(int location, BTree_node node, BTree_node newNode) {
		int size = node.children.size();
		// int newChildrenCount = size - location;// ���ֳ��Ķ��ӵ�����
		for (int i = location - 1 + 1; i < size; i++) {
			BTree_node child = node.children.get(i);
			newNode.children.add(child);
			child.parent = newNode;
		}
		return newNode;
	}

	/***
	 * ɾ������ѵĽڵ�Ĺؼ��ֲ��޸Ĺؼ�������
	 * 
	 * @param middle
	 *            Ҫ�����Ĺؼ���
	 * @param node
	 *            �����ѵĽڵ�
	 * @return �����ѵĽڵ�
	 */
	public BTree_node removeKeys(int middle, BTree_node node) {
		int size = node.key.size();
		int removeCount = size - middle + 1;// Ҫ�����Ĺؼ��ּ������Ĺؼ�������
		int deleteLocation = middle - 1;// Ҫɾ���ؼ��ֵ�����
		for (int i = middle - 1; i < size; i++) {// middle-1:Ҫ�����Ĺؼ���
			node.key.remove(deleteLocation);
		}
		node.countKey = size - removeCount;// ������Ĺؼ�������Ϊ��ԭ������-ɾ��������
		return node;
	}

	public BTree_node removeChildren(int location, BTree_node node) {
		int size = node.children.size();
		int deleteLocation = location;// Ҫɾ�����ӵ�����
		for (int i = location; i < size; i++) {
			node.children.remove(deleteLocation);
		}
		return node;
	}

	/***
	 * ����ָ���ؼ��֣��ڹؼ������еڼ���λ�ã�����������
	 * 
	 * @param node
	 *            �ؼ������ڵĽڵ�
	 * @param key
	 *            ָ���Ĺؼ���
	 * @return �ؼ��ֵ�λ��
	 */
	public int getKeyLocation(BTree_node node, int key) {
		int location;
		int index = 0;
		while (node.key.get(index) != key) {
			index++;
		}
		return location = index + 1;
	}
}
