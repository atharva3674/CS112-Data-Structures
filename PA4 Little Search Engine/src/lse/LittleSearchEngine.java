package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		HashMap<String, Occurrence> hashMap = new HashMap<String, Occurrence>(1000,2.0f);
		Scanner scanner = new Scanner(new File(docFile));
		while(scanner.hasNext()){
			String frag = getKeyword(scanner.next());
			if(frag == null) continue;

			Occurrence occurrence = hashMap.get(frag);

			if(occurrence == null){
				occurrence = new Occurrence(docFile, 1);
				hashMap.put(frag, occurrence);
			}
			else{
				occurrence.frequency++;
			}

		}

		scanner.close();

		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return hashMap;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		
		for(String key : kws.keySet()){
			Occurrence occurences = kws.get(key);
			Boolean hasKey = keywordsIndex.containsKey(key);
			if(hasKey){
				ArrayList<Occurrence> occurences1 = keywordsIndex.get(key);
				occurences1.add(occurences);
				insertLastOccurrence(occurences1);
			}
			else{
				ArrayList<Occurrence> occurences1 = new ArrayList<>();
				occurences1.add(occurences);
				insertLastOccurrence(occurences1);
				keywordsIndex.put(key, occurences1);

			}

		}

	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		word = word.replaceAll("[\\.|\\,|\\?|\\:|\\;|\\!]+$", "");
		for(int i = 0; i < word.toCharArray().length; i++){
			if(!Character.isAlphabetic(word.toCharArray()[i])) return null;
		}
		String word1 = word.toLowerCase();
		if(noiseWords.contains(word1)) return null;	

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return word1;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		int n = occs.size();
		ArrayList<Integer> array = new ArrayList<Integer>();
		Occurrence occurence = occs.get(n - 1);
		int low = 0;
		int mid = 0;
		int high = n - 2;
		int result = high + 1;

		while(low <= high){
			mid = (low + high) / 2;
			array.add(mid);
			if(occs.get(mid).frequency > occurence.frequency) low = mid + 1;

			if(occs.get(mid).frequency < occurence.frequency) high = mid - 1;

			if(occs.get(mid).frequency == occurence.frequency) break;

		}

		if(occs.get(mid).frequency <=  occurence.frequency) occs.add(mid , occs.remove(result));
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return array;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * javac -d bin src/lse/*.java 
	 * java -cp bin lse.LSETest
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		int i1 = 0;
		int i2 = 0;
		Occurrence k1 = null;
		Occurrence k2 = null;
		ArrayList<Occurrence> kWord1 = keywordsIndex.get(kw1);
		ArrayList<Occurrence> kWord2 = keywordsIndex.get(kw2);
		ArrayList<String> array = new ArrayList<String>();
		while(array.size() != 5){
			k1 = null;
			k2 = null;
			try{
				k1 = kWord1.get(i1);
			}
			catch(IndexOutOfBoundsException a){}
			catch(NullPointerException a){}
			
			try{
				k2 = kWord2.get(i2);
			}
			catch(IndexOutOfBoundsException a){}
			catch(NullPointerException a){}
			if(k1 == null && k2 == null) break;

			if(k2 == null){
				if(!array.contains(k1.document)) array.add(k1.document);

				i1++;
				continue;
			}

			if(k1 == null){
				if(!array.contains(k2.document)) array.add(k2.document);

				i2++;
				continue;
			}
			int k1N = k1.frequency;
			int k2N = k2.frequency;

			if(k1N > k2N){
				if(!array.contains(k1.document)) array.add(k1.document);

				i1++;
			}
			else{
				if(!array.contains(k2.document)) array.add(k2.document);

				i2++;
			}

		}

		if(array.isEmpty()) return null;
		

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return array;
	
	}
}
