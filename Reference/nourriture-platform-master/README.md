nourriture-platform
===================

The Nourriture platform is the backend of our system. It provides REST API to developers.

### Developer Installation
If you don't have **Node.js** obtain it from: https://nodejs.org

If you don't have **MongoDB** obtain it from: https://mongodb.org

In the root of this repo invoke:

	npm install			# Install dependencies
	node server.js		# Start web server

The server will now be running at **127.0.0.1:2121**

*NOTE: If you decide to configure the port and wish to run a frontend against this backend instance, make sure to adjust the port accordingly in the frontend configuration.*


### Architecture

Please, refer to the architecture diagram
(http://tinyurl.com/qattlat).

### API
	┌────────┬────────────────────────┬──────────────────────────┐
	│        │ Name                   │ Path                     │
	├────────┼────────────────────────┼──────────────────────────┤
	│ POST   │ postcompany            │ /company/                │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getcompany             │ /company                 │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getcompanyid           │ /company/:id             │
	├────────┼────────────────────────┼──────────────────────────┤
	│ PUT    │ putcompanyid           │ /company/:id             │
	├────────┼────────────────────────┼──────────────────────────┤
	│ DELETE │ deletecompanyid        │ /company/:id             │
	├────────┼────────────────────────┼──────────────────────────┤
	│ POST   │ postrecipe             │ /recipe/                 │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getrecipe              │ /recipe                  │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getrecipeid            │ /recipe/:id              │
	├────────┼────────────────────────┼──────────────────────────┤
	│ PUT    │ putrecipename          │ /recipe/:name            │
	├────────┼────────────────────────┼──────────────────────────┤
	│ DELETE │ deleterecipeid         │ /recipe/:id              │
	├────────┼────────────────────────┼──────────────────────────┤
	│ PUT    │ putrecipeid            │ /recipe/:id              │
	├────────┼────────────────────────┼──────────────────────────┤
	│ POST   │ postingredient         │ /ingredient/             │
	├────────┼────────────────────────┼──────────────────────────┤
	│ PUT    │ putingredientid        │ /ingredient/:id          │
	├────────┼────────────────────────┼──────────────────────────┤
	│ DELETE │ deleteingredientid     │ /ingredient/:id          │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getingredient          │ /ingredient              │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getingredientid        │ /ingredient/:id          │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getingredientname      │ /ingredient/:name        │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getingredientcompanyid │ /ingredient/:companyId   │
	├────────┼────────────────────────┼──────────────────────────┤
	│ POST   │ postgastronomist       │ /gastronomist/           │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getgastronomist        │ /gastronomist            │
	├────────┼────────────────────────┼──────────────────────────┤
	│ GET    │ getgastronomistid      │ /gastronomist/:id        │
	├────────┼────────────────────────┼──────────────────────────┤
	│ PUT    │ putgastronomistid      │ /gastronomist/:id        │
	├────────┼────────────────────────┼──────────────────────────┤
	│ DELETE │ deletegastronomistid   │ /gastronomist/:id        │
	└────────┴────────────────────────┴──────────────────────────┘
