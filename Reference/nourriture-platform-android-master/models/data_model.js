/**
 * Created by niels on 10/28/14.
 * Mongoose data model definitions for the application backend
 */

var util = require('./data_model_middleware');

module.exports = function (mongoose) {

    // CONSUMER
    var FollowRelation = mongoose.Schema( {
        created: { type: Date, required: true },
        cId: { type: mongoose.Schema.Types.ObjectId, ref: "consumer", required: true},
        name: { type: String, validate: util.strLength(64), required: true },
        picture: { type: String, validate: util.strLength(512) },
        occupation: { type: String, validate: util.strLength(128) }
    });
    var Consumer = mongoose.Schema({
        created: { type: Date, required: true },
        modified: { type: Date, required: true },
        username: { type: String, required: true},
        email: { type: String, validate: util.strLength(128), required:true },
        name: { type: String, validate: util.strLength(64), required: true },
        picture: { type: String, validate: util.strLength(512) },
        bio: { type: String, validate: util.strLength(4096) },
        occupation: { type: String, validate: util.strLength(128) },
        gender: { type: Number, min: 0, max: 2 },
        birthday: Date,
        website: { type: String, validate: util.strLength(512) },
        following: [FollowRelation]
    });
    Consumer.pre('validate', true, util.updateTimeStamps);

    // MOMENT
    var Like = mongoose.Schema({
        cId: { type: mongoose.Schema.Types.ObjectId, ref: "Consumer", required: true},
        name: { type: String, validate: util.strLength(64), required: true }
    }, { _id: false });
    var Comment = mongoose.Schema({
       created: { type: Date, required: true},
       author: {
           cId: { type: mongoose.Schema.Types.ObjectId, ref: "Consumer", required: true},
           name: { type: String, validate: util.strLength(64), required: true }
       },
       text: { type: String, validate: util.strLength(1024)},
       likes: [{ type: mongoose.Schema.Types.ObjectId, ref: "Consumer"}]
    });
    var Moment = mongoose.Schema({
        created: { type: Date, required: true },
        author: {
            cId: { type: mongoose.Schema.Types.ObjectId, ref: "Consumer", required: true},
            name: { type: String, validate: util.strLength(64), required: true }
        },
        modified: { type: Date, required: true },
        text: { type: String, validate: util.strLength(1024) },
        subjectID: String,       // NOTE: Referring to recipe, ingredient, gastronomist or company in Nourriture (3rd party)
        likes: [Like],
        likeCount: Number,
        comments: [Comment],
        commentCount: Number
    });
    Moment.pre('validate', true, util.initMomentCounters);
    Moment.pre('validate', true, util.updateTimeStamps);

    // RATING
    var Rating = mongoose.Schema({
        created: { type: Date, required: true },
        author: { type: mongoose.Schema.Types.ObjectId, ref: "Consumer", required: true },
        value: { type: Number, min: 1, max: 6 },
        difficulty: { type: Number, min: 1, max: 6},
        subjectID: { type: String, required: true}      // NOTE: Referring to recipe, ingredient, gastronomist or company in Nourriture (3rd party)
    });
    Rating.pre('validate', true, util.updateTimeStamps);


    // Bind to DB collection names and return on single object
    return {
        FollowRelation: mongoose.model("following", FollowRelation),
        Consumer: mongoose.model("consumer", Consumer),
        Moment: mongoose.model("moment", Moment),
        Comment: mongoose.model("comment", Comment),
        Like:  mongoose.model("like", Like),
        Rating: mongoose.model("rating", Rating)
    };
};