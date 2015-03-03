/**
 * Created by remoteusrr on 22/10/14.
 * To document our API endpoints
 * http://thejackalofjavascript.com/list-all-rest-endpoints/
 */

module.exports =  function (routes, src) {

    var Table = require('cli-table');
    var table = new Table({ head: ["", "Name", "Path"], chars: { 'left':'\t│', 'right':'  │', 'left-mid':'\t├', 'bottom-left':'\t└', 'top-left':'\t┌', 'mid-mid':'─┼', 'bottom-mid':'─┴', 'top-mid':'─┬' } });

    console.log('\nAPI for this service \n');
    if(src == 'restify')
    {
        console.log('\n********************************************');
        console.log('\t\tRESTIFY');
        console.log('********************************************\n');
        for (var key in routes) {
            if (routes.hasOwnProperty(key)) {
                var val = routes[key];
                var _o = {};
                var name = /(\w+)[0-9]{3}/.exec(val.name)[1];
                _o[val.method]  = [name, val.spec.path ];
                table.push(_o);

            }
        }
    }
    else
    {
        console.log('\n********************************************');
        console.log('\t\tEXPRESS');
        console.log('********************************************\n');
        for (var key in routes) {
            if (routes.hasOwnProperty(key)) {
                var val = routes[key];
                if(val.route)
                {
                    val = val.route;
                    var _o = {};
                    _o[val.stack[0].method]  = [val.path, val.path ];
                    table.push(_o);
                }
            }
        }
    }
    console.log(table.toString());

    return table;
};