import json
import base64

mob_name = input('Ingrese el nombre del mob: ')
image_url = input('Ingrese la URL de la imagen de la skin: ')

skin = {
  "textures" : {
    "SKIN" : {
      "url" : image_url
    }
  }
}

json_dumped_skin = json.dumps(skin)
encoded_skin = base64.b64encode(bytes(json_dumped_skin, 'utf-8')).decode('utf-8')

skin_data = {
    "properties": [{
            "name": "textures",
            "value": encoded_skin
        }]
        }

result = f'{mob_name}: player "" setCustomNameVisible false setSkin {json.dumps(skin_data, separators=(",", ":"))}'

print(f'Copia lo siguiente en el archivo plugins/LibsDisguises/configs/diguises.yml y en la configuracion del mob establece el tanto el nombre del mob como el tipo del Disguise en {mob_name}: ')
print(result)
