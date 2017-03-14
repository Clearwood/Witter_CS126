package uk.ac.warwick.java.cs126.services;
//stores all HashTags sorted by occurence in a doubly linked list
/*
parts of this program where developed during CS126 lab which is why I have to give credit to Aaron Baw
*/
public class HashtagStore{
    private HashtagStoreElement<HashtagElement> head;
    private HashtagStoreElement<HashtagElement> tail;
    private int size;
    //initializes store
    public HashtagStore(){
        tail = null;
        head = null;
        this.size = 0;
    }
    //checks if its empty
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
            if(ptr.getValue().getHashtag().equals(hashtag)){
                return ptr;
            }
            ptr = ptr.getNext();
        }
        return null;
    }
    public String getHashtag(int index) {
        // Gets the element at index in the list by using linear traversal
        HashtagStoreElement<HashtagElement> ptr = head;
        for(int i=0;i<index;i++){
            ptr = ptr.getNext();
        }
        return ptr.getValue().getHashtag();
    }
    public String[] getFirst10(){
        //getting first 10 hashtags in string, if there are less than 10 it fills up with null
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
    //adds hashtags to the store
    public boolean add(String hashtag){

        //checks if store is empty
        if(isEmpty()){
            //creates an HashTagElement
            HashtagElement tmp = new HashtagElement(hashtag);
            //from that HashTagElement it forms an Element which is used as the Storing element as a doubly linked list
            HashtagStoreElement<HashtagElement> tmp2 = new HashtagStoreElement<HashtagElement>(tmp);
            head = tmp2;
            tail = tmp2;
            size++;
        } else {
            //checks if the hashtag is already in store
            HashtagStoreElement<HashtagElement> find = find(hashtag);
            //if hashtag is in list
            if(find != null){
                //increases internal occurence counter
                //System.err.println("occurence before: "+find(hashtag).getValue().getOccurence()+"is in list");
                find.getValue().updateOccurence();
                //System.err.println("occurence after: "+find(hashtag).getValue().getOccurence()+"is in list");
                if(find!=head) {
                    //gets the number of occurences of the element
                    int occurenceToAdd = find.getValue().getOccurence();
                    //gets pointer to element left to it
                    HashtagStoreElement<HashtagElement> ptr = find.getPrev();
                    if (ptr.getValue().getOccurence() < occurenceToAdd) {
                        //if the hashtags occures more than the hashtag to the left of it update the previous and next element
                        //of the hashtag which was updated
                        ptr.setNext(find.getNext());
                        //if the element updated was previously the tail element the tail has to be updated
                        if(find==tail){
                            tail = find.getPrev();
                        } else{
                            find.getNext().setPrev(ptr);
                        }
                        //traverse until either the head is reached or the hashtag on the left has  more occurences
                        if (ptr != head) {
                            while (ptr != null) {
                                //get next element on the left
                                ptr = ptr.getPrev();
                                if (ptr.getValue().getOccurence() > occurenceToAdd) {
                                    //insert element after the hashtag which has more occurences
                                    find.setNext(ptr.getNext());
                                    ptr.getNext().setPrev(find);
                                    find.setPrev(ptr);
                                    ptr.setNext(find);
                                    return true;
                                } else if (ptr == head){
                                    //if the pointer has come to the head of the element the head has to be updated additionally
                                    head.setPrev(find);
                                    find.setNext(head);
                                    head = find;
                                    return true;
                                }
                            }
                        } else {
                            //if the pointer is already at the head of the list the head has to be updated
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
                //if a hashtag is newly inserted it cannot have more occurences than anything before
                //so it is inserted at the very end of the list
                HashtagElement tmp = new HashtagElement(hashtag);
                HashtagStoreElement<HashtagElement> tmp2 = new HashtagStoreElement<HashtagElement>(tmp);
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