<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.4" tiledversion="1.4.2" name="testset" tilewidth="16" tileheight="16" tilecount="13" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="wall" type="bool" value="false"/>
  </properties>
  <image width="16" height="16" source="../textures/air.png"/>
 </tile>
 <tile id="1">
  <image width="16" height="16" source="../textures/plant_0.png"/>
 </tile>
 <tile id="2">
  <image width="16" height="16" source="../textures/plant_1.png"/>
 </tile>
 <tile id="3">
  <image width="16" height="16" source="../textures/plant_2.png"/>
 </tile>
 <tile id="4">
  <image width="16" height="16" source="../textures/tilled.png"/>
 </tile>
 <tile id="5">
  <image width="16" height="16" source="../textures/debugTile.png"/>
 </tile>
 <tile id="6">
  <image width="16" height="16" source="../textures/soil.png"/>
 </tile>
 <tile id="7">
  <properties>
   <property name="wall" type="bool" value="false"/>
  </properties>
  <image width="16" height="16" source="../textures/water_001.png"/>
 </tile>
 <tile id="8">
  <properties>
   <property name="wall" type="bool" value="false"/>
  </properties>
  <image width="16" height="16" source="../textures/water_002.png"/>
 </tile>
 <tile id="9">
  <properties>
   <property name="wall" type="bool" value="false"/>
  </properties>
  <image width="16" height="16" source="../textures/water_003.png"/>
  <animation>
   <frame tileid="7" duration="200"/>
   <frame tileid="8" duration="200"/>
   <frame tileid="9" duration="200"/>
  </animation>
 </tile>
 <tile id="10">
  <image width="16" height="16" source="../textures/grass.png"/>
 </tile>
 <tile id="11">
  <image width="16" height="16" source="../textures/rock.png"/>
 </tile>
 <tile id="12">
  <image width="16" height="16" source="../textures/sand.png"/>
 </tile>
</tileset>
