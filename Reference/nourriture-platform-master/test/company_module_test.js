/**
 * Created by Pavel Prochazka on 12/12/14.
 */

var Expect      = require('chai').expect
var Supertest   = require('supertest')  // high-level abstraction for testing HTTP, while still allowing you to drop down to the lower-level API provided by super-agent
var API         = Supertest('http://localhost:2121')
var MongoClient = require('mongodb').MongoClient    //MongoClient for high-level end to Mongo

before(function(done){
    console.log('BEFORE test, check whether NodeJS already up and running')

    var inter = setInterval(function() {
        console.log(" - - - Is NodeJS server running???")

        API.get('')
            .end(function(error,response){

                if(!error){
                    console.log(" - - - YES NodeJS server is running!!!")

                    clearInterval(inter)
                    done()
                }
            })
    }, 500)

})  // Straight to DB*/

after(function(done){   //after running all test cases wipe entire Company collection (nothing should be left there though)
    console.log('AFTER test, wipe Company collection from ' + process.env.dbcon)

    MongoClient.connect(process.env.dbcon, function(err, connection) {
        var collection = connection.collection('companies');
        collection.remove({}, function() {
            connection.close();
            done()  // invoke the callback when your test is complete.
                    // by adding a callback (usually named done) to it() Mocha will know that it should wait for completion. !!!
        });
    });
})  // Straight to DB

describe('Company module API tests', function() {   //MOCHA test name

    var companyUsername //will be assigned upon POST response
    var invalidUsername = "!#$%^&" //for testing the fail scenarios



    it('POST a company, with required fields', function (done) {    //MOCHA test cases
        API.post('/company')
            .set('Content-Type', 'application/json')
            .send('{"name":"Helmans","username":"helmans"}')
            .expect(200)                                            //NOTE: .expect is part of the supertest module
            .end(function(error,response){
                //console.log(response.body)
                Expect(error).to.equal(null)                        //NOTE: expect is part of the chai module
                Expect(response.body._id).is.not.empty
                companyUsername = response.body.username
                done()
            })
    })

    it('POST a company, without all required fields', function (done) {    //MOCHA test cases
        API.post('/company')
            .set('Content-Type', 'application/json')
            .send('{"name":"tobi"}')
            .expect(400)
            .end(function(error, response){
                if (error)
                    return done(error);   //pass err to done() in order to fail the test case FAIL (SUCCESS scenario)
                done()                    //passes the test PASS (FAIL scenerio)
            });
    })



    it('GET a specific company, valid company username', function(done){
        API.get('/company/' + companyUsername)
            .set('Content-Type', 'application/json')
            .expect(200, done);
    })

    it('GET a specific company, invalid company username', function(done){
        API.get('/company/' + invalidUsername)
            .set('Content-Type', 'application/json')
            .expect(404)
            .end(function(error,response){
                Expect(response.body.message).to.eql('No company found with the given company username')
                done()
            })
    })



    it('GET company with search, empty search', function(done){
        API.get('/company')
            .query({ search: '' })
            .set('Content-Type', 'application/json')
            .expect(200)
            .end(function(error,response){
                Expect(error).to.equal(null)
                Expect(response).to.be.an('object')
                done()
            })
    })

    it('GET company with search, valid company username', function(done){
        API.get('/company')
            .query({ search: companyUsername })
            .set('Content-Type', 'application/json')
            .expect(200, done)  //remember to pass done!
    })



    it('PUT a company, with valid username', function (done) {    //MOCHA test cases

        var logoURL = "http://img4.myrecipes.com/i/sponsor-recipe-logos/hellmanns_logo-150.png"
        var newCompanyName = "Helmans ApS"

        API.put('/company/' + companyUsername)
            .set('Content-Type', 'application/json')
            .send({name:newCompanyName})
            .send({logo:logoURL})
            .expect(200)
            .end(function (error,response){
                Expect(response.body.logo).to.eql(logoURL)          //check if attributes of the returned object got really updated
                Expect(response.body.name).to.eql(newCompanyName)
                done()
            })
    })

    it('PUT a company, with invalid username', function(done){
        API.put('/company/' + invalidUsername)
            .set('Content-Type', 'application/json')
            .expect(404)
            .end(function(error,response){
                Expect(response.body.message).to.eql('No company found with the given username')
                done()
            })
    })



    it('DELETE a company, with valid username', function (done) {    //MOCHA test cases
        API.del('/company/' + companyUsername)
            .set('Content-Type', 'application/json')
            .expect(200, done)
    })

    it('DELETE a company, with invalid username', function(done){
        API.put('/company/' + invalidUsername)
            .set('Content-Type', 'application/json')
            .expect(404)
            .end(function(error,response){
                Expect(response.body.message).to.eql('No company found with the given username')
                done()
            })
    })
})