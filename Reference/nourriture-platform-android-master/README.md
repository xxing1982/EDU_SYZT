nourriture-android-platform
===========================

Repository for the Nourriture Android application's backend.

The Nourriture Android application's backend is the backend for the Android app. It provides REST API to developers.

Please, refer to the architecture diagram (http://tinyurl.com/qattlat).

********************************************

#### The detailed list of our API
	   
    ┌────────┬─────────────────────────────────┬───────────────────────────────────┐
    │        │ Name                            │ Path                              │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ POST   │ postconsumer                    │ /consumer                         │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ GET    │ getconsumerusername             │ /consumer/:username               │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ PUT    │ putconsumerusername             │ /consumer/:username               │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ DELETE │ deleteconsumerusername          │ /consumer/:username               │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ GET    │ getconsumer                     │ /consumer/                        │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ GET    │ getconsumerusernamefollowing    │ /consumer/:username/following     │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ POST   │ postconsumeridfollowing         │ /consumer/:id/following           │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ DELETE │ deleteconsumeridfollowingtarget │ /consumer/:id/following/:target   │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ POST   │ postconsumerusernamepicture     │ /consumer/:username/picture       │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ POST   │ postmoment                      │ /moment                           │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ GET    │ getmomentid                     │ /moment/:id                       │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ GET    │ getmoment                       │ /moment                           │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ PUT    │ putmomentid                     │ /moment/:id                       │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ DELETE │ deletemomentid                  │ /moment/:id                       │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ POST   │ postmomentidcomment             │ /moment/:id/comment               │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ DELETE │ deletemomentidcommentcid        │ /moment/:id/comment/:cid          │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ POST   │ postmomentidlike                │ /moment/:id/like                  │
    ├────────┼─────────────────────────────────┼───────────────────────────────────┤
    │ DELETE │ deletemomentidlikecid           │ /moment/:id/like/:cid             │
    └────────┴─────────────────────────────────┴───────────────────────────────────┘
********************************************
