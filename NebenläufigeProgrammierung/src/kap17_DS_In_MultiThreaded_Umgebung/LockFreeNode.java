package kap17_DS_In_MultiThreaded_Umgebung;

/** * Knote für lockfreie Liste */
import java.util.concurrent.atomic.AtomicMarkableReference;
class LockFreeNode<T>{  T data; // Feld für Datum  // Referenz für das nächste Element + Markierung  AtomicMarkableReference<LockFreeNode<T>> next;  LockFreeNode(T item)  {    data = item;    next = new AtomicMarkableReference<LockFreeNode<T>>(null, false);  }}
