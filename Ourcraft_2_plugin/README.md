# Setup

# BuildTools

BuildTools it's used for acceding Minecraft internal classes from the plugin

1 - Install BuildTools from https://www.spigotmc.org/wiki/buildtools/#linux and run it like this:

```bash
java -jar BuildTools.jar --rev 1.19.4 --remapped --generate-docs --generate-source
```

2 - Update the version of the dependencies in `<pluglins>` and `<dependencies>` (BuildTools automatically adds the
dependency as local)