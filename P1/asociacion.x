const TAMA = 128; /* Tamaño máximo de los String */
typedef diccionario* DiccionarioPtr;
typedef entrada* EntradaPtr;
typedef int ID;
typedef string Clave<TAMA>;
typedef string Valor<TAMA>;

enum Estado {
	OK = 0,
	FALLO = 1,
	REEMPLAZADO = 2
};

struct Diccionario {
	ID id;
	diccionarioPtr sig;
	entradaPtr first;
};

struct Entrada {
	Clave clave;
	Valor valor;
	entradaPtr sig;
};

union ResultEntrada switch(Estado e) {
	case OK: Valor valor;
	case FALLO: void;
};

union ResultDiccionario switch(Estado e) {
	case OK: Entrada entrada;
	case FALLO: void;
};

program ASOCIACION {
	version ASOCIACION {
		Estado ponerAsociacion(ID, Clave, Valor) = 1;
		ResultEntrada obtenerAsociacion(ID, Clave) = 2;
		Estado borrarAsociacion(ID, Clave) = 3;
		ResultDiccionario enumerar(ID) = 4;
	} = 1;
} = 0x20000001;

