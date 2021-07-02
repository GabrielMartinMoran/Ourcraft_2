export const commands = {
    getHTML: () => {
        return /*html*/`
        <h1>Comandos</h1>
        <div class="m-2">
        <ul>
        <li><b>/stats:</b> Muestra los atributos del jugador que invocó el comando. 
        También puede agregarse el nombre de otro jugador al final del comando para visualizar los atributos de este otro (por ejemplo: <i>/stats MrKupo</i>)</li>
        <li><b>/memories help:</b> Muestra el listado operaciones posibles con tu memoria. Usa el comando <i>memories</i> para guardar datos que no quieras olvidar.</li>
        </ul>
        </div>
        `;
    }
}