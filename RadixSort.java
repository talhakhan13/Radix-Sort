import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class RadixSort {

	public static void main(String[] args) {
		
		int[] input = new int[] {1,3,2, -14, -11, 23, 101};
		int[] output = radix_sort(input);
		System.out.println(Arrays.toString(output));
	}
	
	public static int[] radix_sort(int[] input) {
		int []result = new int[input.length];
		
		// create the buckets
		int numBuckets = 11;
		Deque<Integer>[] buckets = new Deque[numBuckets];
		for(int i = 0; i < numBuckets; i++) {
			buckets[i] = new LinkedList<Integer>();
		}
		
		
		// get the LSD digit
		int divisor = 1;
		int numPasses = 0;
		for(int num: input) {
			int absVal = Math.abs(num);
			int digit = (absVal / divisor) % 10;
			buckets[digit + 1].add(num);
			// figure out the maximum number of digits a number has in our input
			numPasses = Math.max(numPasses, (int)Math.log10(absVal));
		}
		
		
		do {
			//put the numbers in the result
			int index = 0;
			for(int i = 0; i < numBuckets; i++) {
				while(buckets[i].size() > 0) {
					result[index++] = buckets[i].pollFirst();
				}
			}
			//repeat for significant digits
			if(numPasses > 0) {
				divisor *= 10;
				for(int num: result) {
					int absVal = Math.abs(num);
					int digit = (absVal / divisor) % 10;
					buckets[digit + 1].add(num);
				}
			}
		}while(numPasses-- > 0);
	
		
		//sort through the negatives
		for(int num: result){
			if(num < 0) {buckets[0].add(num);}
			else {buckets[1].add(num);}
		}
		
		int index = 0;
		for(int i = 0; i <= 1; i++) {
			while(buckets[i].size() > 0) {
				if(i == 0) {
					result[index++] = buckets[i].pollLast();
				}else {
					result[index++] = buckets[i].pollFirst();
				}
			}
		}
		
		return result;
	}

}
