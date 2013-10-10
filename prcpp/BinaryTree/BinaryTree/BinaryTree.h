#pragma once
#include <iostream>
#include <stdio.h>

class BinaryTreeNode
{
public:
	BinaryTreeNode(void);
	BinaryTreeNode(int v) {
		m_value = v;
	};
	int get_value() {
		return m_value;
	};
	
	// Setter Methods
	void setLeft(BinaryTreeNode* left){
		m_left = left;
	};
	void setRight(BinaryTreeNode* right){
		m_right = right;
	};

	// Getter Methods
	BinaryTreeNode* getLeft(){
		return m_left;
	};
	BinaryTreeNode* getRight(){
		return m_right;
	};
	int m_value;
	
private:
	BinaryTreeNode* m_left;
	BinaryTreeNode* m_right;
};

class BinaryTree
{
public:
	BinaryTree(int val)
	{
		m_root = BinaryTreeNode(val);
	};
	~BinaryTree(void);

	void insert(int val)
	{
		if (val == m_root.get_value())
		{	
			std::cout << "node with this value already exists." << std::endl;
		} 
		else 
		{
			BinaryTreeNode* currentNode = &m_root;
			BinaryTreeNode* parentNode;
			while (&currentNode != NULL) {
				parentNode = currentNode;
				if (val < currentNode->get_value()) {
					currentNode = currentNode->getLeft();
				} else if (val > currentNode->get_value()) {
					currentNode = currentNode->getRight();
				} else {
					std::cout << "node with this value already exists." << std::endl;
				}
			}
			// add Node where child is null
			if (val < parentNode->get_value()) {
				parentNode->setLeft(new BinaryTreeNode(val));
			} else if (val > parentNode->get_value()) {
				parentNode->setRight(new BinaryTreeNode(val));
			}
			
		}
	};
	void remove(void);
	int show() 
	{
		return m_root.get_value();
	};

	BinaryTreeNode get_root()
	{
		return m_root;
	};

private:
	BinaryTreeNode m_root;
};