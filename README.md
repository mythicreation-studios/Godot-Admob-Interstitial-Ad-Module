# Godot-Admob-Interstitial-Ad-Module
Simple Godot 3.1 Module to add Interstitial Ads to your android mobile games.

Instructions:

- Edit MythicAdmob/src/AndroidManifestChunk.xml
* Replace app-id with Your Admob App ID //eg. ca-app-pub-3940256099942544~3347511713

- Recompile Godot 3.1 with Module.
- Edit Godot 3.1 project.godot file and add the following text:
```python
[android]

modules="org/godotengine/godot/MythicAdmob"
```
Godot Implementation:

Initialize:
```python
var mythicAdmob = null
if Engine.has_singleton("MythicAdmob"):
		mythicAdmob = Engine.get_singleton("MythicAdmob")
		mythicAdmob.initMythicAdmob("app-id", "ad-unit-id", get_instance_id())
		mythicAdmob.loadInterstitialAd()
```

* Replace app-id with your Your Admob App ID //eg. ca-app-pub-3940256099942544~3347511713
* Replace ad-unit-id with your Your Admob Add Unit ID //eg. ca-app-pub-3940256099942544/1033173712

Displaying Ad:
```python
mythicAdmob.showInterstitialAd()
```

Admob Ad Rating Customization:
Default, its set to G for General Audiences.

- Edit MythicAdmob/src/MythicAdmob.java
```java
extras.putString("max_ad_content_rating", "G");
```

* Replace G with the desired rating. //eg. G, PG, T, MA - Rating Details Link: https://support.google.com/admob/answer/7562142?hl=en
