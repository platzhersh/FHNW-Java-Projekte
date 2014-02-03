#include <fstream>
#include <iostream>
using namespace std;

class StateMachine2 {

	enum State { Text, Slash, SComment, MComment, Star };

	State state = Text;

	char c;
	char buf[100];

	ifstream in;
	ofstream out;

public:

	void run() {
		in.open("raw.txt");
		if (!in){
			cout << "raw kann nicht geöffnet werden!";
		}
		else{
			out.open("rclean.txt", ios::trunc);
			if (!out){
				cout << "clean kann nicht geöffnet werden!";
			}
			while (in.get(c) && out && !in.eof()){

				cout << c;

				switch (state){
				case Text:
					if (c == '/') { state = Slash; }
					else{ out.put(c); }	break;
				case Slash:
					if (c == '/'){ state = SComment; }
					else if (c == '*'){ state = MComment; }
					else{
						state = Text; out.put('/'); out.put(c); break;
				case SComment:
					if (c == '\n' || c == '\r'){ state = Text; out.put(c); }break;
				case MComment:
					if (c == '*')state = Star; break;
				case Star:
					if (c == '/'){ state = Text; }
					else{ state = MComment; }break;
					}
				}
			}
		}
		cin.ignore();
	}
};
