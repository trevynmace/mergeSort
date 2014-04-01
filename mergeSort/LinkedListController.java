package mergeSort;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

public class LinkedListController
{
    LinkedList<Integer> linkedList = new LinkedList<Integer>();
    JButton exit = new JButton("Exit");
    JButton convertAndMerge = new JButton("Convert and merge table");
    JFrame frame = new JFrame();

    protected void init()
    {
        linkedList = createRandomIntegerList();
        displayListInTable(true);
    }

    private LinkedList<Integer> createRandomIntegerList()
    {
        //Create a linked list of 100 random integers in the range 1-1000.
        Random generator = new Random();

        for (int i = 0; i < 100; i++)
        {
            linkedList.add(generator.nextInt(1000) + 1);
        }

        return linkedList;
    }

    private void displayListInTable(boolean showConvert)
    {
        createFrame(frame);

        JTable table = new JTable(10, 10);
        int counter = 0;

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                table.setValueAt(linkedList.get(counter), i, j);
                counter++;
            }
        }

        addButtonActions();
        table.setEnabled(false);
        table.setGridColor(Color.BLACK);
        frame.add(table);
        if (showConvert)
        {
            frame.add(convertAndMerge);
            frame.setTitle("Unsorted Table");
        }
        else
        {
            convertAndMerge.setEnabled(false);
            frame.add(exit);
            frame.setTitle("Merge Sorted Table");
            frame.setLocation(450, 250);
        }
        frame.setVisible(true);
    }

    private void createFrame(JFrame changedFrame)
    {
        changedFrame.setSize(800, 220);
        changedFrame.setLocation(400, 200);
        changedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        changedFrame.setResizable(false);
        changedFrame.setLayout(new FlowLayout());
    }

    void convertAndMergeList()
    {
        frame = new JFrame();
        createFrame(frame);
        frame.add(exit);

        //  Put all LinkedList elements into an array
        Comparable[] newList = new Comparable[linkedList.size()];
        for (int i = 0; i < linkedList.size(); i++)
        {
            newList[i] = linkedList.get(i);
        }

        //  Perform a recursive merge sort on the array.
        mergeSort(newList);

        //  Convert back to LinkedList
        LinkedList<Integer> listToReturn = new LinkedList<Integer>();
        for (Comparable comparable : newList)
        {
            listToReturn.push((Integer)comparable);
        }
        Collections.reverse(listToReturn);

        //  Set the converted LinkedList back to the original LinkedList and display it
        linkedList = listToReturn;
        displayListInTable(false);
    }

    public static void mergeSort(Comparable list[])
    {
        recurse(list, 0, list.length - 1);
    }

    private static void recurse(Comparable list[], int start, int end)
    {
        //  The threshold is met when the end minus the start index is greater than zero after recursion
        if (end - start > 0)
        {
            int midPoint = (start + end) / 2;
            recurse(list, start, midPoint);
            recurse(list, midPoint + 1, end);
            merge(list, start, midPoint, end);
        }
        else
        {
            insertionSort(list, start, end);
        }
    }

    //  This method merges the two array pieces back into one array
    public static void merge(Comparable list[], int first, int mid, int last)
    {
        Comparable[] tmp = new Comparable[last - first + 1];
        int i = first, j = mid + 1, k = 0;

        while (i <= mid && j <= last)
        {
            if (list[i].compareTo(list[j]) < 0)
            {
                tmp[k++] = list[i++];
            }
            else
            {
                tmp[k++] = list[j++];
            }
        }

        while (i <= mid)
        {
            tmp[k++] = list[i++];
        }

        while (j <= last)
        {
            tmp[k++] = list[j++];
        }

        for (k = first; k <= last; k++)
        {
            list[k] = tmp[k - first];
        }
    }

    private static void insertionSort(Comparable list[], int start, int end)
    {
        for (int k = end - 1; k >= start; k--)
        {
            Comparable tmp = list[k];
            int j = k + 1;
            while (j <= end && tmp.compareTo(list[j]) > 0)
            {
                list[j - 1] = list[j];
                j++;
            }
            list[j - 1] = tmp;
        }
    }

    private void addButtonActions()
    {
        convertAndMerge.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                convertAndMergeList();
            }
        });
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
    }
}