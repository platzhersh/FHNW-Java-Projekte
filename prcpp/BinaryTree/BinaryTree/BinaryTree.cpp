#include "BinaryTree.h"



int main(){
	BinaryTreeNode b = BinaryTreeNode(12);
	std::cout << b.get_value() << std::endl;

	BinaryTreeNode *pb = new BinaryTreeNode(13);
	std::cout << pb->get_value() << std::endl;

	BinaryTree t = BinaryTree(2);
	t.insert(1);
	t.insert(3);
	
}