#pragma once
#include <iostream>
#include <stdio.h>

class BinaryTreeNode
{
public:
	BinaryTreeNode(void) {
		m_right = nullptr;
		m_left = nullptr;
	};
	BinaryTreeNode(int v) {
		m_value = v;
		m_right = nullptr;
		m_left = nullptr;
	};

	// Setter Methods
	void set_value(int val) {
		m_value = val;
	};
	void setLeft(BinaryTreeNode* left){
		m_left = left;
	};
	void setRight(BinaryTreeNode* right){
		m_right = right;
	};

	// Getter Methods
	int get_value() {
		return m_value;
	};
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
	BinaryTree(void);
	BinaryTree(int val)
	{
		m_root = new BinaryTreeNode(val);
	};
	
	void insert(int val)
	{
		if (val == m_root->get_value())
		{	
			std::cout << "insert(" << val << ") ERROR: node with this value already exists." << std::endl;
		} 
		else 
		{
			BinaryTreeNode* currentNode = m_root;
			BinaryTreeNode* parentNode;
			while (currentNode != nullptr) {
				parentNode = currentNode;
				if (val < currentNode->get_value()) {
					currentNode = currentNode->getLeft();
				} else if (val > currentNode->get_value()) {
					currentNode = currentNode->getRight();
				} else {
					std::cout << "insert(" << val << ") ERROR: node with this value already exists." << std::endl;
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

	// get Node by value
	BinaryTreeNode* getNode(int val) {

	};

	void remove(int val) {
		// 1. find Node
		BinaryTreeNode* currentNode = m_root;
		BinaryTreeNode* parentNode = nullptr;
		std::cout << "currentNode(root) = " << currentNode->get_value() << std::endl;
		while (currentNode->get_value() != val && currentNode != nullptr) {
			parentNode = currentNode;
			if (currentNode->get_value() < val) currentNode = currentNode->getRight();
			else if (currentNode->get_value() > val) currentNode = currentNode->getLeft();
			std::cout << "currentNode = " << currentNode->get_value() << std::endl;
		};
		if (currentNode == nullptr) std::cout << "Node not found" << std::endl;
		else std::cout << "Node removed" << std::endl;

		// 2. check children
		// a) no children -> just delete
		if (currentNode->getLeft() == nullptr && currentNode->getRight() == nullptr) {
			if (parentNode->getLeft() != nullptr && parentNode->get_value() > val) 
				parentNode->setLeft(nullptr);
			else parentNode->setRight(nullptr);
		};
		// b) 1 child -> link parent to only child
		BinaryTreeNode* grandchild;
		bool rightChildOnly = (currentNode->getLeft() == nullptr && currentNode->getRight() != nullptr);
		bool leftChildOnly = (currentNode->getLeft() != nullptr && currentNode->getRight() == nullptr);
		
		if (leftChildOnly || rightChildOnly) {
			if (rightChildOnly) {
				grandchild = currentNode->getRight();
			}
			else if (leftChildOnly) {
				grandchild = currentNode->getLeft();
			}
			if (parentNode->getLeft()->get_value() == currentNode->get_value()) {
				parentNode->setLeft(grandchild);
			}
			else if (parentNode->getRight()->get_value() == currentNode->get_value()) {
				parentNode->setRight(grandchild);
			}
		}
		// c) 2 children -> get smallest descendant of right child
		BinaryTreeNode* smallestDescendant;
		BinaryTreeNode* replaceNode = currentNode;

		if (currentNode->getLeft() != nullptr && currentNode->getRight() != nullptr) {
			currentNode = currentNode->getRight();
			while (currentNode != nullptr) {
				smallestDescendant = currentNode;
				currentNode = currentNode->getLeft();
			}
			replaceNode->set_value(smallestDescendant->get_value());
		}

	};

	// TODO: implement
	int show() 
	{
		return m_root->get_value();
	};

	BinaryTreeNode* get_root()
	{
		return m_root;
	};

	// TODO: print out Tree structure
	char* toString() {
		BinaryTreeNode* currentNode = m_root;

	};

private:
	char* printNode(BinaryTreeNode * currentNode) {
		BinaryTreeNode * parentNode;
		while (currentNode != nullptr) {
			
		}
	};
	BinaryTreeNode* m_root;
};