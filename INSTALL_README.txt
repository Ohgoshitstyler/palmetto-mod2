PROJECT PALMETTO — 16 TERRAIN BLOCKS + CREATIVE TAB
Minecraft Java 1.21.11 | Fabric | IntelliJ IDEA

PLEASE CLOSE THE DEVELOPMENT MINECRAFT WINDOW BEFORE INSTALLING.

WHAT THIS UPDATE DOES
- Replaces ONLY your existing PalmettoMod.java with a version registering all 16 blocks.
- Adds the previously created 16 original-for-your-private-use color-edit PNGs (128x128).
- Adds blockstates, models, item definitions, English names, shovel tags, and drops.
- Adds one new Creative inventory tab: "Palmetto | Terrain".
- Retains the existing mod ID: palmetto.
- Preserves original red_clay block ID; existing Red Clay blocks remain the same block.
- Leaves build.gradle, gradle.properties, fabric.mod.json and your other Java files alone.
- Sand and gravel LOOK and SOUND like sand/gravel, but do NOT fall. This is intentional for terrain building and WorldPainter.

INSTALL (MANUAL — NO INSTALLER TO RUN)
1. In IntelliJ, close your Minecraft development client if running.
2. Make a backup of your current PalmettoMod.java:
   C:\Users\tsnid\IdeaProjects\PalmettoBuildingTerrain\src\main\java\com\projectpalmetto\buildingterrain\PalmettoMod.java
3. Extract this ZIP in Downloads. Open the extracted folder.
4. COPY the 'src' folder from the extracted folder.
5. PASTE it into your project folder:
   C:\Users\tsnid\IdeaProjects\PalmettoBuildingTerrain\
   Choose to merge folders. Confirm replacement of PalmettoMod.java and, if asked, existing Red Clay resource files.
   DO NOT delete your existing src folder. DO NOT copy the outer ZIP folder into the project.
6. In IntelliJ run the Gradle task 'build'. Look for BUILD SUCCESSFUL.
7. Start your existing runClient task (same process as before).
8. Open Creative inventory and find "Palmetto | Terrain". It has all 16 blocks.
9. Or use: /give @s palmetto:orange_clay

TROUBLESHOOTING
- If Minecraft fails to launch, copy the last 15-20 lines of the first red error to ChatGPT.
- If textures are black/magenta, check 'src/main/resources/assets/palmetto/textures/block/'.
- Do not rename any block IDs after building with them. WorldPainter can use those names later.
- These textures are edits of user-provided WoKFlows sources; retain for private use unless you have appropriate rights to redistribute.

BLOCK IDS
palmetto:red_clay  —  Red Clay
palmetto:orange_clay  —  Orange Clay
palmetto:brown_clay  —  Brown Clay
palmetto:red_dirt  —  Red Dirt
palmetto:orange_dirt  —  Orange Dirt
palmetto:brown_dirt  —  Brown Dirt
palmetto:red_coarse_dirt  —  Red Coarse Dirt
palmetto:orange_coarse_dirt  —  Orange Coarse Dirt
palmetto:brown_coarse_dirt  —  Brown Coarse Dirt
palmetto:red_gravel  —  Red Gravel
palmetto:brown_gravel  —  Brown Gravel
palmetto:red_sand  —  Red Sand
palmetto:orange_sand  —  Orange Sand
palmetto:dark_mud  —  Dark Mud
palmetto:forest_floor  —  Forest Floor
palmetto:rooted_soil  —  Rooted Soil
