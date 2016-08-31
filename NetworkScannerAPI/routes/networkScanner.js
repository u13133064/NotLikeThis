/**
 * Created by Jedd Shneier
 */

exports.index = function(req, res) {
    res.status(200).json({ message: "So happy this actually working"});
}

exports.scanNetwork = function(req, res) {
    var i =0;
    for(i;i<1000;i++)
    {

    }

    res.status(200).json({ message: i});
}

