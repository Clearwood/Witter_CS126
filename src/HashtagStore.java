package uk.ac.warwick.java.cs126.services;
public class HashtagStore{
    private HashtagStoreElement<HashtagElement> head;
    private HashtagStoreElement<HashtagElement> tail;
    private int size;
    public HashtagStore(){
        tail = null;
        head = null;
        this.size = 0;
    }
    public boolean isEmpty(){
        return (tail == null || head == null);
    }
    public int size(){
        return size;
    }
    public HashtagStoreElement<HashtagElement> find(String hashtag){
        //linear search if such a hashtag already exists
        HashtagStoreElement<HashtagElement> ptr = head;
        while(ptr != null){
            if(ptr.getValue().getHashtag()==hashtag){
                return ptr;
            }
            ptr = ptr.getNext();
        }
        return null;
    }
    public String getHashtag(int index) {
        // Gets the element at index in the list
        HashtagStoreElement<HashtagElement> ptr = head;
        for (int i=size()-1;i>index;i--) {
            ptr = ptr.getNext();
        }
        return ptr.getValue().getHashtag();
    }
    public String[] getFirst10(){
        String[] first10 = new String[10];
        if(size()>=10){
            for(int i=0; i<10;i++){
                first10[i]=getHashtag(i);
            }
        } else {
            for(int i=0; i<size();i++){
                first10[i]=getHashtag(i);
            }
            for(int i=9; i>size()-1;i--){
                first10[i]=null;
            }
        }
        return first10;
    }
    public boolean add(String hashtag){
        if(isEmpty()){
            HashtagElement tmp = new HashtagElement(hashtag);
            HashtagStoreElement<HashtagElement> tmp2 = new HashtagStoreElement<HashtagElement>(tmp2);
            head = tmp2;
            tail = tmp2;
        } else {
            HashtagStoreElement<HashtagElement> find = find(hashtag);
            if(find != null){
                find.getValue().updateOccurence();
                if(find!=head) {
                    int occurenceToAdd = find.getValue().getOccurence();
                    HashtagStoreElement<HashtagElement> ptr = find.getPrev();
                    if (ptr.getValue().getOccurence() < occurenceToAdd) {
                        ptr.setNext(find.getNext());
                        find.getNext().setPrev(ptr);
                        if(find==tail){
                            tail = find.getPrev();
                        }
                        if (ptr != head) {
                            while (ptr != null) {
                                ptr = ptr.getPrev();
                                if (ptr.getValue().getOccurence() > occurenceToAdd) {
                                    find.setNext(ptr.getNext());
                                    find.setPrev(ptr);
                                    ptr.setNext(find);
                                    return true;
                                } else if (ptr == head){
                                    head.setPrev(find);
                                    find.setNext(head);
                                    head = find;
                                    return true;
                                }
                            }
                        } else {
                            head.setPrev(find);
                            find.setNext(head);
                            head = find;
                            return true;
                        }
                    }
                } else{
                    return true;
                }
            } else {
                HashtagElement tmp = new HashtagElement(hashtag);
                HashtagStoreElement<HashtagElement> tmp2 = new HashtagStoreElement<HashtagElement>(tmp2);
                tail.setNext(tmp2);
                tmp2.setPrev(tail);
                tail = tmp2;
                size++;
                return true;
            }
        }
        return false;
    }
}