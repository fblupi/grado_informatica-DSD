/* Archivo msg.x: Protocolo de impresion de un mensaje remoto */
program CALCULAPROG {
	version OPERACIONES { 	
		int CALCULA (int,int,int) = 1; 	/* primer int-> operación
						 * segundo int -> primer entero
						 * tercer int  -> segundo entero 
						 */		
	} = 1;	
} = 0x20000001;	
