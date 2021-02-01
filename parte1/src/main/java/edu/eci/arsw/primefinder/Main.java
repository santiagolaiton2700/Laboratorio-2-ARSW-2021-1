package edu.eci.arsw.primefinder;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	private static AtomicInteger cont =new AtomicInteger(0);


	public static void main(String[] args) {
		
		PrimeFinderThread pft1=new PrimeFinderThread(0, 9999999,cont);
		PrimeFinderThread pft2=new PrimeFinderThread(10000000, 19999999,cont);
		PrimeFinderThread pft3=new PrimeFinderThread(20000000, 30000000,cont);

		
		pft1.start();
		pft2.start();
		pft3.start();

		long start = System.currentTimeMillis();
		long end = start + 5*1000;
		System.out.println();
		while (System.currentTimeMillis() < end) {
			//System.out.println((end-start)/1000);
		}
		//System.out.println(System.currentTimeMillis()/1000+"Tiempo");

		pft1.suspend();
		pft2.suspend();
		pft3.suspend();

		Scanner inputScanner = new Scanner(System.in);
		String input;

		System.out.println(cont.get()+" Numero de primos encontrados");
		System.out.print("Oprima enter para continuar la busqueda: ");
		input = inputScanner.nextLine();

		if (input.isEmpty()) {
			pft1.resume();
			pft2.resume();
			pft3.resume();

			try {
				pft1.join();
				pft2.join();
				pft3.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(cont.get()+" Numero de primos encontrados");


		}



		
		
	}
	
}
