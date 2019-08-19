//Example 1

// var firstName = ["H", "e", "l", "l", "o"];
var firstName = "Hello";
function fullName(fn, sn) {
    return {
        fullName: function () { return fn + " " + sn }
    }
};
var toFullName = fullName(firstName, "World");
firstName = "adhkjas";
console.log(toFullName.fullName());
//fistName = "Henk";

// (firstName, "World").fullName();