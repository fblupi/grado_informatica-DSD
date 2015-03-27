/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "asociacion.h"

bool_t
xdr_ID (XDR *xdrs, ID *objp)
{
	register int32_t *buf;

	 if (!xdr_int (xdrs, objp))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Clave (XDR *xdrs, Clave *objp)
{
	register int32_t *buf;

	 if (!xdr_string (xdrs, objp, TAMA))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Valor (XDR *xdrs, Valor *objp)
{
	register int32_t *buf;

	 if (!xdr_string (xdrs, objp, TAMA))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_EntradaPtr (XDR *xdrs, EntradaPtr *objp)
{
	register int32_t *buf;

	 if (!xdr_pointer (xdrs, (char **)objp, sizeof (struct Entrada), (xdrproc_t) xdr_Entrada))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_DiccionarioPtr (XDR *xdrs, DiccionarioPtr *objp)
{
	register int32_t *buf;

	 if (!xdr_pointer (xdrs, (char **)objp, sizeof (struct Diccionario), (xdrproc_t) xdr_Diccionario))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Estado (XDR *xdrs, Estado *objp)
{
	register int32_t *buf;

	 if (!xdr_enum (xdrs, (enum_t *) objp))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Entrada (XDR *xdrs, Entrada *objp)
{
	register int32_t *buf;

	 if (!xdr_Clave (xdrs, &objp->clave))
		 return FALSE;
	 if (!xdr_Valor (xdrs, &objp->valor))
		 return FALSE;
	 if (!xdr_EntradaPtr (xdrs, &objp->sig))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Diccionario (XDR *xdrs, Diccionario *objp)
{
	register int32_t *buf;

	 if (!xdr_ID (xdrs, &objp->id))
		 return FALSE;
	 if (!xdr_DiccionarioPtr (xdrs, &objp->sig))
		 return FALSE;
	 if (!xdr_EntradaPtr (xdrs, &objp->first))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_ResultEntrada (XDR *xdrs, ResultEntrada *objp)
{
	register int32_t *buf;

	 if (!xdr_Estado (xdrs, &objp->estado))
		 return FALSE;
	switch (objp->estado) {
	case OK:
		 if (!xdr_Valor (xdrs, &objp->ResultEntrada_u.valor))
			 return FALSE;
		break;
	default:
		break;
	}
	return TRUE;
}

bool_t
xdr_ResultDiccionario (XDR *xdrs, ResultDiccionario *objp)
{
	register int32_t *buf;

	 if (!xdr_Estado (xdrs, &objp->estado))
		 return FALSE;
	switch (objp->estado) {
	case OK:
		 if (!xdr_EntradaPtr (xdrs, &objp->ResultDiccionario_u.entPtr))
			 return FALSE;
		break;
	default:
		break;
	}
	return TRUE;
}

bool_t
xdr_ponerasociacion_1_argument (XDR *xdrs, ponerasociacion_1_argument *objp)
{
	 if (!xdr_ID (xdrs, &objp->arg1))
		 return FALSE;
	 if (!xdr_Clave (xdrs, &objp->arg2))
		 return FALSE;
	 if (!xdr_Valor (xdrs, &objp->arg3))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_obtenerasociacion_1_argument (XDR *xdrs, obtenerasociacion_1_argument *objp)
{
	 if (!xdr_ID (xdrs, &objp->arg1))
		 return FALSE;
	 if (!xdr_Clave (xdrs, &objp->arg2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_borrarasociacion_1_argument (XDR *xdrs, borrarasociacion_1_argument *objp)
{
	 if (!xdr_ID (xdrs, &objp->arg1))
		 return FALSE;
	 if (!xdr_Clave (xdrs, &objp->arg2))
		 return FALSE;
	return TRUE;
}
