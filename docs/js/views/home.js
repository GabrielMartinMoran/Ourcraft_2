export const home = {
    getHTML: () => {
        return /*html*/`
        <div class="row h-100">
            <div class="col-lg-6 col-xs-12">
                <div class="m-4">
                    <h1><img width="50" src="img/icon.png"> Ourcraft 2</h1>
                    <h5>Servidor de Minecraft PVE / PVP / ROL para Minecraft 1.16.5</h5>
                    <iframe class="w-100" height="315" src="https://www.youtube.com/embed/xlqZZVUQ9II" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                    <br>
                    <p>Ourcraft 2 es la secuela del servidor de Minecraft Ourcraft (vigente a mediados de 2020)</p>
                    <p>Ourcraft 2 es un servidor de Minecraft con nuevas mecánicas, tales como:</p>
                    <ul>
                        <li>Sistema de hidratación</li>
                        <li>Mundo dividido en 5 zonas de dificultad</li>
                        <li>Nuevos items que mejoran la experiencia de juego</li>
                        <li>Nuevos mobs hostiles</li>
                        <li>Nuevo sistema de economía</li>
                    </ul>                        
                    <p>Te invitamos a <a href="https://discord.gg/UZZ6rxv">unirte al chat de Discord</a>.</p>
                    <p>
                    Para jugar, en caso de no tener cuenta premium de Minecraft, recomendamos que te descargues el launcher
                    <a href="https://tlauncher.org/en/"> TLauncher</a>.
                    </p>
                </div>
            </div>
            <div class="col-lg-6 col-xs-12">
                <iframe class="w-100 h-100" src="https://discordapp.com/widget?id=723281601554939964&theme=dark"
                allowtransparency="true" frameborder="0"></iframe>
            </div>
        </div>
        `;
    }
}