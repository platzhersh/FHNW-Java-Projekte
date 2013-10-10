#include "BinaryTree.h"



int main(){
	BinaryTreeNode b = BinaryTreeNode(12);
	std::cout << b.get_value() << std::endl;

	BinaryTreeNode *pb = new BinaryTreeNode(13);
	std::cout << pb->get_value() << std::endl;

	BinaryTree t = BinaryTree(50);
	t.insert(30);
	t.insert(20);
	t.insert(40);
	t.insert(80);
	t.insert(70);
	t.insert(90);
	t.insert(60);
	t.insert(100);
	t.insert(85);
	t.insert(101);
	t.insert(102);
	t.insert(103);
	t.insert(105);

	std::cout << "DEBUG" << std::endl;
	t.remove(30);
	t.remove(20);
	
	
	

	return 0;
	
}