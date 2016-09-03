/**
 * Created by Jedd Shneier
 */

var awsScanner=require('../public/javascripts/awsScanner')

exports.index = function(req, res) {
    res.status(200).json({ message: "So happy this actually working"});
}

exports.scanNetwork = function(req, res) {
    var region = req.query.region;
    console.log(region);
    awsScanner.scanNetwork(region);

    res.status(200).json({ message: 0});
}

