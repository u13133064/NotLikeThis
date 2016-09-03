/**
 * Created by Jedd Shneier
 */
var Node = function (uuid,additionalInformation) {
    this.children = [];
    this.uuid = uuid;
    this.additionalInformation=additionalInformation;

}

Node.prototype = {
    add: function (child) {
        this.children.push(child);
    },

    remove: function (child) {
        var length = this.children.length;
        for (var i = 0; i < length; i++) {
            if (this.children[i] === child) {
                this.children.splice(i, 1);
                return;
            }
        }
    },

    getChild: function (i) {
        return this.children[i];
    },

    hasChildren: function () {
        return this.children.length > 0;
    }
}

// recursively traverse a (sub)tree

function traverse(node)
{

    var jsonNetwork='{';
    jsonNetwork+="UUID : "+node.uuid;
    jsonNetwork+=",additionalInformation : "+node.additionalInformation;
    jsonNetwork+=',Children:[';
    for (var i = 0, len = node.children.length; i < len; i++)
    {

        jsonNetwork+=traverse(node.getChild(i));

    }
    jsonNetwork+=']';
    jsonNetwork+='}';
    return jsonNetwork;
}





