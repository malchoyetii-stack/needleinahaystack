# Needle in a Haystack (Forge 1.20.1)

Build:  ./gradlew build   (needs JDK 17; generate the wrapper once with `gradle wrapper` if missing)
Output: build/libs/needleinahaystack-1.20.1-1.0.0.jar -> drop in your mods folder (Forge 47.x)
Dev:    ./gradlew runClient

## How to play
1. Craft a Wooden Pitchfork (3 planks across the middle, 2 above as prongs, 1 stick below) and a Giant Haystack (9 hay blocks).
2. Place the haystack and swing at it. It regrows after each break and drops Hay Bundles.
   Each swing has a 1-in-300 chance to reveal THE NEEDLE (haystack then collapses).
3. Sell Hay Bundles to a Farmer villager (emeralds; better rate every level) and buy better pitchforks:
   Iron (L2, 12 emeralds), Golden (L3, 24), Diamond (L4, 40), Netherite (L5, 64).
   Giant Haystacks can be re-bought at L1 for 6 emeralds.
4. Hay blocks can also be crafted into 3 Hay Bundles for a jump start.

Tweak NEEDLE_ODDS, pitchfork speeds/yields (NeedleInAHaystack.java) and trades (HaystackEvents.java).
