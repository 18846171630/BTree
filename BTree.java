package bTree;

import java.util.Collections;
import java.util.List;

import javax.tools.DocumentationTool.Location;

/***
 * B��
 * 
 * ���ֹ������⣺
 * 
 * 1.���룺��Ϊ�ݹ������ѭ�������ִ����ݹ��һ��Ҫ�ڵݹ����������break������ѭ����
 *
 * ���⣡��Ϊwhileѭ���л���һ��forѭ����������forѭ������Ȼ��ѭ���С�Ϊ����������㣬���ӱ�ǣ��ڲ������ݺ󣬸ı��ǵ�ֵ�������в�����������󣬻ָ���ǡ�
 * 
 * 2.��������и��ڵ㺢�ӵ��ƶ������ڵ�λ�ڸ��ڵ��ĩβ��ֻ��Ҫ��ĩβ���ݣ�����Ҫ�ƶ���
 * 
 * 3.���ѣ�������ѵĽڵ��к��ӣ���ô�´����Ľڵ�Ӧ��Ϊ��Ҷ�ӽڵ㡣
 * 
 * 4.ɾ����ɾ���ڲ�����ǰ��������������ӽڵ�������һ���ؼ������㹻�ģ�����Ϊ��ǰ�ڵ�Ĺؼ����滻Ϊ����ڵ�Ĺؼ��֣��ݹ�ɾ����������ڵ�Ĺؼ��֣����ԣ��ݹ�Ľڵ㲻Ӧ���ǵ�ǰ�ڵ㣬Ӧ���ǣ���ǰ�ڵ�Ķ��ӽڵ�
 * 
 * 5.ɾ����ɾ���ڲ����ĵ�����������ϲ��������ؼ��ֵĶ��ӽڵ���Ҷ�ӽڵ㣬����Ҫ�ٴ�����ӵĶ������ˣ��ᱨ��ָ�롣
 * 
 * 6.������ɾ��ʱ�������ӽڵ�Ĺؼ������㹻���ҳ����ӽڵ�Ϊ����������ǰ������
 * 
 * �����ƣ�
 * 
 * 1.���벿��:�жϵ�ǰ�ڵ����һ��Ҫ���ʵ�λ���Ƿ�δ����������ѡ�����Ҫ���жϵ�ǰ�ڵ��Ƿ�Ϊ���ṹҪ������д�������㣬����⡣
 * 
 * 2.ɾ�����֣��ɶ��Խϵ�
 * 
 * @author LaZY����־һ��
 * 
 * 
 */
public class BTree {
	public static BTree_node root;// B���ĸ��ڵ�

	public final static int t = 2;// B������С��

	private static boolean sign = false;// ��ǣ�

	private static final Disk DISK = new Disk();// ����

	/***
	 * B���ĳ�ʼ��
	 */
	public void init() {
		BTree_node node = new BTree_node();
		node.leaf = true;// ��ǰ�ڵ���ΪҶ�ӽڵ�
		node.countKey = 0;
		this.root = node;
		// HED ����д
		this.DISK.WRITE(this.root);
	}

	/**
	 * �ж�B���Ƿ�Ϊ������B���Ĺؼ��ָ���Ϊ0����Ϊ������
	 * 
	 * @return t:�ǿ����� f:���ǿ���
	 */
	public boolean isEmpty() {
		if (this.root.key.size() == 0) {
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

			// HED ����д
			this.DISK.WRITE(r);

		} else if (r.isFull()) {// ����������/ע�⣺��������������ȷ����ٲ��룬������Ӳ�����
			BTree_node newRoot = splitRoot(r);// ���Ѹ��ڵ㣬�������¸�
			this.root = newRoot;// B���ĸ��ڵ�ָ���¸�
			insert(k, this.root);// �Ӹ��ڵ��ٴβ���
		} else {
			r.key.add(k);// ֱ�Ӳ���
			Collections.sort(r.key);// ��������򣨵�����
			r.countKey++;// ���ӹؼ�������//

			// HED ����д
			this.DISK.WRITE(r);

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

			// HED ����д
			this.DISK.WRITE(node);

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

		// HED ����д
		this.DISK.WRITE(root);
		this.DISK.WRITE(newNode);
		this.DISK.WRITE(newRoot);

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

		// HED ����д
		this.DISK.WRITE(root);
		this.DISK.WRITE(parent);
		this.DISK.WRITE(newNode);

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

		// HED ����д
		this.DISK.WRITE(root);
		this.DISK.WRITE(parent);
		this.DISK.WRITE(newNode);

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
		BTree_node newNode;
		if (node.children.size() != 0) {
			newNode = getRNode();// ����к��ӣ��򴴽����Ƿ�Ҷ�ӽڵ�
		} else {
			newNode = getLeftNode();// �����µı����ѳ��Ľڵ㣨һ����Ҷ�ӽڵ㣩
		}
		for (int i = middle - 1 + 1; i < size; i++) {// (middle-1+1���������е�Ԫ�ش�0��ʼ��middle-1��Ҫ�����Ĺؼ��֣�middle-1+1��Ҫ�����ؼ��ֺ�һ���ؼ���
			int key = node.key.get(i);// ��Ҫ�����Ĺؼ��ֺ���Ĺؼ��ָ������ѳ��Ľڵ㣨�մ����ģ�
			newNode.key.add(key);
		}
		newNode.countKey = newKeyCount;
		return newNode;
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

	public void delete(int k) {
		if (isEmpty()) {
			System.out.println("����ʵ��ǿ���");// ����������ִ��ɾ������
		} else {
			delete(k, this.root);
		}
	}

	private void delete(int k, BTree_node node) {
		int keySize = node.key.size();
		for (int i = 0; i < keySize; i++) {// �ӽڵ��һ���ؼ��ֿ�ʼ����
			Integer key = node.key.get(i);
			if (k < key) {// Ҫɾ���Ĺؼ��ֱȵ�ǰ�ؼ���С�����ڹؼ��ֵ�ǰһ���ӽڵ㣨���ӣ���Ѱ��

				BTree_node beforeChild = node.children.get(i);
				int beforeSize = beforeChild.key.size();
				if (enoughKeys(beforeSize)) {// �Թؼ����㹻�Ľڵ㣬��������
					delete(k, node.children.get(i));
					break;// ִ����������˳�ѭ��
				} else {

					// �Թؼ��ֽ��ٵĽڵ㣬���е�����
					BTree_node af_befChild = node.children.get(i + 1);// Ҫɾ���Ĺؼ��������������ڵ�ĺ�һ���ڵ�
					int size = af_befChild.key.size();
					if (enoughKeys(size)) {
						node = del_handleBy_aft(node, key, i, beforeChild, af_befChild);
						delete(k, node);
					} else if (!(enoughKeys(size))) {// �ؼ��ֲ���
						node = del_mergeBy_aft(node, key, i, beforeChild, af_befChild);
						delete(k, node);
					}
					// else if(!(enoughKeys(size))&&node.isRoot()){//�ؼ��ֲ��㣬���Ǹ��ڵ�
					//
					// }

				}

			} else if (k == key) {// �ҵ�Ҫɾ���Ľڵ�

				// ɾ���ؼ���

				if (node.isLefe()) {
					if (node.isRoot() && node.key.size() == 1) {
						System.out.println("���ڵ�ֻ��һ���ڵ㣬����ɾ��");
					} else {
						node.key.remove(i);// ��Ҷ�ӽڵ㣬ֱ��ɾ�������µĹؼ���
						node.countKey--;
						// HED �޸ı��
						this.sign = true;
					}
				} else {// ���ڲ����
					BTree_node beforeChild = node.children.get(i);
					BTree_node afterChild = node.children.get(i + 1);
					int beforeKeyCount = beforeChild.key.size();
					int afterKeyCount = afterChild.key.size();
					/*
					 * Ҫɾ���ؼ��ֵ�ǰһ���ڵ�c�Ĺؼ�������������t��
					 * 
					 * ��c��ĩβ�ؼ����滻Ҫɾ���Ĺؼ���
					 * 
					 * ��Ȼ�ӵ�ǰ�ڵ㿪ʼ �ݹ�ɾ��c
					 *
					 */
					if (enoughKeys(beforeKeyCount)) {
						int size = beforeChild.key.size();

						Integer lastKey = lefkey(beforeChild);
						// Integer lastKey = beforeChild.key.get(size - 1);//
						// �ؼ���ǰ�溢�ӵ����һ���ؼ���
						node.key.set(i, lastKey);
						delete(lastKey, beforeChild);
					}
					/*
					 * Ҫɾ���ؼ��ֵĺ�һ���ڵ�c�Ĺؼ�������������t��
					 * 
					 * ��c���׹ؼ����滻Ҫɾ���Ĺؼ���
					 * 
					 * ��Ȼ�ӵ�ǰ�ڵ㿪ʼ �ݹ�ɾ��c
					 *
					 */
					else if (!(enoughKeys(beforeKeyCount)) && enoughKeys(afterKeyCount)) {
						int size = afterChild.key.size();

						Integer firstKey = rigkey(afterChild);
						// Integer firstKey = afterChild.key.get(0);//
						// �ؼ��ֺ��溢�ӵĵ�һ���ؼ���
						node.key.set(i, firstKey);
						delete(firstKey, afterChild);// �ݹ�ɾ�����ӽڵ�Ĺؼ���
					}
					/*
					 * ���ڵ�ֻ��һ�ؼ���
					 * 
					 * �ϲ����ø��ڵ�ָ��ϲ���Ľڵ�
					 */

					else if (node.isRoot() && node.key.size() == 1 && !(enoughKeys(beforeKeyCount))
							&& !(enoughKeys(afterKeyCount))) {
						beforeChild.key.add(k);// Ҫɾ���Ĺؼ��֣����뵽ǰһ���ڵ���
						for (int j = 0; j < afterChild.key.size(); j++) {// ��һ���ڵ�����йؼ��ֱ��浽ǰһ���ڵ���
							Integer keyy = afterChild.key.get(j);
							// HED �����Ĳ��Ƕ�����
							beforeChild.key.add(keyy);
						}
						if (beforeChild.isLefe()) {// �����ӽڵ���Ҷ�ӽڵ㣬����Ҫ������ӵĶ�����

						} else {
							for (int j = 0; j < afterChild.children.size(); j++) {// ��һ���ڵ�����ж��ӱ��浽ǰһ���ڵ���
								BTree_node child = afterChild.children.get(j);
								beforeChild.children.add(child);
								child.parent = beforeChild;
							}
						}
						beforeChild.countKey += (1 + afterChild.getCountKey());// �޸�ǰһ�����ӵĹؼ��ֵ�����
						this.root = beforeChild;
						delete(k, this.root);
					}
					/*
					 * Ҫɾ���ؼ��ֵ�ǰ��ڵ�Ĺؼ��ֶ�С��t
					 * 
					 * ��Ҫɾ���Ĺؼ��֡�ǰ��ڵ㡢����ڵ�ϲ�
					 * 
					 */
					else if (!(enoughKeys(beforeKeyCount)) && !(enoughKeys(afterKeyCount))) {
						// HED �����Ĳ��Ƕ�����
						beforeChild.key.add(k);// Ҫɾ���Ĺؼ��֣����뵽ǰһ���ڵ���
						for (int j = 0; j < afterChild.key.size(); j++) {// ��һ���ڵ�����йؼ��ֱ��浽ǰһ���ڵ���
							Integer keyy = afterChild.key.get(j);
							// HED �����Ĳ��Ƕ�����
							beforeChild.key.add(keyy);
						}
						if (beforeChild.isLefe()) {// �����ӽڵ���Ҷ�ӽڵ㣬����Ҫ������ӵĶ�����

						} else {
							for (int j = 0; j < afterChild.children.size(); j++) {// ��һ���ڵ�����ж��ӱ��浽ǰһ���ڵ���
								BTree_node child = afterChild.children.get(j);
								beforeChild.children.add(child);
								child.parent = beforeChild;
							}
						}
						beforeChild.countKey += (1 + afterChild.getCountKey());// �޸�ǰһ�����ӵĹؼ��ֵ�����
						node.key.remove(i);// �ӽڵ����ɾ��Ҫɾ���Ĺؼ���
						node.countKey--;// �޸Ľڵ�Ĺؼ�������
						node.children.remove(i + 1);// ɾ��Ҫɾ���ؼ��ֺ���Ķ��ӽڵ�

						// �޸ĺ�Ľڵ㸳ֵ��ԭ�ڵ㡣��ʹ�õݹ��ԭ��1.�״�2.�����Ժ������ѭ��������ִ��ʣ�µ�����
						// BTree_node parent = node.parent;
						// int index = getChildLocation(node);//
						// �ڵ����丸�ڵ�ĺ����е�λ�ã�������
						// parent.children.set(index, node);
						delete(k, beforeChild);
					}

				}
				break;
			}
			break;
		}
		// HED ��Ҫ���˳�ѭ���󲻽�������Ĵ���
		if (this.sign) {

		} else {
			Integer key = node.key.get(keySize - 1);
			if (k > keySize) {// ��������ݱ����йؼ��ִ������һ��������Ѱ��

				BTree_node afterChild = node.children.get(keySize);
				int afterSize = afterChild.key.size();
				if (enoughKeys(afterSize)) {// �Թؼ����㹻�Ľڵ㣬��������
					delete(k, node.children.get(keySize));
				} else {

					// �Թؼ��ֽ��ٵĽڵ㣬���е�����
					BTree_node bef_aftChild = node.children.get(keySize - 1);// Ҫɾ���Ĺؼ��������������ڵ��ǰһ���ڵ�
					int size = bef_aftChild.key.size();
					if (enoughKeys(size)) {
						node = del_handleBy_bef(node, key, keySize - 1, afterChild, bef_aftChild);
						delete(k, node);
					} else if (!(enoughKeys(size))) {// �ؼ��ֲ���
						node = del_mergeBy_bef(node, key, keySize - 1, afterChild, bef_aftChild);
						delete(k, node);
					}

				}

			}
		}
	}

	/***
	 * �ڵ��еĹؼ��ָ���>=t��������t�����ؼ���
	 * 
	 * @param quentity
	 *            �ڵ��еĹؼ�������
	 * @return t:�㹻 f������
	 */
	private boolean enoughKeys(int quentity) {
		if (quentity == this.t || quentity > this.t) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * ���²��ҹ����У�Ҫɾ���Ĺؼ��֣��ڵ�ǰ�ؼ��ֵ�ǰһ���ڵ�������У�Ȼ�����˽ڵ�Ĺؼ��������㹻������ǰһ���ڵ�ĺ�һ���ڵ���е���
	 * 
	 * @param par
	 *            ��ǰ�ڵ�
	 * @param k
	 *            ��ǰ�ؼ���
	 * @param i
	 *            �ؼ��ֵ���������
	 * @param chil
	 *            ɾ���ؼ������ڵĽڵ�
	 * @param aft_chil
	 *            ɾ���ؼ������ڵĽڵ�ĺ�һ���ڵ�
	 * @return ���ص�����ĵ�ǰ�ڵ�
	 */
	private BTree_node del_handleBy_aft(BTree_node par, int k, int i, BTree_node chil, BTree_node aft_chil) {// XXX
																												// �������Ż�
		chil.key.add(k);// ���ڵ�Ĺؼ�����ӵ����ӽڵ���
		chil.countKey++;
		par.key.remove(i);// �Ӹ��ڵ�ɾ�����Ƴ��Ĺؼ���
		par.countKey--;
		Integer key = aft_chil.key.get(0);// ���ӽڵ�ĺ�һ���ڵ���׹ؼ��ֲ��뵽���ڵ�
		par.key.add(key);
		par.countKey++;
		Collections.sort(par.key);
		aft_chil.key.remove(0);// ���Ƴ��ĺ�һ�����ӽڵ�Ĺؼ���ɾ��
		aft_chil.countKey--;
		if (chil.isLefe()) {

		} else {// ���ӽڵ㲻��Ҷ�ӽڵ㣬������ӽڵ�Ķ���
			BTree_node child = aft_chil.children.get(0);// ��һ�����ӽڵ�ĵ�һ��������ӵ����ӽڵ���
			chil.children.add(child);
			aft_chil.children.remove(0);
		}
		return par;
	}

	/***
	 * ���²��ҹ����У�Ҫɾ���Ĺؼ��֣��ڵ�ǰ�ؼ��ֵĺ�һ���ڵ�������У�Ȼ�����˽ڵ�Ĺؼ��������㹻�����ݺ�һ���ڵ��ǰһ���ڵ���е���
	 * 
	 * @param par
	 *            ��ǰ�ڵ�
	 * @param k
	 *            ��ǰ�ؼ���
	 * @param i
	 *            �ؼ��ֵ���������
	 * @param chil
	 *            ɾ���ؼ������ڵĽڵ�
	 * @param bef_chil
	 *            ɾ���ؼ������ڵĽڵ��ǰһ���ڵ�
	 * @return ���ص�����ĵ�ǰ�ڵ�
	 */
	private BTree_node del_handleBy_bef(BTree_node par, int k, int i, BTree_node chil, BTree_node bef_chil) {// XXX
																												// �������Ż�
		chil.key.add(k);// ���ڵ�Ĺؼ�����ӵ����ӽڵ���
		Collections.sort(chil.key);
		chil.countKey++;
		par.key.remove(i);// �Ӹ��ڵ�ɾ�����Ƴ��Ĺؼ���
		par.countKey--;
		int size = bef_chil.key.size();
		Integer key = bef_chil.key.get(size - 1);// ���ӽڵ��ǰһ���ڵ��ĩβ�ؼ��ֲ��뵽���ڵ�
		par.key.add(key);
		Collections.sort(par.key);
		par.countKey++;
		bef_chil.key.remove(size - 1);// ���Ƴ���ǰһ�����ӽڵ�Ĺؼ���ɾ��
		bef_chil.countKey--;
		if (chil.isLefe()) {

		} else {// ���ӽڵ㲻��Ҷ�ӽڵ㣬������ӽڵ�Ķ���
			BTree_node child = bef_chil.children.get(bef_chil.children.size() - 1);// ǰһ�����ӽڵ�ĵ����һ��������ӵ����ӽڵ���
			chil.children.add(child);// ������,����
			for (int j = chil.children.size() - 1; j >= 0; j--) {// �ӵ�һ��Ԫ������ƶ�
				BTree_node c = chil.children.get(j - 1);
				chil.children.set(j, c);
			}
			chil.children.set(0, child);// ������
			bef_chil.children.remove(bef_chil.children.size() - 1);
		}
		return par;
	}

	/***
	 * ���ڵ�ؼ��ֵ��Ӻ��ӵ��ұ��ֵܵĹؼ���Ҳ�����������ڵ�Ĵ˹ؼ��֣����ӣ��ͺ��ӵ����ֵܺϲ�,�ϲ���������
	 * 
	 * @param par
	 *            ���ڵ㣨��ǰ�ڵ㣩
	 * @param k
	 *            ���ڵ�Ĺؼ���
	 * @param i
	 *            �ؼ���λ��
	 * @param chil
	 *            �ؼ���ǰ���Ӻ���
	 * @param aft_chil
	 *            ���ӵ����ֵ�
	 * @return �޸ĺ�ĸ��ڵ�
	 */
	private BTree_node del_mergeBy_aft(BTree_node par, int k, int i, BTree_node chil, BTree_node aft_chil) {
		chil.key.add(k);// ��Ӹ��ڵ�Ĺؼ���
		Collections.sort(chil.key);
		chil.countKey++;
		par.key.remove(i);// ���ڵ�Ĺؼ����Ƴ�
		par.countKey--;
		// ��ʱ���ڵ��л�����һ������
		List<Integer> keys = aft_chil.key;
		List<BTree_node> children = aft_chil.children;
		chil.key.addAll(keys);// ������ֵܵ����йؼ���
		Collections.sort(chil.key);
		chil.countKey += keys.size();
		if (chil.isLefe()) {

		} else {
			for (int j = 0; j < children.size(); j++) {// ������ֵܵ����к���
				BTree_node child = children.get(j);
				chil.children.add(child);
				child.parent = chil;
			}
		}
		par.children.remove(i + 1);// �Ӹ��ڵ���ɾ�����ֵ�
		if (par.isRoot()) {
			this.root = chil;
			chil.parent = null;
			return this.root;
		} else {
			return par;
		}
	}

	/***
	 * ���ڵ�ؼ��ֺ�����Ӻ��ӵ�����ֵܵĹؼ���Ҳ�����������ڵ�Ĵ˹ؼ��֣����ӣ��ͺ��ӵ����ֵܺϲ�
	 * 
	 * @param par
	 *            ���ڵ㣨��ǰ�ڵ㣩
	 * @param k
	 *            ���ڵ�Ĺؼ���
	 * @param i
	 *            �ؼ���λ��
	 * @param chil
	 *            �ؼ��ֺ���Ӻ���
	 * @param aft_chil
	 *            ���ӵ����ֵ�
	 * @return �޸ĺ�ĸ��ڵ�
	 */
	private BTree_node del_mergeBy_bef(BTree_node par, int k, int i, BTree_node chil, BTree_node bef_chil) {
		chil.key.add(k);// ��Ӹ��ڵ�Ĺؼ���
		Collections.sort(chil.key);
		chil.countKey++;
		par.key.remove(i);// ���ڵ�Ĺؼ����Ƴ�
		par.countKey--;
		// ��ʱ���ڵ��л�����һ������
		List<Integer> keys = bef_chil.key;
		List<BTree_node> children = bef_chil.children;
		chil.key.addAll(keys);// ������ֵܵ����йؼ���
		Collections.sort(chil.key);
		chil.countKey += keys.size();
		if (chil.isLefe()) {

		} else {// ������ֵܵ����к���
			List<BTree_node> c_children = chil.getChildren();
			bef_chil.children.addAll(c_children);
			List<BTree_node> bef_children = bef_chil.getChildren();
			chil.children.clear();
			chil.children.addAll(bef_children);

			// }
		}

		par.children.remove(i);// �Ӹ��ڵ���ɾ�����ֵ�
		if (par.isRoot()) {
			this.root = chil;
			chil.parent = null;
			return this.root;
		} else {
			return par;
		}
	}

	public int rigkey(BTree_node node) {
		int key;
		while (!(node.isLeaf())) {
			node = node.children.get(0);
		}
		key = node.key.get(0);
		return key;
	}

	public int lefkey(BTree_node node) {
		int key;
		while (!(node.isLeaf())) {
			int size = node.children.size();
			node = node.children.get(size - 1);
		}
		int size = node.key.size();
		key = node.key.get(size - 1);
		return key;
	}

}
