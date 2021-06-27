function getImageTag(url) {
  return `<img src="${url}" style="height: 1.5rem"></img>`;
}

const TAGS = [
  { 'key': 'water_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/2/25/Water_%28animated%29.png?version=4d81c3a13e2c70d4bcfb0a5a4532ff5b") },
  { 'key': 'heart_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/d/d9/Heart.png?version=1da811ed114de18b9e78c0c036051244") },
  { 'key': 'fire_resistance_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/9/95/Fire_Resistance.png?version=7ab4ee881c8d3d61856424eedbc5c3ab") },
  { 'key': 'strength_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/8/8b/Strength.png?version=dc72afc37e7e89eb58bdf20ab08ff10e") },
  { 'key': 'nightvision_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/9/91/Night_Vision.png?version=ab01d5e091f4a96fd3cf093e35960dba") },
  { 'key': 'slowness_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/7/7e/Slowness.png?version=14275b91ffc19cf9a7c72171ea2e572d") },
  { 'key': 'weakness_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/f/f9/Weakness.png?version=37dd027a732a19c2831e4d5edd861ea3") },
  { 'key': 'dolphinsgrace_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/8/82/Dolphin%27s_Grace.png?version=a54b4d95d5dd62d01a90a0f3f8d5e617") },
  { 'key': 'conduitpower_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/e/ef/Conduit_Power.png?version=5928bbfb94d4cd7ee7926a1d7e2ce70d") },
  { 'key': 'miningfatigue_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/c/ca/Mining_Fatigue.png?version=a9f717dcc65dd1222a9ae918e2effc99") },
  { 'key': 'speed_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/9/94/Speed.png?version=abfd2445d65fb0d5f10b2aeb6c4b5220") },
  { 'key': 'hunger_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/5/5a/Hunger.png?version=dc7bced9e6d6b12348365395c0b038dc") },
  { 'key': 'wooden_sword_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/d/d5/Wooden_Sword_JE2_BE2.png?version=d8b7ce08684294038de6be0a831a9180") },
  { 'key': 'stone_sword_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/b/b1/Stone_Sword_JE2_BE2.png?version=db3a89bf69162a8854f8de3d00009b43") },
  { 'key': 'iron_sword_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/8/8e/Iron_Sword_JE2_BE2.png?version=8e55e662946d2f44bf9e85b410b17506") },
  { 'key': 'golden_sword_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/d/db/Golden_Sword_JE3_BE2.png/120px-Golden_Sword_JE3_BE2.png?version=c1a2d896a14838d241d6fd21a9e99e3b") },
  { 'key': 'diamond_sword_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/6/6a/Diamond_Sword_JE2_BE2.png/120px-Diamond_Sword_JE2_BE2.png?version=fc4edfafd493d460f1fa0b339815cf1c") },
  { 'key': 'netherite_sword_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/7/74/Grid_Espada_de_netherita.png?version=2623ab5438745dfc06b64453e92b4c1a") },
  { 'key': 'wooden_axe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/5/56/Wooden_Axe_JE2_BE2.png?version=3889bb78b62ea1dc18967a2419799673") },
  { 'key': 'stone_axe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/0/02/Stone_Axe_JE2_BE2.png?version=ffaf2ed5504f00a042a989217d842fe0") },
  { 'key': 'iron_axe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/b/b7/Iron_Axe_JE4_BE2.png?version=aa88a14281e7957e380d70c02addfe71") },
  { 'key': 'golden_axe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/e/e2/Golden_Axe_JE3_BE2.png?version=9b007277659951286cd68ccb070a55ab") },
  { 'key': 'diamond_axe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/a/ae/Diamond_Axe_JE2_BE2.png?version=a4d8e237ad09abe71d161f9b507074aa") },
  { 'key': 'netherite_axe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/d/df/Netherite_Axe_JE2.png?version=861fc2d174ae46b9a79dcdd1c7e0d6c7") },
  { 'key': 'stone_hoe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/c/c8/Stone_Hoe_JE2_BE2.png?version=bea75ff4058a7af4d86612de1dc1f16a") },
  { 'key': 'iron_hoe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/b/ba/Iron_Hoe_JE2_BE2.png?version=bb5fe20803d8cfcc5001a07a96fb57c0") },
  { 'key': 'golden_hoe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/2/22/Golden_Hoe_JE2_BE2.png?version=afa054c4499c830aca256c4379a493bb") },
  { 'key': 'diamond_hoe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/6/68/Diamond_Hoe_JE2_BE2.png?version=d311afb09560cd753bb0e5b222e051b6") },
  { 'key': 'netherite_hoe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/5/50/Netherite_Hoe_JE2.png?version=8e2e6e996259443d123c610a59e5ea6d") },
  { 'key': 'stone_shovel_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/6/6c/Stone_Shovel_JE2_BE2.png?version=1c203234b52c3bd1e47115127b7aa035") },
  { 'key': 'iron_shovel_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/c/cd/Iron_Shovel_JE2_BE2.png?version=8e47922dc1c564c1b800b62734e6953c") },
  { 'key': 'golden_shovel_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/c/c9/Golden_Shovel_JE3_BE2.png?version=c3a0bead590efbd96b2bbfc8f9248b7e") },
  { 'key': 'diamond_shovel_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/6/61/Diamond_Shovel_JE2_BE2.png?version=4c7393b306b1d82ffc60031bb0098767") },
  { 'key': 'netherite_shovel_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/a/a1/Netherite_Shovel_JE2_BE1.png?version=0dc43c1488537dff6bd74d74924644b0") },
  { 'key': 'golden_pickaxe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/d/d8/Golden_Pickaxe_JE3_BE2.png/120px-Golden_Pickaxe_JE3_BE2.png?version=b030c25479998ef365f337e4e2efe84f") },
  { 'key': 'diamond_pickaxe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/7/7a/Diamond_Pickaxe_JE2_BE2.png?version=a15ca8b25719827ee46fff32b294ccf9") },
  { 'key': 'netherite_pickaxe_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/d/d4/Netherite_Pickaxe_JE3.png?version=d8eecd2cf596c1b0eff64ccc46f147c0") },
  { 'key': 'bow_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/9/99/Bow_JE2_BE1.png/120px-Bow_JE2_BE1.png?version=f59c9dcd0233ad8c30c7de68ac08e578") },
  { 'key': 'crossbow_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/7/79/Crossbow.png/150px-Crossbow.png?version=929fd418e4c54e34615df8d2dad0b688") },
  { 'key': 'arrow_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/5/5d/Arrow_%28Item%29.png?version=99dedc3d59580070c1b0573b417eb6ee") },
  { 'key': 'leather_helmet_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/d/de/Leather_Cap_JE4_BE2.png?version=79252a96f1e3deef301899482af61a35") },
  { 'key': 'iron_helmet_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/3/37/Iron_Helmet_JE2_BE2.png?version=6c3678013ffe66c2e28933679297ca47") },
  { 'key': 'golden_helmet_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/7/7a/Golden_Helmet_JE2_BE2.png?version=67a2a195e4b1799f234a848225dabbd1") },
  { 'key': 'diamond_helmet_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/b/b2/Diamond_Helmet_JE2_BE2.png?version=180f70b1340f7387df4dae39cd595008") },
  { 'key': 'netherite_helmet_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/2/2f/Netherite_Helmet_JE2_BE1.png?version=1678b24f5df322ca644a0ad0eb9b6de4") },
  { 'key': 'turtle_helmet_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/5/5e/Turtle_Shell.png?version=51f35fe5d76a2145ddfe45feb83aaed7") },
  { 'key': 'leather_chesplate_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/b/b7/Leather_Tunic_JE4_BE2.png?version=ade87a138bf98729c4a23e4c3f12e3a7") },
  { 'key': 'iron_chesplate_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/3/31/Iron_Chestplate_JE2_BE2.png?version=be13f6dab76c99d1fe8a33ffda37aecd") },
  { 'key': 'golden_chesplate_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/2/2e/Golden_Chestplate_JE2_BE2.png?version=f2b47c52a8209ed72256191ebb9db575") },
  { 'key': 'diamond_chesplate_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/e/e0/Diamond_Chestplate_JE3_BE2.png?version=b2f00ac24fe36f212ff917b6db4da393") },
  { 'key': 'netherite_chesplate_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/5/56/Netherite_Chestplate_JE2_BE1.png?version=2e3e9275b823b18a5504b9c8463d99e8") },
  { 'key': 'leather_leggings_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/0/06/Leather_Pants_JE4_BE2.png?version=6f8f8e1eec313cde66256ee6dc384aa6") },
  { 'key': 'iron_leggings_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/4/42/Iron_Leggings_JE2_BE2.png?version=e1b8cb904a82c10df76223cfc9e2b27b") },
  { 'key': 'golden_leggings_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/b/bf/Golden_Leggings_JE2_BE2.png?version=bfc87ac23bf91b8c5b542d3cd9b30fac") },
  { 'key': 'diamond_leggings_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/f/fc/Diamond_Leggings_JE2_BE2.png?version=efb92bca816b1820024529bb3254157d") },
  { 'key': 'netherite_leggings_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/4/4a/Netherite_Leggings_JE2.png?version=f8ae08a021daf2a916e03c1bda5a81b4") },
  { 'key': 'leather_boots_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/2/25/Leather_Boots_JE3_BE2.png?version=9cd1915c0e3deeb23eb5a7b9a088b1e5") },
  { 'key': 'iron_boots_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/4/42/Iron_Boots_JE2_BE2.png?version=a9d466157909bd56801dfa0924487f62") },
  { 'key': 'golden_boots_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/7/7c/Golden_Boots_JE2_BE2.png?version=967fd7a24ef877f10ac87d8c6982ea3c") },
  { 'key': 'diamond_boots_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/8/82/Diamond_Boots_JE2_BE2.png?version=4beb9780ae572d7fe47235bee0e95023") },
  { 'key': 'netherite_boots_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/6/60/Netherite_Boots_JE2_BE1.png?version=05110553bee0afb49131b989e89c4cb3") },
  { 'key': 'shield_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/c/c6/Shield_JE2_BE1.png/80px-Shield_JE2_BE1.png?version=e61d7b9ca83a7a79114955333a82ae1c") },
  { 'key': 'boat_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/2/26/Acacia_Boat_%28Item%29.png/120px-Acacia_Boat_%28Item%29.png?version=66b7919a546a0c23221de669d12e83a7") },
  { 'key': 'anvil_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/3/38/Anvil_JE2_BE2.png/150px-Anvil_JE2_BE2.png?version=3ac9f594c4c981fb9a68ea1a09bde1cc") },
  { 'key': 'compass_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/4/45/Compass_JE1_BE1.gif/150px-Compass_JE1_BE1.gif?version=79d901f087b8e9a5bd70bc25d79ea8e1") },
  { 'key': 'enchantment_table_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/9/93/Grid_Mesa_de_encantamientos.png?version=f35a383ef10f5eed2979675aaa49f167") },
  { 'key': 'leather_horse_armor_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/f/f7/Brown_Leather_Horse_Armor.png?version=42c07fd479d58727e224c7313daee0f9") },
  { 'key': 'brewing_stand_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/0/03/Soporte_para_pociones.png?version=5164ea0f700c928d6494e1d75df1b258") },
  { 'key': 'hopper_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/8/81/Hopper.png?version=ed53a40d4173cdf617591f7be716a7f4") },
  { 'key': 'torch_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/thumb/b/b2/Torch.png/150px-Torch.png?version=57ae271813a967e9035f30bdf80fe79c") },
  { 'key': 'minecart_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/9/96/Vagoneta.png?version=2a35da434bccd65b1f64abf6af657ffe") },
  { 'key': 'TNT_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/5/51/TNT_JE2_BE2.png/150px-TNT_JE2_BE2.png?version=6b19c3b8d4668ce617f1c4a8f2d98731") },
  { 'key': 'observer_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/3/39/Observer_JE4_BE3.png/150px-Observer_JE4_BE3.png?version=11c145c12ba1187514046c3b9dd48702") },
  { 'key': 'piston_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/0/08/Grid_Pist%C3%B3n.png?version=150a57103de1744d7c57fba74b701519") },
  { 'key': 'daylight_sensor_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/6/68/Grid_Sensor_de_luz_solar.png?version=537735a47ae1ad70c7910ea9097244ec") },
  { 'key': 'rail_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/1/1d/Rails.png?version=e813125fa76b79bbb1c9350fbc0860bd") },
  { 'key': 'furnace_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_es_gamepedia/0/0c/Horno.png?version=2c39afbeed87947d8f5db23abe9e8305") },
  { 'key': 'blast_furnace_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/a/a8/Blast_Furnace_JE1_BE1.png/150px-Blast_Furnace_JE1_BE1.png?version=1259f036ecf83bb6dd56a0a7c1ce5dfd") },
  { 'key': 'smoker_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/6/66/Smoker_JE1_BE1.png/150px-Smoker_JE1_BE1.png?version=184f596ff97221361f29e6c4fd4bcccc") },
  { 'key': 'dispenser_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/a/a2/Dispenser_JE3_BE2.png/150px-Dispenser_JE3_BE2.png?version=e286ec4161ffa1e4764d504187c16935") },
  { 'key': 'dropper_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/1/16/Dropper_JE2_BE2.png/150px-Dropper_JE2_BE2.png?version=21542a702f0743d92796a4333cf3b57b") },
  { 'key': 'stick_img', 'value': getImageTag("https://gamepedia.cursecdn.com/minecraft_gamepedia/thumb/7/7a/Stick_JE1_BE1.png/150px-Stick_JE1_BE1.png?version=0bc06bbfffe51d4626be7ed6ec9f3b4b") },
];
function replaceTags() {
  const regex = /{{ *([\w\d]+) *, *(\d+) *}}/gm;
  while ((m = regex.exec(document.body.innerHTML)) !== null) {
    if (m.index === regex.lastIndex) {
      regex.lastIndex++;
    }
    let toReplace = '';
    for (let i = 0; i < parseInt(m[2]); i++) {
      toReplace += TAGS.find(x => x.key === m[1]).value;
    }
    document.body.innerHTML = document.body.innerHTML.replace(m[0], toReplace);
  }
}

function includeHTML() {
  var z, i, elmnt, file, xhttp;
  // Loop through a collection of all HTML elements: 
  z = document.getElementsByTagName("include");
  for (i = 0; i < z.length; i++) {
    elmnt = z[i];
    // search for elements with a certain atrribute:
    file = elmnt.getAttribute("src");
    if (file) {
      // Make an HTTP request using the attribute value as the file name:
      xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
          if (this.status == 200) { elmnt.innerHTML = this.responseText; }
          if (this.status == 404) { elmnt.innerHTML = "Page not found."; }
          // Remove the attribute, and call this function once more:
          elmnt.removeAttribute("src");
          includeHTML();
          replaceTags();
        }
      }
      var href = window.location.href;
      var dir = href.substring(0, href.lastIndexOf('/')) + "/";
      xhttp.open("GET", dir + file, true);
      xhttp.send();
      return;
    }
  }
}

function onLoad() {
  includeHTML();
  replaceTags();
}

$(document).ready(function () {
  onLoad();
});

