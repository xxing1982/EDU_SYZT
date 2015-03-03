/**
 * Created by niels on 10/30/14.
 * Mongoose data models for the platform
 */

var util = require('./data_model_middleware');

module.exports = function (mongoose) {  //passing mongoose object to constructor (this anonymous method)

    // COMPANY, username = unique ID
    var Company = mongoose.Schema({     //with Mongoose, everything is derived from a Schema (http://mongoosejs.com/docs/guide.html)
        created: { type: Date, required: true },
        modified: { type: Date, required: true },
        username: { type: String, required: true},
        name: { type: String, validate: util.strLength(64), required: true },
        logo: { type: String, validate: util.strLength(512) },
        description: { type: String, validate: util.strLength(1024) },
        website: { type: String, validate: util.strLength(512) },
        phone: { type: String, validate: util.strLength(16) },
        email: { type: String, validate: util.strLength(128) }
    });
    Company.pre('validate', true, util.updateTimeStamps);   //middleware (http://mongoosejs.com/docs/middleware.html), will get executed for the entire object and change "created" and "modified" values

    // GASTRONOMIST, username = unique ID
    var PartialRecipe = mongoose.Schema( {
        created: { type: Date, required: true },
        title: { type: String, validate: util.strLength(64) },
        picture: { type: String, validate: util.strLength(512) },
        description: { type: String, validate: util.strLength(512), required: true },
        original: { type: mongoose.Schema.Types.ObjectId, ref: "Recipe" }
    });
    var Gastronomist = mongoose.Schema({
        created: { type: Date, required: true },
        modified: { type: Date, required: true },
        username: { type: String, required: true },
        email: { type: String, validate: util.strLength(128), required:true },
        name: { type: String, validate: util.strLength(64), required: true },
        picture: { type: String, validate: util.strLength(512) },
        bio: { type: String, validate: util.strLength(4096) },
        occupation: { type: String, validate: util.strLength(128) },
        website: { type: String, validate: util.strLength(512) },
        latestRecipes: [PartialRecipe]
    });
    Gastronomist.pre('validate', true, util.updateTimeStamps);

    // INGREDIENT, _id = unique ID
    var Ingredient = mongoose.Schema({
        created: { type: Date, required: true },
        modified: { type: Date, required: true },
        company: { type: String, required: true },
        type: { type: String, validate: util.strLength(32) }, // liquid / solid
        name: { type: String, validate: util.strLength(64) },
        picture: { type: String, validate: util.strLength(512) },
        calories: Number,
        carbs: Number,
        proteins: Number,
        fat: Number,
        season: { type: String, validate: util.strLength(64) },
        origin: { type: String, validate: util.strLength(64) }
    });
    Ingredient.pre('validate', true, util.updateTimeStamps);

    // RECIPE, _id = unique ID
    var RecipeIngredient = mongoose.Schema( {
        name: { type: String, validate: util.strLength(64) },
        quantity: Number,
        quantityUnit: { type: String, validate: util.strLength(12) },
        original: { type: mongoose.Schema.Types.ObjectId, ref: "Ingredient" }
    });

    var RecipeInstruction = mongoose.Schema( {
        step: { type: String, validate: util.strLength(8192) }
    });

    var Recipe = mongoose.Schema({
        created: { type: Date, required: true },
        modified: { type: Date, required: true },
        author: { type: mongoose.Schema.Types.ObjectId, ref: "Gastronomist", required: true },
        title: { type: String, validate: util.strLength(64), required: true },
        description: { type: String, validate: util.strLength(512), required: true },
        instructions: [RecipeInstruction],
        picture: { type: String, validate: util.strLength(512) },
        calories: Number,
        carbs: Number,
        proteins: Number,
        fat: Number,
        difficulty: Number,
        ingredients: [RecipeIngredient]
    });
    Recipe.pre('validate', true, util.updateTimeStamps);

    // USER, username = unique ID
    var User = mongoose.Schema({
        created: { type: Date, required: true },
        modified: { type: Date, required: true },
        username: { type: String, required: true, unique: true },
        email: { type: String, required: true, unique: true },
        password: { type: String, required: true},
        role : { type: String, required: true },        // Authorization role (raw|gastro|comp|both|admin)
        authMethod: { type: String, required: true }    // Authentication method (local|fb|go)
    });
    User.pre('validate', true, util.updateTimeStamps);


    // Bind to DB collection names and return on single object
    return {
        Company: mongoose.model("company", Company),    //to use SCHEMA definition, we need to convert SCHEMA into a MODEL (model is a class with which we construct documents)
        Gastronomist: mongoose.model("gastronomist", Gastronomist),
        Ingredient: mongoose.model("ingredient", Ingredient),
        Recipe: mongoose.model("recipe", Recipe),
        User: mongoose.model("user", User)
    };
};