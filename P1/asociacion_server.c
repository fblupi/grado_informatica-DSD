/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "asociacion.h"

typedef enum {
	false,
	true
} bool;

DiccionarioPtr root = NULL;

Estado *
ponerasociacion_1_svc(ID arg1, Clave arg2, Valor arg3,  struct svc_req *rqstp)
{
	static Estado  result;

	if(root==NULL) { // No hay ningún diccionario
		// Nueva entrada
		EntradaPtr ent = (EntradaPtr)malloc(sizeof(Entrada));
		ent->clave = strdup(arg2);
		ent->valor = strdup(arg3);
		ent->sig = NULL;
		// Nuevo diccionario
		DiccionarioPtr dic = (DiccionarioPtr)malloc(sizeof(Diccionario));  
		dic->id = arg1;
		dic->sig = NULL;
		dic->first = ent;
		root = dic;
		result = OK;
	} else { // Hay algún diccionario
		DiccionarioPtr dicPtr = root; // Primera entrada es la raiz
		bool encontradoID = false;
		if(dicPtr->id==arg1) {
			encontradoID = true;
		}
		while(encontradoID==false && dicPtr->sig!=NULL) { // Buscar diccionario
			dicPtr = dicPtr->sig;
			if(dicPtr->id==arg1) {
				encontradoID = true;
			}
		}
		if(encontradoID==true) { // Diccionario encontrado
			EntradaPtr entPtr = dicPtr->first;
			bool encontradoClave = false;
			if(strcmp(entPtr->clave,arg2)==0) {
				encontradoClave = true;
			}
			while(encontradoClave==false && entPtr->sig!=NULL) { // Buscar entrada
				entPtr = entPtr->sig;
				if(strcmp(entPtr->clave,arg2)==0) {
					encontradoClave = true;
				}
			}
			if(encontradoClave==true) { // Clave encontrada
				entPtr->valor = strdup(arg3);
				result = REEMPLAZADO;
			} else { //  Clave no encontrada
				// Nueva entrada
				EntradaPtr ent = (EntradaPtr)malloc(sizeof(Entrada));
				ent->clave = strdup(arg2);
				ent->valor = strdup(arg3);
				ent->sig = NULL;
				entPtr->sig = ent;
				result = OK;
			}
		} else { // Diccionario no encontrado -> se crea uno nuevo
			// Nueva entrada
			EntradaPtr ent = (EntradaPtr)malloc(sizeof(Entrada));
			ent->clave = strdup(arg2);
			ent->valor = strdup(arg3);
			ent->sig = NULL;
			// Nuevo diccionario
			DiccionarioPtr dic = (DiccionarioPtr)malloc(sizeof(Diccionario));  
			dic->id = arg1;
			dic->sig = NULL;
			dic->first = ent;
			dicPtr->sig = dic;
			result = OK;
		}
	}

	return &result;
}

ResultEntrada *
obtenerasociacion_1_svc(ID arg1, Clave arg2,  struct svc_req *rqstp)
{
	static ResultEntrada  result;

	if(root==NULL) { // No hay ningún diccionario
		result.estado = FALLO;
	} else { // Hay algún diccionario
		DiccionarioPtr dicPtr = root;
		bool encontradoID = false;
		if(dicPtr->id==arg1) {
			encontradoID = true;
		}
		while(encontradoID==false && dicPtr->sig!=NULL) { // Buscar diccionario
			dicPtr = dicPtr->sig;
			if(dicPtr->id==arg1) {
				encontradoID = true;
			}
		}
		if(encontradoID==true) { // Diccionario encontrado
			EntradaPtr entPtr = dicPtr->first;
			bool encontradoClave = false;
			if(strcmp(entPtr->clave,arg2)==0) {
				encontradoClave = true;
			}
			while(encontradoClave==false && entPtr->sig!=NULL) { // Buscar entrada
				entPtr = entPtr->sig;
				if(strcmp(entPtr->clave,arg2)==0) {
					encontradoClave = true;
				}
			}
			if(encontradoClave==true) { // Clave encontrada
				result.estado = OK;
				result.ResultEntrada_u.valor = strdup(entPtr->valor);
			} else { //  Clave no encontrada
				result.estado = FALLO;
			}
		} else { // Diccionario no encontrado
			result.estado = FALLO;
		}
	}

	return &result;
}

Estado *
borrarasociacion_1_svc(ID arg1, Clave arg2,  struct svc_req *rqstp)
{
	static Estado  result;

	if(root==NULL) { // No hay ningún diccionario
		result = FALLO;
	} else { // Hay algún diccionario
		DiccionarioPtr dicPtr = root;
		bool encontradoID = false;
		if(dicPtr->id==arg1) {
			encontradoID = true;
		}
		if(encontradoID==true) { // Es el primer diccionario
			EntradaPtr entPtr = dicPtr->first;
			bool encontradoClave = false;
			if(strcmp(entPtr->clave,arg2)==0) {
				encontradoClave = true;
			}
			if(encontradoClave==true) { // Es la primera clave
				if(entPtr->sig==NULL) { // Es la unica clave
					root = dicPtr->sig;
					dicPtr->sig = NULL;
					xdr_free((xdrproc_t)xdr_Diccionario,(char*)dicPtr);
					result = OK;
				} else { // No es la unica clave
					dicPtr->first = entPtr->sig;
					entPtr->sig = NULL;
					xdr_free((xdrproc_t)xdr_Entrada,(char*)entPtr);
					result = OK;
				}
			} else { // No es la primera asociacion
				EntradaPtr entPtrAnt = entPtr;
				while(encontradoClave==false && entPtr->sig!=NULL) { // Buscar entrada
					entPtrAnt = entPtr;
					entPtr = entPtr->sig;
					if(strcmp(entPtr->clave,arg2)==0) {
						encontradoClave = true;
					}
				}
				if(encontradoClave==true) { // Clave encontrada
					entPtrAnt->sig = entPtr->sig;
					entPtr->sig = NULL;
					xdr_free((xdrproc_t)xdr_Entrada,(char*)entPtr);
					result = OK;
				} else { // Clave no encontrada
					result = FALLO;
				}
			}
		} else { // No es el primer diccionario
			DiccionarioPtr dicPtrAnt;
			while(encontradoID==false && dicPtr->sig!=NULL) { // Buscar diccionario
				dicPtrAnt = dicPtr;
				dicPtr = dicPtr->sig;
				if(dicPtr->id==arg1) {
					encontradoID = true;
				}
			}
			if(encontradoID==true) { // Diccionario encontrado
				EntradaPtr entPtr = dicPtr->first;
				bool encontradoClave = false;
				if(strcmp(entPtr->clave,arg2)==0) {
					encontradoClave = true;
				}
				if(encontradoClave==true) { // Es la primera clave
					if(entPtr->sig==NULL) { // Es la unica clave
						dicPtrAnt->sig = dicPtr->sig;
						dicPtr->sig = NULL;
						xdr_free((xdrproc_t)xdr_Diccionario,(char*)dicPtr);
						result = OK;
					} else { // No es la unica clave
						dicPtr->first = entPtr->sig;
						entPtr->sig = NULL;
						xdr_free((xdrproc_t)xdr_Entrada,(char*)entPtr);
						result = OK;
					}
				} else { // No es la primera asociacion
					EntradaPtr entPtrAnt = entPtr;
					while(encontradoClave==false && entPtr->sig!=NULL) { // Buscar entrada
						entPtrAnt = entPtr;
						entPtr = entPtr->sig;
						if(strcmp(entPtr->clave,arg2)==0) {
							encontradoClave = true;
						}
					}
					if(encontradoClave==true) { // Clave encontrada
						entPtrAnt->sig = entPtr->sig;
						entPtr->sig = NULL;
						xdr_free((xdrproc_t)xdr_Entrada,(char*)entPtr);
						result = OK;
					} else { // Clave no encontrada
						result = FALLO;
					}
				}
			} else { // Diccionario no encontrado
				result = FALLO;
			}
		} 
	}

	return &result;
}

ResultDiccionario *
enumerar_1_svc(ID arg1,  struct svc_req *rqstp)
{
	static ResultDiccionario  result;

	if(root==NULL) { // No hay ningún diccionario
		result.estado = FALLO;
	} else { // Hay algún diccionario
		DiccionarioPtr dicPtr = root;
		bool encontradoID = false;
		if(dicPtr->id==arg1) {
			encontradoID = true;
		}
		while(encontradoID==false && dicPtr->sig!=NULL) { // Buscar diccionario
			dicPtr = dicPtr->sig;
			if(dicPtr->id==arg1) {
				encontradoID = true;
			}
		}
		if(encontradoID==true) { // Diccionario encontrado
			result.estado = OK;
			EntradaPtr entPtr = dicPtr->first;
			EntradaPtr sal = (EntradaPtr)malloc(sizeof(Entrada));
			sal = entPtr;
			result.ResultDiccionario_u.entPtr = sal;
		} else { // Diccionario no encontrado -> se crea uno nuevo
			result.estado = FALLO;
		}
	}

	return &result;
}
