package TAM4.TAM4;

public class Tablero {

	private String[][] casillas;
	
	public Tablero() {
		casillas = new String[3][3];
		reiniciarTablero();
	}
	
	
	public String[][] getCasillas() {
		return casillas;
	}
	

	public void insertarMovimiento(int x, int y, String ficha) {
			casillas[x][y] = ficha;
	}
	
	/* Verifica si una casilla específica en el tablero está disponible o si ya
	 * está ocupada por una ficha. Si la casilla está vacía, la función devuelve true,
	 * si no, devuelve false.
	 */
	public boolean comprobarCasilla(int x, int y) {
		
		if(casillas[x][y].equals("")){
			return true;
		}else {
			return false;
		}
	}
	
	public void vaciarCasilla(int x, int y) {
		casillas[x][y] = "";
	}
	
	/*esta función comprueba si hay un ganador en el juego verificando todas las combinaciones posibles en el tablero
	*Si encuentra un ganador, devuelve verdadero; de lo contrario, devuelve falso.
	*/
	public boolean comprobarTablero() {
		String row = "";
		int i = 0;
		boolean found = false;
		
		while(i<8 && !found) {
			
			switch(i) {
				case 0:
					row = casillas[0][0]+casillas[0][1]+casillas[0][2];
					break;
				case 1:
					row = casillas[1][0]+casillas[1][1]+casillas[1][2];
					break;
				case 2:
					row = casillas[2][0]+casillas[2][1]+casillas[2][2];
					break;
				case 3:
					row = casillas[0][0]+casillas[1][0]+casillas[2][0];
					break;
				case 4:
					row = casillas[0][1]+casillas[1][1]+casillas[2][1];
					break;
				case 5:
					row = casillas[0][2]+casillas[1][2]+casillas[2][2];
					break;
				case 6:
					row = casillas[0][0]+casillas[1][1]+casillas[2][2];
					break;
				case 7:
					row = casillas[0][2]+casillas[1][1]+casillas[2][0];
					break;
			}
			if(row.equals("XXX")||row.equals("OOO")) {
				found = true;
			}
			i++;
		}
		return found;
	}

	//Se utiliza para reiniciar el estado del tablero, estableciendo todas las casillas en blanco
	public void reiniciarTablero() {
		
		for(int i=0;i<3.;i++) {
			for(int j=0;j<3.;j++) {
				casillas[i][j] = "";
			}
		}
	}
}
