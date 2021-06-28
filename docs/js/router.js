import { commands } from "./views/commands.js";
import { recipes } from "./views/recipes.js";
import { home } from "./views/home.js";
import { mechanics } from "./views/mechanics.js";
import { attributes } from "./views/attributes.js";

const DEFAULT_ROUTE = 'home';

export const router = {

    views: {
        home: home,
        recipes: recipes,
        //mechanics: mechanics,
        commands: commands,
        attributes: attributes
    },

    render: (viewName) => {
        let viewHTML = router.views[viewName].getHTML();
        document.getElementById('viewport').innerHTML = viewHTML;
    },

    renderHash: () => {
        let newLocation = window.location.hash.substr(1).replace('/', '');
        if (router.views[newLocation]) {
            router.render(newLocation);
        } else {
            window.location.hash = `#/${DEFAULT_ROUTE}`;
        }
    },

    register: () => {
        window.addEventListener('hashchange', () => {
            router.renderHash();
        })
    }
}