export const mechanics = {
    getHTML: () => {
        return /*html*/`
        <h1>Mecánicas</h1>
        <div class="m-2">
            <h3>Mundo en expansión</h3>
            <div class="row m-4">
                <img class="col-md-3 w-100"
                    src="https://gamepedia.cursecdn.com/minecraft_gamepedia/a/a5/Worldborderanimation.gif">
                <div class="col-md-9">
                    Comenzando con un radio de 250 bloques, a medida que los jugadores cooperan y progresen en equipo,
                    el mundo se ira expandiendo a razon de 1 bloque de radio por segundo.
                    Esto impedira a los jugadores alejarse del área de spawn ni bien comienza la partida, limitando la
                    exploración a etapas no tan tempranas del juego,
                    donde deberan arreglárselas con los recursos que encuentren en el área inicial reducida.
                </div>

            </div>
            <h3>Diversidad de razas</h3>
            <div class="row m-4">
                <img class="col-md-3 w-100"
                    src="https://www.pngkey.com/png/detail/850-8502586_minecraft-characters-steve-and-alex.png">
                <div class="col-md-9">
                    Al comenzar la partida, cada jugador deberá elegir una raza del <a href="races.html">listado de
                        razas</a>. Cada raza otorga habilidades únicas y permanentes.
                    Una vez seleccionada, esta no podrá ser cambiada.
                </div>

            </div>
            <h3>Crafteos limitados</h3>
            <div class="row m-4">
                <img class="col-md-3 w-100"
                    src="https://gamepedia.cursecdn.com/minecraft_gamepedia/0/07/Crafting_Table_JE4.png">
                <div class="col-md-9">
                    No todos los crafteos existentes en Minecraft se encuentran habilitados. Los items y herramientas
                    que se encuentran en el
                    <a href="craftings.html">listado de crafteos eliminados</a> no podran ser construidos por el
                    jugador. Esto provocara al jugador la necesidad de ingeniarselas para poder
                    obtener ciertos elementos del juego.
                </div>
            </div>
            <h3>Sin regeneración automática</h3>
            <div class="row m-4">
                <img class="col-md-3 w-100"
                    src="https://gamepedia.cursecdn.com/minecraft_gamepedia/a/a7/Heart.svg?version=f0799535de721205f7304d484a9e9b58">
                <div class="col-md-9">
                    La regeneración automática de salud se encuentra deshabilitada, lo que significa en en caso de
                    sufrir daño, para regenerar corazones deberan consumir pociones que produzcan
                    este efecto, utilizar manzanas doradas o beacons de regeneración.
                </div>
            </div>
            <h3>Misiones individuales</h3>
            <div class="row m-4">
                <img class="col-md-3 w-100"
                    src="https://gamepedia.cursecdn.com/minecraft_es_gamepedia/2/2f/Libro_y_pluma.png?version=4c7d81cdc1da1b8a2dd86cdca5131098">
                <div class="col-md-9">
                    Cada jugador deberá completar una serie de <a href="quests.html">misiones individuales</a>. Las
                    misiones se categorizan en 5 niveles de dificultades.
                    Al comienzo de la partida, a cada jugador se le asignaran aleatoriamente 2 misiones de cada nivel,
                    de las cuales deberá realizar al menos
                    1 por nivel de dificultad.
                </div>
            </div>
            <h3>Misiones grupales</h3>
            <div class="row m-4">
                <img class="col-md-3 w-100"
                    src="https://gamepedia.cursecdn.com/minecraft_es_gamepedia/0/0b/Dragon_Egg.png?version=af86ed0cb3a62c6025bb5d58cd47feee">
                <div class="col-md-9">
                    Además de las misiones individuales, los jugadores tendran <a href="quests.html">misiones
                        grupales</a> que deberán realizar en compañia de los demás.
                </div>
            </div>
            <h3>Fogatas mejoradas</h3>
            <div class="row m-4">
                <img class="col-md-3 w-100"
                    src="https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/9/91/Campfire_JE2_BE2.gif/150px-Campfire_JE2_BE2.gif?version=4078c73f98691fedcc55de4c96b14c59">
                <div class="col-md-9">
                    <p>
                        Dado que como es posible ver en el <a href="craftings.html">listado de crafteos eliminados</a>
                        no es posible la
                        creación de hornos, o al menos al inicio de la partida, se le ha adicionado a la fogata la
                        funcionalidad
                        de poder fundir minerales como el hierro o el oro luego de un tiempo de calentarse en la
                        hoguera.
                    </p>
                    <p>
                        Desde la fogata también es posible secar la carne de Zombie para convertira en cuero, quemar
                        cactus para conseguir tinte verde y convertir palos en antorchas.
                    </p>
                </div>
            </div>
        </div>
        `;
    }
}