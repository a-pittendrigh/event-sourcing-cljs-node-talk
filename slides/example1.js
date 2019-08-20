//Example 1

let firstName = ["H", "e", "l", "l", "o"];
let surname = ["W", "o", "r", "l", "d"];
function fullName(fn, sn) {
    var firstName = fn;
    var surname = sn;
    return {
        fullName: function () { return firstName + " " + surname },
        setSurname: function (sn) { surname = sn;}
    }
};
var toFullName = fullName(firstName, surname);
console.log(toFullName.fullName()); // H,e,l,l,o W,o,r,l,d
// surname = ["J", "o", "z", "i", "J", "S"];
toFullName.setSurname("JoziJS");
console.log(toFullName.fullName()); // H,e,l,l,o JoziJS