package patterns.observer.copyright.sol;

public interface TextModel {

  void addListener(Listener l);

  void insert(int pos, char ch);

  void delete(int from, int len);

  String getSubstring(int from, int len);

}