package assigment01.management;

import assigment01.datastructure.MyLinkedList;
import assigment01.datastructure.Node;
import assigment01.entity.Book;
import assigment01.entity.Lending;
import assigment01.entity.Reader;
import assigment01.validation.Validation;

public class LendingListManager {
    
    public void inputData(MyLinkedList<Book> listBook, MyLinkedList<Reader> listReader,
            MyLinkedList<Lending> listLending) {
        
        BookListManager bm = new BookListManager();
        ReaderListManager rd = new ReaderListManager();
        //Check Bcode exist.
        boolean checkBcode = false;
        String bcode = "";
        String rcode = "";
        do {
            bcode = Validation.getInputBookID("Enter bcode: ", listBook);
            Node<Book> curr = listBook.getHead();
            while (curr != null) {
                if (curr.getData().getBcode().equalsIgnoreCase(bcode)) {
                    checkBcode = false;
                    curr = curr.getNext();
                }
                if (checkBcode) {
                    System.out.println("Cant find this bcode.");
                }
            }
        } while (checkBcode);
        //Check rcode exist.
        boolean checkRcode = false;
        do {
            rcode = Validation.getInputReaderID(rcode, listReader);
            Node<Reader> curr1 = listReader.getHead();
            while (curr1 != null) {
                if (curr1.getData().getRcode().equalsIgnoreCase(rcode)) {
                    checkRcode = false;
                    curr1 = curr1.getNext();
                }
            }
            if (checkRcode) {
                System.out.println("Can't find this Reader code.");
            }
        } while (checkRcode);
        int state = Validation.getIntLimit("Enter state", 0, 2);
        Node<Book> book = bm.searchByCode(listBook, bcode, true);
        Node<Reader> reader = rd.searchByCode(listReader, rcode);
        if (state == 1) {
            System.out.println("The reader can't lend. Please Given back.");
        } else if (book.getData().getLended() == book.getData().getQuantity()) {
            listLending.addLast(new Lending(bcode, rcode, 0));
        } else if (book.getData().getLended() < book.getData().getLended()) {
            updateListBook(listBook, bcode);
            listLending.addLast(new Lending(bcode, rcode, 1));
        }
    }
    
    public void updateListBook(MyLinkedList<Book> listBook, String bcode) {
        Node<Book> curr = listBook.getHead();
        while (curr != null) {
            if (curr.getData().getBcode().equalsIgnoreCase(bcode)) {
                curr.getData().setLended(curr.getData().getLended() + 1);
                curr.getData().setQuantity(curr.getData().getQuantity() - 1);
            }
        }
    }
    
    public void sort(MyLinkedList<Lending> listLending) {
        for(Node<Lending> i = listLending.getHead(); i != null; i = i.next) {
            Node<Lending> min = i;
            for(Node<Lending> j = i.next; j != null; j = j.next) {
                boolean comparator1 = min.data.getBcode().trim().compareToIgnoreCase(j.getData().getBcode().trim()) > 0;
                boolean comparator2 = min.data.getRcode().trim().compareToIgnoreCase(j.getData().getRcode().trim()) > 0;
                if(comparator1 && comparator2) {
                    min = j;
                }
            }
            Lending temp = min.getData();
            min.setData(i.getData());
            i.setData(temp);
        }
    }
}