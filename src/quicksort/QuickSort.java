package quicksort;

import java.util.ArrayList;
import java.util.List;

public class QuickSort <Type extends Comparable<Type>> {
	public List<Type> sort(List<Type> list) {
		if (list.size() > 1) {	//Make sure list is has more than one element
			Type pivot = list.get(list.size() / 2);	//Set pivot to middle element
			
			List<Type> less, equal, greater; //Create three lists of same type
			
			less = new ArrayList<Type>();
			equal = new ArrayList<Type>();	//Instantiate lists
			greater = new ArrayList<Type>();
			
			for (Type item : list) {	//For each item in the list
				if (item.compareTo(pivot) == -1) {	//If the item is compared smaller than the pivot, then add to less list
					less.add(item);
				} else if (item.compareTo(pivot) == 0) {
					equal.add(item);	//If the item is compared equal to pivot, then add to equal list
				} else {
					greater.add(item);	//If the item is compared greater than pivot, then add to greater list
				}

				less = sort(less);			//Recursively sort less and greater lists, stopping when there is only one element left in list
				greater = sort(greater);
			}

			list.clear();	//After sorting clear original list

			for (Type num : less) {		//Reconstruct original list sorted
				list.add(num);
			}
			for (Type num : equal) {
				list.add(num);
			}
			for (Type num : greater) {
				list.add(num);
			}

			return list;
		}
		return list;	//Return list
	}
}

