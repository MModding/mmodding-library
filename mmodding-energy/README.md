## mmodding-energy

Often referred as the "MModding Energy Module", or "MModding Energy API",
this module aims to provide a way to define, manage and save energy storages.

During its implementation, it took inspiration from the Fabric Transfer API Base
and the Team Reborn Energy API, as the implementation also makes use of the amazing
transaction system and the Fabric Lookup API. (Hidden in implementation compared to
Reborn Energy, for example.)
By such, making transfers between energy accesses will be similar to transfers between energy storages of the Tech Reborn API.

We also follow the push-based convention of Reborn Energy, since it makes sense and is
pretty much a standard now. Also pretty much prevents all the compatibility issues.

This API exists because:
1. I always wanted to make such thing, because oh gosh long live
Fabric Lookup API and Fabric Transfer API, those are wonderful to use.
2. I wanted to be able to define multiple Energy Units from a
standard (here, Fabric Energy (E) of Reborn Energy), so that
modders can define and handle different energy units; also
giving them the right to make other units incompatible through
unit identity checks.
3. I wanted an API which would handle the storage and saving part
by itself. By such, Energy on Items is stored through hidden data
components, and ItemEnergy provides an interface for dealing with
that stuff, and Energy on Blocks is stored through hidden saved
level data, and then BlockEnergy works in a similar way.
