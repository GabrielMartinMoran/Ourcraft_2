import { router } from "./router.js";

export const navbar = {
    render: () => {
        document.getElementById('navbar').innerHTML = /*html*/`
        <!-- Image and text -->
        <nav class="navbar navbar-light bg-light navbar-expand-lg" class="navbar">
            <a class="navbar-brand" href="#/home">
                <img src="img/icon.png" width="30" class="d-inline-block align-top" alt="">
                Ourcraft
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="#/home">
                            <img src="https://static.wikia.nocookie.net/minecraft_gamepedia/images/7/7f/Respawn_Anchor_Charges_3_JE1_BE1.gif"
                                width="30" height="30" class="d-inline-block align-top" alt="">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#/attributes"><img
                                src="https://static.wikia.nocookie.net/minecraft_gamepedia/images/1/10/Bottle_o%27_Enchanting.gif"
                                width="30" height="30" class="d-inline-block align-top" alt="">Atributos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#/recipes"><img
                                src="https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/0/07/Crafting_Table_JE4.png/120px-Crafting_Table_JE4.png?version=ba7f97cda03f5fa43c477fb6a941a2e5"
                                width="30" height="30" class="d-inline-block align-top" alt="">Recetas</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#/commands"><img
                                src="https://static.wikia.nocookie.net/minecraft_gamepedia/images/e/ea/Impulse_Command_Block_JE1.png"
                                width="30" height="30" class="d-inline-block align-top" alt="">Comandos</a>
                    </li>
                    <!--
                    <li class="nav-item">
                        <a class="nav-link" href="#/mechanics"><img
                                src="https://static.wikia.nocookie.net/minecraft_gamepedia/images/c/c5/Lit_Furnace_%28S%29.gif"
                                width="30" height="30" class="d-inline-block align-top" alt="">Mecánicas</a>
                    </li>
                    -->
                </ul>
            </div>
        </nav>
        <br>
        <br>
        <br>
        `;
    }
}