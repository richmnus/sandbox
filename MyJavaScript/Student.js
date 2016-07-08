/**
 * Student class inherits from Person class
 */

// Constructor for the student subclass
// This could also be written: var Student = function(firstName, subject) {
function Student(firstName, subject) {
	// Call the parent constructor, making sure (using Function#call) that
	// 'this' is set correctly during the call
	Person.call(this, firstName);
	// Initialize Student class specific properties
	this.subject = subject;
}

// Set the Student's prototype object to equal that of Person (i.e. inherit from
// Person)
Student.prototype = Object.create(Person.prototype);
// Reset the constructor property from Person to Student
Student.prototype.constructor = Student;

// Override the sayHello() method
Student.prototype.sayHello = function() {
	console.log("Hello, I'm " + this.firstName + " and I'm studying "
			+ this.subject);
}

// Add a sayGoodbye() method
Student.prototype.sayGoodBye = function() {
	console.log("Goodbye!");
}
