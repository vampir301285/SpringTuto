package kap17_DS_In_MultiThreaded_Umgebung;

/** * Codebeispiel für Listenknoten */

class Node<T>{  final T data;  Node<T> next;
  public Node(T data)  {    assert(data != null);    this.data = data;    this.next = null;  }}
