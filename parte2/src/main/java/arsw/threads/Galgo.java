package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	int ubicacion;
	boolean pausado;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		pausado=true;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {			
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);

			synchronized (this){
				while (getPausado()){
					wait();
				}

			}

			if (paso == carril.size()) {

				carril.finish();

				synchronized (regl) {
					ubicacion = regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion + 1);
				}
				System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
				if (ubicacion==1){
					regl.setGanador(this.getName());
				}
				
			}
		}
	}


	@Override
	public void run() {
		
		try {

			pausado=false;
			corra();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void pausado(){
		synchronized (this){
			pausado=true;

		}
	}

	public void continuar(){
		synchronized (this){
			pausado=false;
			notifyAll();
		}
	}
	public boolean getPausado(){
		return pausado;
	}


}
