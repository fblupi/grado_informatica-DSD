const TAMA = 128; 
typedef int ID;
typedef string Clave<TAMA>;
typedef string Valor<TAMA>;
typedef struct Entrada * EntradaPtr;
typedef struct Diccionario * DiccionarioPtr;

enum Estado {
	OK = 0,
	FALLO = 1,
	REEMPLAZADO = 2
};

struct Entrada {
	Clave clave;
	Valor valor;
	EntradaPtr sig;
};

struct Diccionario {
	ID id;
	DiccionarioPtr sig;
	EntradaPtr first;
};

union ResultEntrada switch(Estado estado) {
	case OK: Valor valor;
	default: void;
};

union ResultDiccionario switch(Estado estado) {
	case OK: EntradaPtr entPtr;
	default: void;
};

program ASOCIACIONPROG {
	version ASOCIACION {
		Estado ponerAsociacion(ID, Clave, Valor) = 1;
		ResultEntrada obtenerAsociacion(ID, Clave) = 2;
		Estado borrarAsociacion(ID, Clave) = 3;
		ResultDiccionario enumerar(ID) = 4;
	} = 1;
} = 0x20000001;

