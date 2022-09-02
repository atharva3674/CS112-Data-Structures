package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Atharva Patil
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 * javac -d bin src/trie/*.java
	 * java -cp bin trie.TrieApp
	 */
	public static TrieNode buildTrie(String[] allWords) {
		
		/** COMPLETE THIS METHOD**/

		int n = 0;
		n = allWords.length;

		if(n == 0){
			return null;
		}

		
		TrieNode root = new TrieNode(null, null, null);
		TrieNode ptr = null;
		TrieNode prevPtr = null;
		TrieNode ptr1 = null;

		
		for(int i = 0; i < n; i++){
			if(i == 0){
				
				Indexes index = new Indexes((short)i,(short) 0, (short)(allWords[i].length() - 1));
				root.firstChild = new TrieNode(index, null, null);
				 
				ptr = root.firstChild;
			}
				int child = 1;
				int prefix = 0;
				String frag = allWords[i];
				short startIndex = 0;
				short endIndex = 0;
				prevPtr = root;
				ptr1 = null;
				ptr = root.firstChild;
				int put = 0;
				
				while(ptr != null){

					char fragCurrentChar = frag.charAt(startIndex);
					char fragCompareChar = allWords[ptr.substr.wordIndex].charAt(startIndex);

					if(fragCurrentChar == fragCompareChar){
						int len = frag.length();
						for(short letter = startIndex; letter < len;letter++){
							if(frag.charAt(letter) != allWords[ptr.substr.wordIndex].charAt(letter) || letter > ptr.substr.endIndex){
								break;
							}
							endIndex = letter;
						}
						if(endIndex == ptr.substr.endIndex){
							startIndex = (short) (endIndex + 1);
							prevPtr = ptr;
							ptr = ptr.firstChild;
							
						}
						else{

							int index = ptr.substr.wordIndex;

							Indexes newIndex1= new Indexes(index, startIndex, endIndex);
							Indexes newIndex2 = new Indexes(index, (short) (endIndex + 1), ptr.substr.endIndex);
							Indexes newIndex3 = new Indexes(i, (short)(endIndex + 1), (short) (frag.length() - 1));
							TrieNode newNode1 = new TrieNode (newIndex1, null, null);
							TrieNode newNode2 = new TrieNode (newIndex2, null, null);
							TrieNode newNode3 = new TrieNode (newIndex3, null, null);

							if(prevPtr.firstChild == ptr || prevPtr == root){
								prevPtr.firstChild = newNode1;
							}
							else{
								prevPtr.sibling = newNode1;
							}
							newNode2.sibling = newNode3;
							newNode1.firstChild = newNode2;
							newNode1.sibling = ptr.sibling;
							


							if(ptr.firstChild != null){
								newNode2.firstChild = ptr.firstChild;
							}
							break;
						}


					}

					else if(ptr.sibling == null && (fragCurrentChar != fragCompareChar)){
						
						int n1 = frag.length();
						for(short b = startIndex; b < n1; b++){

							endIndex = b;
						}

						Indexes newIndex = new Indexes(i, startIndex, endIndex);

						TrieNode newNode1 = new TrieNode(newIndex, null, null);

						ptr.sibling = newNode1;
						break;
					}
					else{
						prevPtr = ptr;

						ptr = ptr.sibling;
					}	
				}			
			}
			return root; 
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
	}

	// 0 no common

	private static int common(String[] allWords, int index1, int index2){
		String frag = allWords[index2];
		String frag1 = allWords[index1];
		
		int counter = 0;
		if(frag.charAt(0) != frag1.charAt(0))
				return -1;
		
		for(int i = 0; i < frag1.length(); i++){
			if(frag1.charAt(i) == frag.charAt(i)){
				counter++;
			}
			else{
			return counter;
			}
		}


	return counter;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		ArrayList<TrieNode> arrayList = new ArrayList<>();
		TrieNode ptr = root;

		while(ptr != null){

			if(ptr.substr == null){
				ptr = ptr.firstChild;
			}
		

			int n = 0;

			int startIndex = 0;
			int endIndex = ptr.substr.endIndex;
			String frag = allWords[ptr.substr.wordIndex];
			String fragSub = frag.substring(startIndex, endIndex + 1);
			boolean c1 = prefix.startsWith(fragSub);
			boolean c2 = frag.startsWith(prefix);
			
			if(c1 || c2){
				if(ptr.firstChild == null){
					arrayList.add(ptr);	

				}
				else{
					arrayList.addAll(completionList(ptr.firstChild, allWords, prefix));
					
					if(ptr.sibling != null){
						String f = allWords[ptr.sibling.substr.wordIndex];
						String f1 = f.substring(startIndex, ptr.sibling.substr.endIndex + 1);
						if(f1.startsWith(prefix) == false)
							break;
					}
					// break;
				}
			}
			


			ptr = ptr.sibling;
			
		}

		if(arrayList.isEmpty()){
			return null;
		}
		


		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return arrayList;
	}

	private static int level(int n){
		n++;
		return n;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
