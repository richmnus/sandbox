/**
 * Person class is the supertype
 */

// This is the constructor
var Person = function(firstName) {
	// Local variables are added as properties to the prototype object
	// 'this' refers to the Person prototype object, outside the function it
	// could refer to the global object
	this.firstName = firstName;
	console.log('Person: Instance created: ' + firstName);
};

// Add a method to the class by assigning a function to a named property in the
// class's prototype object
Person.prototype.sayHello = function() {
	console.log("Hello, I'm " + this.firstName);
}

Person.prototype.dance = function() {
	console.log("I'm dancing!");
}
