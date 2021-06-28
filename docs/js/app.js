import { navbar } from "./navbar.js";
import { footer } from "./footer.js";
import { router } from "./router.js";

function onLoad() {
  /* Router */
  router.register();
  router.renderHash();
  /* NavBar */
  navbar.render();
  /* Footer */
  footer.render();
}

$(document).ready(function () {
  onLoad();
});

