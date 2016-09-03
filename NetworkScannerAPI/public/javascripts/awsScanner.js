/**
 * Created by Jedd Shneier
 */
var AWS = require('aws-sdk');
var composite = require('./composite');
exports.scanNetwork=function()
{
    AWS.config.region = 'ap-south-1';
    var ec2 = new AWS.EC2();
    ec2.describeRegions(function(err, data)
    {
        if (err)
        {
            console.log(err, err.stack); // an error occurred
        }
        else
        {
            var regions =data.Regions;
            var root = new composite.Node('1','AWS');
            for(var i =0;i<regions.length;i++)
            {

                root.add( new composite.Node(regions[i].RegionName,regions[i].RegionName));
                AWS.config.region =regions[i].RegionName ;
                ec2 = new AWS.EC2();
                ec2.describeVpcs(function(err, data)
                {
                    if (err)
                    {
                        console.log(err, err.stack); // an error occurred
                    }
                    else
                    {
                        var vpcs = data.Vpcs;
                        for(var j =0;j<vpcs.length;j++) {
                            console.log(root.getChild());
                            root.getChild(i).add(new composite.Node(vpcs[j].VpcId,vpcs[j].VpcId));
                        }
                    }

                })

            }

            console.log(composite.traverse(root));


        }

    })
};

