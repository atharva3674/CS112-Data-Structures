package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author Atharva Patil
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node sumCurrent = null;
		Node sumLast;

		Node poly1Ptr = poly1;
		Node poly2Ptr = poly2;

		int degree = 0;
        float coeff = 0;

		while(poly1Ptr != null && poly2Ptr != null){
			if(poly1Ptr.term.degree < poly2Ptr.term.degree ){
				
				degree = poly1Ptr.term.degree;
				coeff = poly1Ptr.term.coeff;

				Node term = new Node(coeff, degree, sumCurrent);

				sumCurrent = term;

				poly1Ptr = poly1Ptr.next;
				
			} else if(poly1Ptr.term.degree == poly2Ptr.term.degree){
				
				if(poly1Ptr.term.coeff + poly2Ptr.term.coeff == 0){
					poly1Ptr = poly1Ptr.next;
					poly2Ptr = poly2Ptr.next;
				}
				else{
				degree = poly1Ptr.term.degree;
				coeff = poly1Ptr.term.coeff + poly2Ptr.term.coeff;

				Node term = new Node(coeff, degree, sumCurrent);

				sumCurrent = term;

				poly1Ptr = poly1Ptr.next;
				poly2Ptr = poly2Ptr.next;
				}

			} else if(poly1Ptr.term.degree > poly2Ptr.term.degree){

				degree = poly2Ptr.term.degree;
				coeff = poly2Ptr.term.coeff;

				Node term = new Node(coeff, degree, sumCurrent);

				sumCurrent = term;

				poly2Ptr = poly2Ptr.next;

			}
		}
		
		if(poly1Ptr == null){
			while( poly2Ptr != null){
				degree = poly2Ptr.term.degree;
				coeff = poly2Ptr.term.coeff;

				Node term = new Node(coeff, degree, sumCurrent);

				sumCurrent = term;

				poly2Ptr = poly2Ptr.next;
			}

		}
		else if (poly2Ptr == null){
			while( poly1Ptr != null){
				degree = poly1Ptr.term.degree;
				coeff = poly1Ptr.term.coeff;

				Node term = new Node(coeff, degree, sumCurrent);

				sumCurrent = term;

				poly1Ptr = poly1Ptr.next;
			}

		}

		Node ptr = sumCurrent;
		Node previous = null;
		Node next = null;
		while(ptr != null){
			next = ptr.next;
			ptr.next = previous;
			previous = ptr;
			ptr = next;
		}
		sumCurrent = previous;



		
		return sumCurrent;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

		Node sumCurrent = null;
		Node poly1Ptr = poly1;
		Node poly2Ptr = poly2;

		if(poly1 == null || poly2 == null){
			return sumCurrent;
		}

		while(poly1Ptr != null){
			poly2Ptr = poly2;
			while(poly2Ptr != null){
				float coeff = 0;
				int degree = 0;

				coeff = poly1Ptr.term.coeff * poly2Ptr.term.coeff;
				degree = poly1Ptr.term.degree + poly2Ptr.term.degree;

				Node term = new Node(coeff, degree, sumCurrent);

				sumCurrent = term;

				poly2Ptr = poly2Ptr.next;
			}

			poly1Ptr = poly1Ptr.next;

		}
		

		int maxDegree = sumCurrent.term.degree + 1;
		Node sumCurrentSimp = null;
		
		for(int degree = 0; degree < maxDegree; degree++){
			float coeff = 0;
			Node genPtr = sumCurrent;
			while(genPtr != null){
				if(genPtr.term.degree == degree){
					coeff = coeff + genPtr.term.coeff;

				}
				genPtr = genPtr.next;
			}

			if (coeff != 0){
				sumCurrentSimp = new Node(coeff, degree, sumCurrentSimp);
			}
		}

		// reverse
		Node ptr = sumCurrentSimp;
		Node previous = null;
		Node next = null;
		while(ptr != null){
			next = ptr.next;
			ptr.next = previous;
			previous = ptr;
			ptr = next;
		}
		sumCurrentSimp = previous;
		
		


		return sumCurrentSimp;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

		float sum = 0;

		for(Node ptr = poly; ptr != null; ptr = ptr.next){
			sum += (ptr.term.coeff) * Math.pow(x, ptr.term.degree);
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
