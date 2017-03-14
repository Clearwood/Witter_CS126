package uk.ac.warwick.java.cs126.services;
import java.util.Comparator;
//generic mergesort
public class MergeSort<E> {

    private ArrayList<E> strList;
    private final Comparator<E> c;
    // Constructor
    public MergeSort(ArrayList<E> input, Comparator<E> c) {
        strList = input;
        this.c = c;
        strList = mergeSort(strList);
    }
    public ArrayList<E> getSorted(){
        return strList;
    }

    public ArrayList<E> mergeSort(ArrayList<E> whole) {
        ArrayList<E> left = new ArrayList<E>();
        ArrayList<E> right = new ArrayList<E>();
        int centerElement;

        if (whole.size() == 1) {
            return whole;
        } else {
            centerElement = whole.size()/2;
            //copies the right part of the array in to the right arraylist
            for (int i=centerElement; i<whole.size(); i++) {
                right.add(whole.get(i));
            }
            //copies the left part of the array into the left arraylist
            for (int i=0; i<centerElement; i++) {
                left.add(whole.get(i));
            }
            //sort right part of arraylist
            right = mergeSort(right);
            // Sort left part of arraylist
            left  = mergeSort(left);

            // merge everything back together
            merge(left, right, whole);
        }
        return whole;
    }

    private void merge(ArrayList<E> left, ArrayList<E> right, ArrayList<E> whole) {
        int IndexLeft = 0;
        int IndexRight = 0;
        int IndexWhole = 0;

        //while both arraylists are not empty get the larger value
        //of the two and copy it to the whole array
        while (IndexLeft < left.size() && IndexRight < right.size()) {
            if ( c.compare(left.get(IndexLeft),right.get(IndexRight)) > 0) {
                whole.set(IndexWhole, left.get(IndexLeft));
                IndexLeft++;
            } else {
                whole.set(IndexWhole, right.get(IndexRight));
                IndexRight++;
            }
            wholeIndex++;
        }

        ArrayList<E> rest;
        int restIndex;
        if (IndexLeft >= left.size()) {
            // The left arraylist is empty
            rest = right;
            restIndex = IndexRight;
        } else {
            //the right arraylist is empty
            rest = left;
            restIndex = IndexLeft;
        }

        // Copy the rest of the arraylist which has not been used up yet to the rest arraylist
        for (int i=restIndex; i<rest.size(); i++) {
            whole.set(IndexWhole, rest.get(i));
            IndexWhole++;
        }
    }
}