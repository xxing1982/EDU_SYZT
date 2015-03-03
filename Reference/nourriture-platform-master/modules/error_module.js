/**
 * Created by niels on 12/3/14.
 */

var auth = require("authorized");

exports.jsonErrorFormatter = function(req, res, body) {
    if (body instanceof auth.UnauthorizedError) {
        body.statusCode = 401;
    }

    if (body instanceof Error) {
        // snoop for RestError or HttpError, but don't rely on
        // instanceof
        res.statusCode = body.statusCode || 500;

        if (body.body) {
            body = body.body;
        } else {
            body = {
                message: body.message
            };
        }
    } else if (Buffer.isBuffer(body)) {
        body = body.toString('base64');
    }

    var data = JSON.stringify(body);
    res.setHeader('Content-Length', Buffer.byteLength(data));

    return (data);
};