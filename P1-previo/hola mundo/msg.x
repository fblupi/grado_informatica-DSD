/* Archivo msg.x: Protocolo de impresion de un mensaje remoto */
program MESSAGEPROG {	/* Nombre del servicio */
	version PRINTMESSAGEVERS { 	/* Nombre de versión. El generador de código hará #define PRINTMESSAGEVERS 1 */
		int PRINTMESSAGE (string) = 1; 	/* especificación de una función que devuelve un entero y recibe un string. */
						/* El = 1 es el identificador único del procedimiento. Si añado otro, le pongo 2. */
	} = 1;	/* Versión del servicio*/
} = 0x20000001;	/* Identificador único de nuestro servicio en nuestra máquina, así RPCBIND sabe cuando llegue una petición a que identificador mandarla.*/
