package bTree;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Administrator
 *
 */
public class BTree_test {
	
	public void test(test t) {
		t.string = "123";
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BTree tree = new BTree();
		tree.init();
		//�������
//		tree.insert(51);
//		tree.insert(52);
//		tree.insert(49);
//		tree.insert(48);
//		tree.insert(47);
//		tree.insert(46);
//		tree.insert(45);
//		tree.insert(44);
//		tree.insert(43);
//		tree.insert(42);
//		tree.insert(41);
//		tree.insert(40);
		
		//ɾ������
		
		/*//1.ɾ���ڵ�����Ҷ�ӽڵ�Ĺؼ���
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		
		tree.delete(3);
		tree.delete(2);
		tree.delete(1);*/
		
		/*//2.ɾ��Ҷ�ӽڵ�Ĺؼ��֣��Ǹ��ڵ㣩
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		
		tree.delete(4);
		tree.delete(1);*/
		
		/*//3.ɾ���ڲ��ڵ�Ĺؼ��֣��ҹؼ��ֺ�������Ĺؼ����㹻
		tree.insert(10);
		tree.insert(20);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		tree.insert(60);
		
		tree.delete(40);*/
		
		/*//4.ɾ���ڲ��ڵ�Ĺؼ��֣��ҹؼ��ֵ�ǰ������������Ĺؼ��ֶ��������ϲ�
		tree.insert(10);
		tree.insert(20);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		tree.insert(60);
		
		tree.delete(20);*/
		
		/*//5.ɾ���ڲ��ڵ�Ĺؼ��֣�(���ڵ㣬�Ҹ��ڵ�ֻ��һ���ؼ���)�ҹؼ��ֵ�ǰ������������Ĺؼ��ֶ��������ϲ�
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		
		tree.delete(4);
		tree.delete(2);*/
		
		/*//6.������ʱ���ֽڵ㲻����ؼ��ָ���,�����ֵܽڵ�ؼ�������Ҫ��
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		
		tree.delete(1);*/
		
		/*//7.������ʱ���ֽڵ㲻����ؼ��ָ���,�ֵܽڵ�ؼ���Ҳ����
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		
		tree.delete(4);
		tree.delete(3);*/
		
		/*//8.ɾ��ʱ�ƶ����ӵĶ���ʱ�����
		tree.insert(10);
		tree.insert(20);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		tree.insert(60);
		tree.insert(8);
		tree.insert(9);
		tree.insert(6);
		
		tree.delete(20);*/
		
		/*//8.ɾ��ʱ�ƶ����ӵĶ���ʱ�����2
		tree.insert(10);
		tree.insert(20);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		tree.insert(60);
		tree.insert(8);
		tree.insert(9);
		tree.insert(6);
		
		tree.delete(40);*/
		
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		tree.insert(8);
		tree.insert(9);
		tree.insert(10);
		
		tree.delete(4);
		tree.delete(5);
		tree.delete(6);
		tree.delete(7);
		tree.delete(8);
		tree.delete(9);
		System.out.println("succeful");
	}
	
	
}
