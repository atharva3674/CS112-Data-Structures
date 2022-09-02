package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		/** COMPLETE THIS METHOD **/
		if(g == null || p1 == null || p2 == null) return null;
		ArrayList<String> array = new ArrayList<String>();
		boolean[] marked = new boolean[g.members.length];
		int index = g.map.get(p1);
		int[] edgeTo = new int[g.members.length];
		int[] distTo = new int[g.members.length];
		Queue<Person> q = new Queue<Person>();
		Person[] personTouch = new Person[g.members.length];
		q.enqueue(g.members[index]);
		marked[index] = true;
		while(!q.isEmpty()){
			Person person = q.dequeue();
			int personIndex = g.map.get(person.name);
			marked[personIndex] = true;
			Friend adjacent = person.first;
			if(adjacent == null) return null;
			while(adjacent != null){
				if(!marked[adjacent.fnum]){
					marked[adjacent.fnum] = true;
					personTouch[adjacent.fnum] = person;
					q.enqueue(g.members[adjacent.fnum]);
					if(g.members[adjacent.fnum].name.equals(p2)){
						person = g.members[adjacent.fnum];

						while(!person.name.equals(p1)){
							array.add(0, person.name);
							person = personTouch[g.map.get(person.name)];
						}
						array.add(0, p1);
						return array;
					}
				}
				adjacent = adjacent.next;
			}
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return null;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		/** COMPLETE THIS METHOD **/
		if(g == null || school == null || school.length() == 0) return null;
		boolean[] marked = new boolean[g.members.length];
		ArrayList<ArrayList<String>> array = new ArrayList<>();		
		for(Person person : g.members){
			if(person.school != null && marked[g.map.get(person.name)] == false  && person.school.equals(school)){
				ArrayList<String> arrayList = new ArrayList<>();
				Queue<Integer> queue = new Queue<>();
				int start = g.map.get(person.name);
				marked[start] = true;
				arrayList.add(person.name);
				queue.enqueue(start);
				while(!queue.isEmpty()){
					int n = queue.dequeue();
					Person person1 = g.members[n];
					for(Friend ptr = person1.first; ptr != null; ptr = ptr.next){
						int n1 = ptr.fnum;
						Person person2 = g.members[n1];
						if(marked[n1] == false && person2.school != null && person2.school.equals(school)  ){
							marked[n1] = true;
							queue.enqueue(n1);
							arrayList.add(g.members[n1].name);
						}
					}
				}
				array.add(arrayList);
			}
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return array;
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		ArrayList<String> arrayList = new ArrayList<>();
		boolean[] marked = new boolean[g.members.length];
		int[] values = new int[g.members.length];
		int[] values1 = new int[g.members.length];

		for(Person person : g.members){
			if(marked[g.map.get(person.name)] == false){
				values1 = new int[g.members.length];
				dfs(g, arrayList, marked, values, values1, g.map.get(person.name), g.map.get(person.name));
			}
		}
		for(int i = 0; i < arrayList.size(); i++){
			Friend ptr = g.members[g.map.get(arrayList.get(i))].first;
			int n = 0;
			while(ptr != null){
				ptr = ptr.next;
				n++;
			}
			if(n == 0 || n == 1) arrayList.remove(i);
		}
		for(Person person : g.members){
			if(person.first.next == null && !arrayList.contains(g.members[person.first.fnum].name))
				arrayList.add(g.members[person.first.fnum].name);
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return arrayList;
		
	}

	private static void dfs(Graph g,ArrayList<String> arrayList, boolean[] marked, int[] values, int[] values1, int i , int j){
		Person person = g.members[i];
		marked[g.map.get(person.name)] = true;
		int n = 0;
		for(int k = 0; k <values1.length; k++){
			if(values1[k] != 0) n++;
		}
		n = n + 1;
		if(values[i] == 0 && values1[i] == 0){
			values1[i] = n;
			values[i] = values1[i];
		}
		for(Friend ptr = person.first; ptr != null; ptr = ptr.next){
			if(marked[ptr.fnum] != true){
				dfs(g, arrayList, marked, values, values1, ptr.fnum, j);
				if(values1[i] > values[ptr.fnum]){
					values[i] = Math.min(values[i], values[ptr.fnum]);
				}
				else{
					if(i == 0 && Math.abs(values1[i] - values1[ptr.fnum]) <= 1 && values[ptr.fnum] == 1 && Math.abs(values1[i] - values[ptr.fnum]) < 1){
						continue;
					}
					if(values1[i] <= values[ptr.fnum] && (i != 0 || values[ptr.fnum] == 1)){
						if(!arrayList.contains(g.members[i].name))
							arrayList.add(g.members[i].name);
					}
				}
			}
			else{
				values[i] = Math.min(values[i], values1[ptr.fnum]);
			}
		}
	}
}

