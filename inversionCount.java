import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class inversionCount {

	static int inversions = 0;

	static ArrayList<Integer> inversionCompariosnCounts = new ArrayList<Integer>();
	static ArrayList<String> inversionCompariosnFiles = new ArrayList<String>();

	    public static void main(String[] args) {
	        int sources = 5;
	        for (int sourceNumber = 1; sourceNumber <= sources; sourceNumber++) {
	            String fileName = "source" + sourceNumber + ".txt";
	            int length = 10000;
	            int[] ranklist = new int[length];
	            try {
	                FileReader(fileName, ranklist);
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            }
	            // Perform different sorting algorithms and gets the inversion count
	            CountInversions(ranklist, length, fileName, "Quick Sort");
	            CountInversions(ranklist, length, fileName, "Bubble Sort");
	            CountInversions(ranklist, length, fileName, "Merge Sort");
	        }

			// Below is the code to find lowest inversion with file 
			int countTrack = inversionCompariosnCounts.get(0);
			int trackFile = 0;
			for(int i=0;i<inversionCompariosnCounts.size();i++){
				if(inversionCompariosnCounts.get(i)<countTrack){
					countTrack = inversionCompariosnCounts.get(i);
					trackFile=i;
				}
			}
			System.out.println("The least number of inversion is " + countTrack + '\n');
			System.out.println("The file with least inversion is : " + inversionCompariosnFiles.get(trackFile));
	    }

	    private static void FileReader(String fileName, int[] ranklist) throws FileNotFoundException  {
	        Scanner scanner = new Scanner(new File(fileName));
	        int i = 0;
	        while (scanner.hasNextInt() && i < ranklist.length) {
	            ranklist[i] = scanner.nextInt();
	            i++;
	        }
	    }

	    private static void CountInversions(int[] ranklist, int length, String fileName, String sortAlgorithm) {
	        int[] copy = new int[length];
	        System.arraycopy(ranklist, 0, copy, 0, length);
	        switch (sortAlgorithm) {
	            case "Quick Sort":
	                QuickSortInvertCount(copy, length);
	                break;
	            case "Bubble Sort":
	                BubbleSort(copy, length);
	                break;
	            case "Merge Sort":
	                MergeSort(copy, length);
	                break;
	            default:
	                break;
	        }
			System.out.println(fileName + " " + sortAlgorithm + " Inversions: " + inversions + '\n');
			inversionCompariosnCounts.add(inversions);
			inversionCompariosnFiles.add(fileName);
	        inversions = 0;
	    }
	
		private static void MergeSort(int[] ranklist, int length) {
			//return base case
			if(length==1){return;}
				
			//initialize lengths of sub-arrays
			int lengthL = 0;
			int lengthR = 0;
				
			//assign length to sub-arrays based on even or odd ranklist length
			if(length%2==0){lengthL = length/2;}
			else{lengthL = length/2+1;}
			lengthR = length-lengthL;
				
			//initialize sized sub-arrays
			int [] L = new int[lengthL];
			int [] R = new int[lengthR];
				
			//for-loops assign values from ranklist to sub-arrays based on position relative to split
			int i = 0;
			for(i=0;i<lengthL;i++){
				L[i] = ranklist[i];
			}
				
			for(i=0;i<lengthR;i++){
				R[i] = ranklist[i+lengthL];
			}
				
			//recursively call to merge-sort function and add to stack
			MergeSort(L,lengthL);
			MergeSort(R,lengthR);
				
			//apply merge method to sort arrays into ranklist
			merge(ranklist,L,R,length,lengthL,lengthR);
		}

		//most relevant info is passed to function since it's already been calculated
		private static void merge(int[] ranklist,int[] L,int[] R,int length,int lengthL,int lengthR) {
			
			//initialize sub-array and ranklist counters
			int l = 0;
			int r = 0;
			int i = 0;
				
			//while-loop assigns elements from both sub-arrays in growing order into ranklist until one sub-array is empty
			while(l<lengthL && r<lengthR){
			//<= statement very important to not count duplicate elements as inversions
				if(L[l]<=R[r]){
					ranklist[i] = L[l];
					l++;
				}
				else {
					ranklist[i] = R[r];
					r++;
					inversions+=lengthL-l; 	//***inversions are based on if smaller value is in R
				}							//***The number of inversions is from how many larger values remain unassigned in L
				i++;
			}
				
			//based on which sub-array was emptied, remaining elements of other sub-array are copied into the ranklist
			//**notice, sub arrays have already been sorted, ranklist remains sorted after pasting new sub-array
			if(l==lengthL) {
				System.arraycopy(R, r, ranklist, i, lengthR-r);
			}
			if(r==lengthR) {
				System.arraycopy(L, l, ranklist, i, lengthL-l);
			}
			//System.out.println(Arrays.toString(ranklist)); This will show merged array if desired
		}
		

		
		//quicksort method accepts pointer and lenght argument
		private static void QuickSortInvertCount(int[] ranklist, int length) {
				
			//catch simplest possible case and return
			if (length<=1){
				return;
			}
				
			//select pivot point index (we selected center element or element or just before center)
			int pivot = 0;
				
				
			if ((length%2)==0) {
				pivot = (length/2)-1;
			}
			else {
				pivot = (length/2);
			}
				
			//initialize length integers for our 3 sub-arrays
			int lengthL = 0; 
			int lengthE = 0; 
			int lengthG = 0;
				
			//count all elements less-than, equal-to, or greater-than pivot element and increase lengths respectively
			for(int i = 0; i<length;i++) {
				if (ranklist[i]< ranklist[pivot]){lengthL++;}
				if (ranklist[i]==ranklist[pivot]){lengthE++;}
				if (ranklist[i]> ranklist[pivot]){lengthG++;}
			}
				
			//initialize less-than, equal-to, and greater-than arrays
			int [] L = new int[lengthL];
			int [] E = new int[lengthE];
			int [] G = new int[lengthG];
				
			//initialize array counters
			int l = 0; int e = 0; int g = 0;
				
			//For loop assigns elements to L,E, and G respectively
			//inversions are counted based on how many larger elements were sorted before smaller element is placed
			for(int i = 0; i<length;i++) {
				if (ranklist[i]< ranklist[pivot]){
					L[l] = ranklist[i]; 
					l++;
					inversions+=g+e;
					//counts inversions based on how many larger elements were sorted first
				}
				if (ranklist[i]==ranklist[pivot]){
					E[e] = ranklist[i]; 
					e++;
					inversions+=g;
					//counts inversions based on how many larger elements were sorted first
				}
					
				if (ranklist[i]> ranklist[pivot]){
					G[g] = ranklist[i]; 
					g++;
				}
			}
				
				//recursively call method for L and G (E cannot be sorted and wastes time)
				QuickSortInvertCount(L,lengthL);
				QuickSortInvertCount(G,lengthG);
				
				//concatenate L, E, & G at the address of provided ranklist (this rewrites the provided ranklist)
				System.arraycopy(L, 0, ranklist, 0, lengthL);
				System.arraycopy(E, 0, ranklist, lengthL, lengthE);
				System.arraycopy(G, 0, ranklist, lengthL+lengthE, lengthG);
				
				
			}
		
		private static void BubbleSort(int[] ranklist, int length) {
			//temp variable for swapping procedure
			int x = 0;
			//first for loop ensures that even first element can reach end if largest
			for(int i=0;i<length;i++) {
				//second for loop moves through array to swap any inverted values
				for(int j = 0; j<(length-1);j++) {
					//only check if inverted values are present
					if (ranklist[j]>ranklist[j+1]) {
						x = ranklist[j];
						ranklist[j] = ranklist[j+1];
						ranklist[j+1] = x;
						inversions++;
						//inversion count increased each time element is found inverted
					}
				}
			}
		}
}







