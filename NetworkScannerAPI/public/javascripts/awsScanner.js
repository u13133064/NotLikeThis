/**
 * Created by Jedd Shneier
 */
var AWS = require('aws-sdk');
var composite = require('aws-sdk');
exports.scanNetwork=function(region) {
    AWS.config.region = region;
    var ec2 = new AWS.EC2();
    ec2.describeInstances(function(err, data) {
        if (err) console.log(err, err.stack); // an error occurred
        var inst_id = '-';
        console.log(data.Reservations.length);
        for (var i = 0; i < data.Reservations.length; i++) {
            var res = data.Reservations[i];
            var instances = res.Instances;
            for (var j = 0; j < instances.length; j++) {
                var instanceID = instances[j].InstanceId;
                var state = instances[j].State.Code;
                var public_ip = instances[j].PublicIpAddress;
                var imageID = instances[j].ImageId;
                console.log('instance ' + instanceID + " state " + state + " public ip " + public_ip + 'image id ' + imageID);
            }
        }

})};