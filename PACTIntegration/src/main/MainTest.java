package main;

import java.util.Date;
import java.util.Stack;

public class MainTest {

	public static void main(String[] args) 
	{

		Stack<Integer> S = new Stack<Integer>() ;
		System.out.println(S.empty()) ;
		S.push(0) ;
		S.push(1) ;
		S.push(2) ;
		
		while(!S.empty())
		{
			System.out.println(S.empty()) ;
			int top = S.pop();
			System.out.println(top) ;
		}
		System.out.println(S.empty()) ;

		
		

	}

}
