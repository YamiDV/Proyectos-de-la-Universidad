from google.oauth2.credentials import Credentials
from googleapiclient.discovery import build
from google.oauth2 import service_account
import pywhatkit as kit
from datetime import datetime, timedelta
import time

SCOPES = ['https://www.googleapis.com/auth/spreadsheets']
KEY = 'key.json'
# Escribe aquí el ID de tu documento:


SPREADSHEET_ID = '1aS5QJZgBTn7p15MBSig7g6ZhS7ENaWaNiP5BRSB7YYQ'

creds = None
creds = service_account.Credentials.from_service_account_file(KEY,scopes=SCOPES)

service = build('sheets', 'v4', credentials=creds)
sheet = service.spreadsheets()
A2='A3'

 
def enviarmsj(X):
    # Llamada a la api
    celda = str(X)
    result = sheet.values().get(spreadsheetId=SPREADSHEET_ID, range='Mensajes!'+ celda +':D5').execute()
    arreglo1 = []


    
    if not result or 'values' not in result:
        # Si result está vacío o no contiene 'values'
        arreglo1.append(1)  # Incrementar la variable 'arreglo' en 1
    else:
        # Si result tiene valores
        # Realizar alguna acción adicional si es necesario
        pass
    print(arreglo1)
    # Extraemos values del resultado
    values = result.get('values',[])
    print(values)
    flat_list = [item for sublist in values for item in sublist]

    # Unir todos los elementos en una cadena separados por un espacio
    cadena_texto = ' '.join(flat_list)

    # Imprimir la cadena resultante

    print(cadena_texto)

    # Enviar un mensaje a un número específico  python-sheets-test@proyecto-yape.iam.gserviceaccount.com
    target_number = "+51 926921038"
    message = cadena_texto
    hora_actual = datetime.now()
    hora_envio = hora_actual.hour
    minuto_envio = hora_actual.minute + 1
    kit.sendwhatmsg(target_number, message, hora_envio, minuto_envio)  # Establece la hora en formato de 24 horas

enviarmsj(A2)